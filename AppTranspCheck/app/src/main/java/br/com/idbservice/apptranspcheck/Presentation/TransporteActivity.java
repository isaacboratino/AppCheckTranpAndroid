package br.com.idbservice.apptranspcheck.Presentation;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import br.com.idbservice.apptranspcheck.Entities.TransporteEntity;
import br.com.idbservice.apptranspcheck.Entities.UsuarioEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;
import br.com.idbservice.apptranspcheck.R;

public class TransporteActivity extends AppCompatActivity {

    private EditText textOrigemView;
    private EditText textDestinoView;
    private ImageView imgCanhoto;
    private ImageView imgAssinatura;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);

        this.InicializarComponentes();
        dispatchTakePictureIntent();
        this.RecuperarTransporteUsuario();
    }

    private void InicializarComponentes() {
        this.textOrigemView = (EditText) findViewById(R.id.textOrigem);
        this.textDestinoView = (EditText) findViewById(R.id.textDestino);
        this.imgCanhoto = (ImageView) findViewById(R.id.imgCanhoto);
        this.imgAssinatura = (ImageView) findViewById(R.id.imgAssinatura);
    }

    private void RecuperarTransporteUsuario() {
        try {
            // Recuperar parametros enviados pela view anterior
            Intent myIntent = getIntent(); // gets the previously created intent
            String idUsuario = myIntent.getExtras().get("idUsuario").toString();

            List<TransporteEntity> transportes = (List<TransporteEntity>) JsonData.lerJson(TransporteEntity.TABLE_NAME);
            for (int i = 0; i < transportes.size(); i++) {

                TransporteEntity transporte = JsonData.mapper.convertValue(transportes.get(i), TransporteEntity.class);

                // Recuperar transporte ativo do usuario
                if (transporte.getUsuarioEntity().getId().toString().equals(idUsuario) &&
                        transporte.getStatus() == TransporteEntity.STATUS_ATIVO) {
                    this.textOrigemView.setText(transporte.getEnderecoOrigem());
                    this.textDestinoView.setText(transporte.getEnderecoDestino());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
