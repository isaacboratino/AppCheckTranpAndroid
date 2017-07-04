package br.com.idbservices.transpcheckserver.Infrastructure.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import br.com.idbservices.transpcheckserver.Domain.Entities.*;

public class TransporteData {

	List<TransporteEntity> transportes;
	
	public TransporteData() {
		
		// Criar usuario do sistema
        UsuarioEntity usuario1 = new UsuarioEntity(UUID.fromString("65a221bf-b67d-44eb-8a38-ca0e63d3fbd4"), "usuario1","senha1");
        UsuarioEntity usuario2 = new UsuarioEntity("usuario2","senha2");

        List<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        // Criar transportes por usuario
        TransporteEntity transporte1usuario1 = new TransporteEntity("Av São Joao 1, Sao Paulo",
                "Av Ibirapuera 320", "", "", TransporteEntity.STATUS_ATIVO, usuario1);

        TransporteEntity transporte2usuario1 = new TransporteEntity("Av São Bento 1, Sao Paulo",
                "Av Ipiranga 320", "", "", TransporteEntity.STATUS_AGUARDANDO, usuario1);

        // Criar transportes por usuario
        TransporteEntity transporte1usuario2 = new TransporteEntity("Av São Joao 1, Sao Paulo",
                "Av Ibirapuera 320", "", "", TransporteEntity.STATUS_ATIVO, usuario2);

        TransporteEntity transporte2usuario2 = new TransporteEntity("Av São Bento 1, Sao Paulo",
                "Av Ipiranga 320", "", "", TransporteEntity.STATUS_AGUARDANDO, usuario2);

        transportes = new ArrayList<TransporteEntity>();
        transportes.add(transporte1usuario1);
        transportes.add(transporte2usuario1);
        transportes.add(transporte1usuario2);
        transportes.add(transporte2usuario2);
	}
	
	public TransporteEntity findTransporteAtivoByIdUsuario(UUID idUsuario) {
		
		TransporteEntity transporte = null;
		
		for (int i = 0; i < transportes.size(); i++) {
			
			TransporteEntity transporteTemp = transportes.get(i);
			
			if (transporteTemp.getUsuarioEntity().getId().toString().equals(idUsuario.toString()) && transporteTemp.getStatus() == TransporteEntity.STATUS_ATIVO) {
				transporte = transporteTemp;
			}
		}
		return transporte;
	}
}