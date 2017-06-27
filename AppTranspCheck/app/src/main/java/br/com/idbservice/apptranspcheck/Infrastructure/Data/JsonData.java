package br.com.idbservice.apptranspcheck.Infrastructure.Data;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.FileConcerns;

public class JsonData {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static Object lerJson(String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return  mapper.readValue(FileConcerns.CriarArquivo(arquivoJson), Object.class);

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

            mapper.writeValue(FileConcerns.CriarArquivo(arquivoJson), dados);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarJson(Object dados, String arquivoJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try {

            // Convert object to JSON string and save into a file directly
            mapper.writeValue(FileConcerns.CriarArquivo(arquivoJson), dados);

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
