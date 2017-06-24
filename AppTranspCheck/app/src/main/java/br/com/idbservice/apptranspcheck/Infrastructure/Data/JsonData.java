package br.com.idbservice.apptranspcheck.Infrastructure.Data;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.File;
import android.os.Environment;

import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.FileUtils;

public class JsonData {

    public static Object LerJson(String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            Object dados = mapper.readValue(FileUtils.CriarArquivo(arquivoJson), Object.class);

            return dados;

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void GravarJson(Object dados, String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            mapper.writeValue(FileUtils.CriarArquivo(arquivoJson), dados);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
    }

    public static void AtualizarJson(Object dados, String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            // Convert object to JSON string and save into a file directly
            mapper.writeValue(FileUtils.CriarArquivo(arquivoJson), dados);

            // Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(dados);
            System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dados);
            System.out.println(jsonInString);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
    }
}
