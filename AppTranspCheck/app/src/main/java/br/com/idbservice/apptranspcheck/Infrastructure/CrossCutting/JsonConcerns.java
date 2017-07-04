package br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonConcerns {

    public static Object requestJsonToObject(String jsonString, Class<?> classeType) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Object object = mapper.readValue(jsonString, classeType);

        return object;
    }
}
