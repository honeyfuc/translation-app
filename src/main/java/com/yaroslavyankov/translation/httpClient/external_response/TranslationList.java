package com.yaroslavyankov.translation.httpClient.external_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TranslationList {
    @JsonProperty("translations")
    private List<Translation> translationList;
}
