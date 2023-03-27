package com.yaroslavyankov.translation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    private Integer id;

    private String content;

    private Timestamp circulationTime;

    private String translateFrom;

    private String translateTo;

    private String ip;
}
