package br.com.idbservice.apptranspcheck.Infrastructure.Data;

import java.util.ArrayList;
import java.util.List;

import br.com.idbservice.apptranspcheck.Entities.*;

public class InitData {

    public static void CargaInicial() {

        try {
            // Verifico se ja existem dados gravados
            Object dataJsonUsuarios = null;

            try {
                JsonData.LerJson(UsuarioEntity.TABLE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (dataJsonUsuarios == null) {

                // Criar usuario do sistema
                UsuarioEntity usuario1 = new UsuarioEntity("usuario1","senha1");
                UsuarioEntity usuario2 = new UsuarioEntity("usuario2","senha2");

                List<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();
                usuarios.add(usuario1);
                usuarios.add(usuario2);

                JsonData.GravarJson(usuarios, UsuarioEntity.TABLE_NAME);

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

                List<TransporteEntity> transportes = new ArrayList<TransporteEntity>();
                transportes.add(transporte1usuario1);
                transportes.add(transporte2usuario1);
                transportes.add(transporte1usuario2);
                transportes.add(transporte2usuario2);

                JsonData.GravarJson(transportes, TransporteEntity.TABLE_NAME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
