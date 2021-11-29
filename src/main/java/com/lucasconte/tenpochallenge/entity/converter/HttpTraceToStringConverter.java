package com.lucasconte.tenpochallenge.entity.converter;

import com.google.gson.Gson;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class HttpTraceToStringConverter implements AttributeConverter<HttpTrace, String> {
    private static final Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(HttpTrace attribute) {
        try {
            return GSON.toJson(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HttpTrace convertToEntityAttribute(String dbData) {
        try {
            return GSON.fromJson(dbData, HttpTrace.class);
        } catch (Exception e) {
            return null;
        }
    }
}
