package br.com.idbservice.apptranspcheck.Infrastructure.Data;

import java.util.List;

import br.com.idbservice.apptranspcheck.Domain.Entities.TransporteEndEntity;

public class TransporteData {

    public TransporteEndEntity findStatusByIdUsuario(char status, String idUsuario) throws Exception {

        List<TransporteEndEntity> transportes = (List<TransporteEndEntity>) JsonData.lerJson(br.com.idbservice.apptranspcheck.Domain.Entities.TransporteEndEntity.TABLE_NAME);
        for (int i = 0; i < transportes.size(); i++) {

            TransporteEndEntity transporte = JsonData.mapper.convertValue(transportes.get(i), TransporteEndEntity.class);

            // Recuperar transporte ativo do usuario
//            if (transporte.getUsuarioEntity().getId().toString().equals(idUsuario) &&
//                    transporte.getStatus() == status) {
//                return transporte;
//            }
        }
        return null;
    }
}
