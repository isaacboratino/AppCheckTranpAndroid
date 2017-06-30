package br.com.idbservices.transpcheckserver.Infrastructure.CrossCutting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConcerns {

	public static Object requestJsonToObject(ServletInputStream requestBody, Class<?> classe) throws IOException {
		
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(requestBody));
		
        String json = "";
        
        if(bufferReader != null){
            json = bufferReader.readLine();
        }
 
        ObjectMapper mapper = new ObjectMapper();
        
        Object object = mapper.readValue(json, classe);
        
        return object;
	}
	
	public static void writeJsonInResponseFromObject(HttpServletResponse response, Object object) throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), object);
	}
	
}
