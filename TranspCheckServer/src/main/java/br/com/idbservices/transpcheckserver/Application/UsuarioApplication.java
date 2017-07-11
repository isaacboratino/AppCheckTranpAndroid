package br.com.idbservices.transpcheckserver.Application;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import br.com.idbservices.transpcheckserver.Domain.Entities.UsuarioEntity;
import br.com.idbservices.transpcheckserver.Infrastructure.CrossCutting.JsonConcerns;

public class UsuarioApplication {
	
	public UsuarioEntity validarUsuario(HttpServletRequest request) throws Exception {
		
		UsuarioEntity usuarioValidacao = (UsuarioEntity) JsonConcerns.requestJsonToObject(request.getInputStream(), UsuarioEntity.class);		
		
		if (usuarioValidacao.getUsuario().equals("usuario1") && usuarioValidacao.getSenha().equals("senha1"))
			return new UsuarioEntity(UUID.fromString("65a221bf-b67d-44eb-8a38-ca0e63d3fbd4"), 
					usuarioValidacao.getUsuario(), usuarioValidacao.getSenha());
		else 
			return null;
	}
}