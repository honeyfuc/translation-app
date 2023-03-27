package com.yaroslavyankov.translation.dto;

import com.yaroslavyankov.translation.httpClient.external_response.Translation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String translatedContent;
}
