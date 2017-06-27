package br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class CameraImageConcerns {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private AppCompatActivity currentActivity;
    private ImageView imageContainer;

    private void abrirCamera(ImageView imageContainer, AppCompatActivity currentActivity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (takePictureIntent.resolveActivity(currentActivity.getPackageManager()) != null) {
            currentActivity.start.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == currentActivity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgCanhoto.setImageBitmap(imageBitmap);
        }
    }
}
