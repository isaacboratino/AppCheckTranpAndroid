package br.com.idbservices.transpcheckserver.Application;

import java.util.UUID;

import br.com.idbservices.transpcheckserver.Domain.Entities.TransporteEntity;
import br.com.idbservices.transpcheckserver.Infrastructure.Data.TransporteData;

public class TransporteApplication {

	public TransporteEntity getTransporteAtivoByIdUsuario(String idUsuario) {
		return new TransporteData().findTransporteAtivoByIdUsuario(UUID.fromString(idUsuario));
	}
	
}
