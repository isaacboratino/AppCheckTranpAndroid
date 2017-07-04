package br.com.idbservice.apptranspcheck.Infrastructure.Data;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.FileConcerns;

public class JsonData {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static Object inputStreamJsonToEntity(InputStream requestBody, Class<?> classe) throws IOException {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(requestBody));

        String json = "";

        if(bufferReader != null){
            json = bufferReader.readLine();
        }

        ObjectMapper mapper = new ObjectMapper();

        Object object = mapper.readValue(json, classe);

        return object;
    }

    public static Object lerJson(String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return  mapper.readValue(FileConcerns.criarArquivo(arquivoJson), Object.class);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void gravarJson(Object dados, String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            mapper.writeValue(FileConcerns.criarArquivo(arquivoJson), dados);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
    }
}
