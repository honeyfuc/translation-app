package com.yaroslavyankov.translation.service.data_service;

import com.yaroslavyankov.translation.dao.RequestDAO;
import com.yaroslavyankov.translation.model.Request;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class RequestDataAccessService implements RequestDAO {

    private final JdbcTemplate jdbcTemplate;

    public RequestDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int insertRequest(Request request) {
        String INSERT_MESSAGE_SQL
                = "insert into request (content, circulation_time, translate_from, translate_to, ip) values(?, ?, ?, ?, ?) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,request.getContent());
            ps.setTimestamp(2, request.getCirculationTime());
            ps.setString(3, request.getTranslateFrom());
            ps.setString(4, request.getTranslateTo());
            ps.setString(5, request.getIp());
            return ps;
        }, keyHolder);

        return  keyHolder.getKey().intValue();
    }
}
