package com.yaroslavyankov.translation.mapper;

import com.yaroslavyankov.translation.dto.RequestDTO;
import com.yaroslavyankov.translation.model.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestRowMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Request(
                resultSet.getInt("id"),
                resultSet.getString("content"),
                resultSet.getTimestamp("circulation_time"),
                resultSet.getString("translate_from"),
                resultSet.getString("translate_to"),
                resultSet.getString("ip"));
    }
}
