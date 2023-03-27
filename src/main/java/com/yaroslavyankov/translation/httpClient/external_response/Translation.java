package com.yaroslavyankov.translation.httpClient.external_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translation {

    private String text;

    private  String detectedLanguageCode;
}
