package com.yaroslavyankov.translation.controller;

import com.yaroslavyankov.translation.dto.RequestDTO;
import com.yaroslavyankov.translation.exception.BadRequestException;
import com.yaroslavyankov.translation.exception.InvalidRequestBodyException;
import com.yaroslavyankov.translation.exception.SameSourceAndTargetLanguages;
import com.yaroslavyankov.translation.service.TranslateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/translate")
public class TranslateController {

    private final TranslateService translateService;

    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }


    @PostMapping
    public ResponseEntity getTranslate(@RequestBody RequestDTO requestDTO,
                                       @RequestParam(value = "from") String from,
                                       @RequestParam(value = "to") String to,
                                       HttpServletRequest request) throws IOException, InterruptedException {

       try {
           return ResponseEntity.ok(translateService.translate(requestDTO, from, to, request));
       } catch (SameSourceAndTargetLanguages | BadRequestException | InvalidRequestBodyException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
