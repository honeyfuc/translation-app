package com.yaroslavyankov.translation.httpClient.external_request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRequest {

    private String targetLanguageCode;

    private List<String> texts;
}
