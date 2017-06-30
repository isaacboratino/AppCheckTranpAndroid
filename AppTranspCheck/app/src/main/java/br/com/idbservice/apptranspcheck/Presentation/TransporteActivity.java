package br.com.idbservice.apptranspcheck.Presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.idbservice.apptranspcheck.Domain.Entities.TransporteEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.FileConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.TransporteData;
import br.com.idbservice.apptranspcheck.R;

public class TransporteActivity extends BaseActivity {

    private String idUsuario;

    private TextView textOrigemView;
    private TextView textDestinoView;
    private ImageView imgCanhoto;
    private ImageView imgAssinatura;

    private Button btnCanhoto;
    private Button btnAssinatura;
    private Button btnEnviar;

    private Uri uriImageAssinatura;

    static final int INTENT_REQUEST_CODE_CANHOTO = 1;
    static final int INTENT_REQUEST_CODE_ASSINATURA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);

        try {
            super.exibirLogo();
            this.inicializarComponentes();
            this.recuperarTransporteUsuario();
        } catch (Exception e) {
            super.tratarException(e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case INTENT_REQUEST_CODE_CANHOTO :
                this.imgCanhoto.setImageURI(this.uriImageAssinatura);
                this.imgCanhoto.setVisibility(View.VISIBLE);
                break;
            case INTENT_REQUEST_CODE_ASSINATURA :
                if (data != null) {
                    this.imgAssinatura.setImageURI((Uri)data.getExtras().get("imgAssinatura"));
                    this.imgAssinatura.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void inicializarComponentes() {

        this.textOrigemView = (TextView) findViewById(R.id.textOrigem);
        this.textDestinoView = (TextView) findViewById(R.id.textDestino);

        this.imgCanhoto = (ImageView) findViewById(R.id.imgCanhoto);
        this.imgAssinatura = (ImageView) findViewById(R.id.imgAssinatura);

        this.btnCanhoto = (Button) findViewById(R.id.btnCanhoto);
        this.btnAssinatura = (Button) findViewById(R.id.btnAssinatura);
        this.btnEnviar = (Button) findViewById(R.id.btnEnviar);

        this.btnCanhoto.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                try {
                    recuperarFotoCanhoto();
                } catch (Exception e) {
                    tratarException(e);
                }
            }
        });

        this.btnAssinatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recuperarFotoAssinatura();
                } catch (Exception e) {
                    tratarException(e);
                }
            }
        });
    }

    private void recuperarTransporteUsuario() throws Exception {
        try {
            // Recuperar parametros enviados pela view anterior
            this.idUsuario = getIntent().getExtras().get("idUsuario").toString();

            TransporteEntity transporte = new TransporteData().findStatusByIdUsuario(TransporteEntity.STATUS_ATIVO, idUsuario);

            if (transporte != null) {
                this.textOrigemView.setText(transporte.getEnderecoOrigem());
                this.textDestinoView.setText(transporte.getEnderecoDestino());
            } else
                throw new Exception("Não foi encontrado nenhum transporte ativo para esse usuario.");

        } catch (Exception e) {
            throw new Exception("Erro ao tentar recuperar transporte ativo do usuario ", e);
        }
    }

    public void recuperarFotoCanhoto() throws Exception  {

        Intent cameraIntentView = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = criarImagemTemporaria();

        if (photoFile != null) {

            try {

                uriImageAssinatura = FileProvider.getUriForFile(getApplicationContext(),
                        getString(R.string.app_full_package),
                        photoFile);

                cameraIntentView.putExtra(MediaStore.EXTRA_OUTPUT, uriImageAssinatura);

                this.startActivityForResult(cameraIntentView, INTENT_REQUEST_CODE_CANHOTO);

            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    private File criarImagemTemporaria() throws Exception {

        String nomeArquivo = "canhoto_" + this.idUsuario + "_";

        return FileConcerns.criarArquivoTemporario(nomeArquivo, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    private void recuperarFotoAssinatura() throws Exception  {
        try {
            Intent inkIntentView = new Intent(this, InkActivity.class);
            inkIntentView.putExtra("idUsuario",idUsuario);
            startActivityForResult(inkIntentView, INTENT_REQUEST_CODE_ASSINATURA);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

}
