package br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.idbservice.apptranspcheck.Presentation.TransporteActivity;
import br.com.idbservice.apptranspcheck.R;

public class CameraImageConcerns {

    private TransporteActivity currentActivity;
    private String diretorioFoto;
    private Uri fotoURI;

    static final int REQUEST_TAKE_PHOTO = 1;

    public void abrirCamera(TransporteActivity currentActivity) {

        this.currentActivity = currentActivity;

        Intent cameraIntentView = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntentView.resolveActivity(currentActivity.getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = criarArquivoDeImagem();
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }

            if (photoFile != null) {

                try {

                    this.fotoURI = FileProvider.getUriForFile(currentActivity.getApplicationContext(),
                            currentActivity.getString(R.string.app_full_package),
                            photoFile);
                    cameraIntentView.putExtra(MediaStore.EXTRA_OUTPUT, this.fotoURI);
                    this.currentActivity.startActivityForResult(cameraIntentView, REQUEST_TAKE_PHOTO);

                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    private File criarArquivoDeImagem() throws IOException {

        String imageFileName = "canhoto_" + this.currentActivity.getIdUsuario() + "_";
        File storageDir = currentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        diretorioFoto = image.getAbsolutePath();
        return image;
    }

    public void activityResultProvider(int requestCode, int resultCode, Intent data) {
        try {
            this.currentActivity.getImgCanhoto().setImageURI(this.fotoURI);
            this.currentActivity.getImgCanhoto().setVisibility(View.VISIBLE);
        } catch (Exception e) {
            System.out.print(e);
        }

    }
}
