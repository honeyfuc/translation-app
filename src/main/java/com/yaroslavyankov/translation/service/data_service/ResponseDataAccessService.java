package com.yaroslavyankov.translation.service.data_service;

import com.yaroslavyankov.translation.dao.ResponseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResponseDataAccessService implements ResponseDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResponseDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertResponse(String translatedString, int id) {
        String sql = """
                    INSERT INTO response (content_part, request_id)
                    VALUES (?, ?);
                """;
        return jdbcTemplate.update(sql, translatedString, id);
    }
}
