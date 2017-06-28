package br.com.idbservice.apptranspcheck.Presentation;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import br.com.idbservice.apptranspcheck.R;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BaseActivity extends AppCompatActivity {

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

}
