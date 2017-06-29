package br.com.idbservice.apptranspcheck.Infrastructure.Data;

import java.util.List;

import br.com.idbservice.apptranspcheck.Entities.TransporteEntity;

public class TransporteData {

    public TransporteEntity findStatusByIdUsuario(char status, String idUsuario) throws Exception {

        List<TransporteEntity> transportes = (List<TransporteEntity>) JsonData.lerJson(TransporteEntity.TABLE_NAME);
        for (int i = 0; i < transportes.size(); i++) {

            TransporteEntity transporte = JsonData.mapper.convertValue(transportes.get(i), TransporteEntity.class);

            // Recuperar transporte ativo do usuario
            if (transporte.getUsuarioEntity().getId().toString().equals(idUsuario) &&
                    transporte.getStatus() == status) {
                return transporte;
            }
        }
        return null;
    }
}
