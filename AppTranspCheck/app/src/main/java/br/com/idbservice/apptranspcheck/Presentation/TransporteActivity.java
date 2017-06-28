package br.com.idbservice.apptranspcheck.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.idbservice.apptranspcheck.Entities.TransporteEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.CameraImageConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;
import br.com.idbservice.apptranspcheck.R;

public class TransporteActivity extends BaseActivity {

    private String IdUsuario;

    private TextView textOrigemView;
    private TextView textDestinoView;
    private ImageView imgCanhoto;
    private ImageView imgAssinatura;

    private Button btnCanhoto;
    private Button btnAssinatura;
    private Button btnEnviar;

    private CameraImageConcerns camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);

        super.exibirLogo();
        this.inicializarComponentes();
        this.recuperarTransporteUsuario();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        camera.activityResultProvider(requestCode, resultCode, data);
    }

    private void inicializarComponentes() {

        this.textOrigemView = (TextView) findViewById(R.id.textOrigem);
        this.textDestinoView = (TextView) findViewById(R.id.textDestino);

        this.imgCanhoto = (ImageView) findViewById(R.id.imgCanhoto);
        this.imgAssinatura = (ImageView) findViewById(R.id.imgAssinatura);

        this.btnCanhoto = (Button) findViewById(R.id.btnCanhoto);
        this.btnAssinatura = (Button) findViewById(R.id.btnAssinatura);
        this.btnEnviar = (Button) findViewById(R.id.btnEnviar);

        this.btnCanhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarFotoCanhoto();
            }
        });

        this.camera = new CameraImageConcerns();
    }

    private void recuperarTransporteUsuario() {
        try {
            // Recuperar parametros enviados pela view anterior
            Intent myIntent = getIntent(); // gets the previously created intent
            this.setIdUsuario(myIntent.getExtras().get("idUsuario").toString());

            List<TransporteEntity> transportes = (List<TransporteEntity>) JsonData.lerJson(TransporteEntity.TABLE_NAME);
            for (int i = 0; i < transportes.size(); i++) {

                TransporteEntity transporte = JsonData.mapper.convertValue(transportes.get(i), TransporteEntity.class);

                // Recuperar transporte ativo do usuario
                if (transporte.getUsuarioEntity().getId().toString().equals(this.getIdUsuario()) &&
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

    private void recuperarFotoCanhoto() {
        camera.abrirCamera(this);
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public ImageView getImgCanhoto() {
        return imgCanhoto;
    }
}
