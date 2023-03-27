package com.yaroslavyankov.translation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Id
    private Integer id;

    private String contentPart;

    private Integer requestForeignKey;
}
