package br.com.idbservice.apptranspcheck.Presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

import br.com.idbservice.apptranspcheck.R;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BaseActivity extends AppCompatActivity {

    public static UUID ID_USUARIO;
    private ProgressDialog progress;

    public void inicializar() {
        progress = new ProgressDialog(this);
    }

    protected void exibirLogo() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_caminhao);
    }

    protected boolean verificaPermissoes() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            }
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void toggleDialogWait(Boolean show) {
        progress.setMessage("Aguarde ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        if (show)
            progress.show();
        else
            progress.cancel();
    }

    public void showAlert(Context cotext, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cotext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void tratarException(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(e.getClass().getName(), e.getMessage(), e);
    }
}
