package br.com.idbservice.apptranspcheck.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.simplify.ink.InkView;

import java.io.File;

import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.FileConcerns;
import br.com.idbservice.apptranspcheck.R;

public class InkActivity extends BaseActivity {

    private InkView inkView;
    private FloatingActionButton fabClose;
    private FloatingActionButton fabOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_ink);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            super.exibirLogo();
            this.inicializarComponentes();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void inicializarComponentes() {

        this.inkView = (InkView) findViewById(R.id.inkView);
        this.inkView.setColor(getResources().getColor(android.R.color.black));
        this.inkView.setMinStrokeWidth(1.5f);
        this.inkView.setMaxStrokeWidth(6f);

        this.fabClose = (FloatingActionButton) findViewById(R.id.fabClose);
        this.fabOk = (FloatingActionButton) findViewById(R.id.fabOk);

        this.fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InkActivity.this.finish();
            }
        });

        this.fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recuperarAssinatura();
                } catch (Exception e) {
                    tratarException(e);
                }
            }
        });
    }

    public void recuperarAssinatura() throws Exception  {

        Bitmap bitmap = this.inkView.getBitmap(getResources().getColor(R.color.colorWhite));

        File imgAssinatura = criarImagemTemporaria();

        FileConcerns.bitmapToJpg(bitmap, imgAssinatura);

        if (imgAssinatura != null) {

            try {

                /*Uri uriImageAssinatura = FileProvider.getUriForFile(getApplicationContext(),
                        getString(R.string.app_full_package),
                        imgAssinatura);*/

                Uri uriImageAssinatura = Uri.fromFile(imgAssinatura);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("imgAssinatura", uriImageAssinatura);
                setResult(Activity.RESULT_OK, returnIntent);

                InkActivity.this.finish();
                this.finish();

            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    private File criarImagemTemporaria() throws Exception {

        String nomeArquivo = "assinatura_" + BaseActivity.KEY_USUARIO.toString() + "_";

        return FileConcerns.criarArquivoTemporario(nomeArquivo, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }
}
