package com.yaroslavyankov.translation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yaroslavyankov.translation.dao.RequestDAO;
import com.yaroslavyankov.translation.dao.ResponseDAO;
import com.yaroslavyankov.translation.dto.RequestDTO;
import com.yaroslavyankov.translation.dto.ResponseDTO;
import com.yaroslavyankov.translation.exception.*;
import com.yaroslavyankov.translation.httpClient.external_response.Translation;
import com.yaroslavyankov.translation.httpClient.external_response.TranslationList;
import com.yaroslavyankov.translation.httpClient.external_request.ExternalRequest;
import com.yaroslavyankov.translation.model.Request;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;


@Service
@Slf4j
@PropertySource("classpath:api.properties")
public class TranslateService {

    @Value("${api.key}")
    private String KEY;

    @Value("${api.uri}")
    private  String URI;

    private final RequestDAO requestDAO;

    private final ResponseDAO responseDAO;

    @Autowired
    public TranslateService(RequestDAO requestDAO, ResponseDAO responseDAO) {
        this.requestDAO = requestDAO;
        this.responseDAO = responseDAO;
    }

    public ResponseDTO  translate(RequestDTO requestDTO, String from, String to, HttpServletRequest httpServletRequest) throws SameSourceAndTargetLanguages, BadRequestException, InvalidRequestBodyException {
        if (from.equals(to))
            throw  new SameSourceAndTargetLanguages("Выбраны одинаковые языки для перевода");
        if (requestDTO.getContent() == null || requestDTO.getContent().isEmpty())
            throw new InvalidRequestBodyException("Тело запроса навалидно - возможно, оно null или пустое");

        String translatedString;

        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
            HttpPost requestToExternalApi = prepareExternalRequest(requestDTO, to);
            HttpResponse response = httpClient.execute(requestToExternalApi);

            translatedString = getTranslatedString(response);
            int foreignKey = addRequest(requestDTO, from, to);
            addResponse(translatedString, foreignKey);

        } catch (Exception | EntityNotFoundException exception) {
            throw new BadRequestException("Некорректый запрос");
        }

        return new ResponseDTO(translatedString);
    }

    private String getJsonStringRequest(RequestDTO requestDTO, String languageToTranslate) {
        Gson gson = new Gson();
        List<String> contentToSend = new ArrayList<>(Arrays.asList(requestDTO.getContent().split(" ")));

        ExternalRequest externalRequest = new ExternalRequest();
        externalRequest.setTargetLanguageCode(languageToTranslate);
        externalRequest.setTexts(contentToSend);

        return gson.toJson(externalRequest);
    }

    private HttpPost prepareExternalRequest(RequestDTO requestDTO, String languageToTranslate) {
        HttpPost request = new HttpPost(URI);
        String auth = String.format("Api-Key %s", KEY);
        StringEntity params = new StringEntity(getJsonStringRequest(requestDTO, languageToTranslate), "UTF-8");
        params.setContentType("charset=UTF-8");
        request.addHeader("content-type", "application/json");
        request.addHeader("Authorization", auth);
        request.setEntity(params);

        return request;
    }

    private String getTranslatedString(HttpResponse externalResponse) throws IOException, EntityNotFoundException {
        HttpEntity entity = externalResponse.getEntity();
        StringBuilder translatedString = new StringBuilder();
        if (entity != null) {
            String retSrc = EntityUtils.toString(entity);

            ObjectMapper objectMapper = new ObjectMapper();
            TranslationList translationList = objectMapper.readValue(retSrc, TranslationList.class);

            for (Translation translation : translationList.getTranslationList()) {
                translatedString.append(translation.getText()).append(" ");
            }
        } else {
            throw new EntityNotFoundException("Внешний сервер не вернул ответ");
        }

        return translatedString.toString();
    }

    private Request getRequestEntity(RequestDTO requestDTO, String from, String to) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        InetAddress clientIp = null;
        try {
             clientIp = InetAddress.getLocalHost();
        } catch (UnknownHostException exception) {
            exception.getMessage();
        }

        Request request = new Request();
        request.setContent(requestDTO.getContent());
        request.setCirculationTime(timestamp);
        request.setTranslateFrom(from);
        request.setTranslateTo(to);
        request.setIp(clientIp != null ? clientIp.getHostAddress() : null);

        return request;
    }


    private int addRequest(RequestDTO requestDTO, String from, String to) {
        Request request = getRequestEntity(requestDTO, from, to);

        return requestDAO.insertRequest(request);
    }

    private void addResponse(String translatedString, int id) {
        List<String> partsOfTranslatedString = new ArrayList<>(Arrays.asList(translatedString.split(" ")));

        for (String item : partsOfTranslatedString) {
            responseDAO.insertResponse(item, id);
        }
    }
}
