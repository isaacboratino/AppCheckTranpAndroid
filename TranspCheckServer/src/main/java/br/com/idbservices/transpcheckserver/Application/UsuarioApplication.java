package br.com.idbservices.transpcheckserver.Application;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import br.com.idbservices.transpcheckserver.Domain.Entities.UsuarioEntity;
import br.com.idbservices.transpcheckserver.Infrastructure.CrossCutting.JsonConcerns;

public class UsuarioApplication {
	
	public UsuarioEntity validarUsuario(HttpServletRequest request) throws Exception {
		
		Object json = JsonConcerns.requestJsonToObject(request.getInputStream(), UsuarioEntity.class);
		
		return (UsuarioEntity) json;
		
		/*if (usuario.equals("usuario1") && senha.equals("senha1"))
			return new UsuarioEntity(usuario,senha);
		else 
			return null;*/
	}

}
