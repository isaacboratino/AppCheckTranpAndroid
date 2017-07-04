package br.com.idbservices.transpcheckserver.Services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.idbservices.transpcheckserver.Application.UsuarioApplication;
import br.com.idbservices.transpcheckserver.Domain.Entities.UsuarioEntity;
import br.com.idbservices.transpcheckserver.Infrastructure.CrossCutting.JsonConcerns;

@WebServlet("/auth")
public class AuthenticationService extends HttpServlet {
       
    public AuthenticationService() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {		
		       
		response.setContentType("application/json");
		
		try {
			
			UsuarioEntity usuario = new UsuarioApplication().validarUsuario(request);
			
			if (usuario != null) {
				response.setStatus(HttpServletResponse.SC_OK);
				JsonConcerns.writeJsonInResponseFromObject(response, usuario);
			}
			else
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);					
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			JsonConcerns.writeJsonInResponseFromObject(response, e);
		}
	}
}
