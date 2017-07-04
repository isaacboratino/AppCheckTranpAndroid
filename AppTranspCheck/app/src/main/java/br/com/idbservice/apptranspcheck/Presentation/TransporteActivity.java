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
import java.io.IOException;

import br.com.idbservice.apptranspcheck.Application.IPostTaskListener;
import br.com.idbservice.apptranspcheck.Domain.Entities.TransporteEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.FileConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.ThirdPart.TranspCheckServer.ConsumeServer;
import br.com.idbservice.apptranspcheck.R;

public class TransporteActivity extends BaseActivity {

    private TextView origemTextView, destinoTextView;
    private ImageView canhotoImageView, assinaturaImageView;

    private Button canhotoButton, assinaturaButton, enviarButton;

    private Uri canhotoImageUri, canhotoImageTempFile, assinaturaImageUri;

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
                this.canhotoImageView.setImageURI(this.canhotoImageUri);
                this.canhotoImageView.setVisibility(View.VISIBLE);
                break;
            case INTENT_REQUEST_CODE_ASSINATURA :
                if (data != null) {
                    this.assinaturaImageUri = (Uri) data.getExtras().get("imgAssinatura");
                    this.assinaturaImageView.setImageURI(this.assinaturaImageUri);
                    this.assinaturaImageView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void inicializarComponentes() {

        this.origemTextView = (TextView) findViewById(R.id.origemTextView);
        this.destinoTextView = (TextView) findViewById(R.id.destinoTextView);

        this.canhotoImageView = (ImageView) findViewById(R.id.canhotoImageView);
        this.assinaturaImageView = (ImageView) findViewById(R.id.assinaturaImageView);

        this.canhotoButton = (Button) findViewById(R.id.canhotoButton);
        this.assinaturaButton = (Button) findViewById(R.id.assinaturaButton);
        this.enviarButton = (Button) findViewById(R.id.enviarButton);

        this.canhotoButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                try {
                    recuperarFotoCanhoto();
                } catch (Exception e) {
                    tratarException(e);
                }
            }
        });

        this.assinaturaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recuperarFotoAssinatura();
                } catch (Exception e) {
                    tratarException(e);
                }
            }
        });

        this.enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    enviarArquivos();
                } catch (Exception e) {
                    tratarException(e);
                }
            }
        });

        super.inicializar();
    }

    private void recuperarTransporteUsuario() throws Exception {
        try {

            /*TransporteEntity transporte = new TransporteData().findStatusByIdUsuario(TransporteEntity.STATUS_ATIVO,
                    BaseActivity.ID_USUARIO.toString());*/

            IPostTaskListener<Object> postTaskListener = new IPostTaskListener<Object>() {
                @Override
                public void onPostTask(Object result) {
                    TransporteEntity transporte = (TransporteEntity) result;

                    if (transporte != null) {

                        origemTextView.setText(transporte.getEnderecoOrigem());
                        destinoTextView.setText(transporte.getEnderecoDestino());

                    } else {
                        TransporteActivity.super.tratarException(new Exception("Não foi encontrado nenhum transporte ativo para esse usuario."));
                    }
                }
            };

            ConsumeServer.sendJson(getString(R.string.url_transporte)+"?u="+BaseActivity.ID_USUARIO,
                    null, TransporteEntity.class, "GET", postTaskListener);

        } catch (Exception e) {
            throw new Exception("Erro ao tentar recuperar transporte ativo do usuario ", e);
        }
    }

    public void recuperarFotoCanhoto() throws Exception  {

        Intent cameraIntentView = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = criarImagemTemporaria();

        if (photoFile != null) {

            try {
                //String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                canhotoImageTempFile = Uri.fromFile(photoFile.getAbsoluteFile());
                canhotoImageUri = FileProvider.getUriForFile(getApplicationContext(), getString(R.string.app_full_package), photoFile);

                cameraIntentView.putExtra(MediaStore.EXTRA_OUTPUT, canhotoImageUri);

                this.startActivityForResult(cameraIntentView, INTENT_REQUEST_CODE_CANHOTO);

            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    private File criarImagemTemporaria() throws Exception {
        return FileConcerns.criarArquivoTemporario(getNomeImageCanhoto(), ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    private String getNomeImageCanhoto() {
        return "canhoto_" + BaseActivity.ID_USUARIO + "_";
    }

    private void recuperarFotoAssinatura() throws Exception  {
        try {
            Intent inkIntentView = new Intent(this, InkActivity.class);
            startActivityForResult(inkIntentView, INTENT_REQUEST_CODE_ASSINATURA);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private void enviarArquivos() throws IOException {

        if (canhotoImageUri == null || canhotoImageTempFile == null) {
            showAlert(TransporteActivity.this,"Atenção","A foto do canhoto é necessaria");
        } else if (assinaturaImageUri == null) {
            showAlert(TransporteActivity.this,"Atenção","A assinatura do canhoto é necessaria");
        } else {
            toggleDialogWait(true);

            IPostTaskListener<Object> postTaskListener = new IPostTaskListener<Object>() {

                @Override
                public void onPostTask(Object object) {
                    toggleDialogWait(false);
                    showAlert(TransporteActivity.this,"Sucesso","Imagens enviadas com sucesso");
                }
            };

            ConsumeServer.sendMultPartBuilder(getString(R.string.url_transporte)+"?u="+BaseActivity.ID_USUARIO,
                    new String[]{this.canhotoImageTempFile.getPath(), this.assinaturaImageUri.getEncodedPath()}, postTaskListener);
        }
    }
}
