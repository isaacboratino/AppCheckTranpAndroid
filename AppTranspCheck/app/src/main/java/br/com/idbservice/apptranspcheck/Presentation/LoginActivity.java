package br.com.idbservice.apptranspcheck.Presentation;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.idbservice.apptranspcheck.Application.IPostTaskListener;
import br.com.idbservice.apptranspcheck.Domain.Entities.UsuarioLoginResponseEntity;
import br.com.idbservice.apptranspcheck.Domain.Entities.UsuarioLoginTryEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.ValidationConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.ThirdPart.TranspCheckServer.ConsumeServer;
import br.com.idbservice.apptranspcheck.R;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class LoginActivity extends BaseActivity  {

    private AutoCompleteTextView textUsuarioView;
    private EditText textSenhaView;
    private View loginProgress;
    private View loginScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        super.verificaPermissoes();
        super.exibirLogo();

        // Carga inicial caso nao exista
        //InitData.cargaInicial();

        this.inicializarComponentes();
    }

    private void inicializarComponentes() {

        textUsuarioView = (AutoCompleteTextView) findViewById(R.id.textUsuario);

        this.textSenhaView = (EditText) findViewById(R.id.textSenha);
        this.textSenhaView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    try {
                        logarUsuario();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logarUsuario();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        loginScroll = findViewById(R.id.loginScroll);
        loginProgress = findViewById(R.id.loginProgress);

        super.inicializar();
    }

    private void logarUsuario() throws Exception {

        textUsuarioView.setError(null);
        this.textSenhaView.setError(null);

        String usuario = textUsuarioView.getText().toString();
        String senha = this.textSenhaView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(senha)) {
            textSenhaView.setError(getString(R.string.error_field_required));
            focusView = textSenhaView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(senha) && !ValidationConcerns.isPasswordValid(senha)) {
            this.textSenhaView.setError(getString(R.string.error_invalid_password));
            focusView = this.textSenhaView;
            cancel = true;
        }

        if (TextUtils.isEmpty(usuario)) {
            textUsuarioView.setError(getString(R.string.error_field_required));
            focusView = textUsuarioView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();

        } else {

            toggleDialogWait(true);

            IPostTaskListener<Object> postTaskListener = new IPostTaskListener<Object>() {

                @Override
                public void onPostTask(Object object) {

                    toggleDialogWait(false);

                    if (object == null) {
                        tratarException(new Exception(getString(R.string.msg_login_null)));

                    } else if (object.getClass().getCanonicalName().indexOf("Exception") > -1) {
                        tratarException((Exception) object);

                    } else {

                        UsuarioLoginResponseEntity usuarioRetorno = (UsuarioLoginResponseEntity) object;

                        if (usuarioRetorno != null) {
                            BaseActivity.KEY_USUARIO = usuarioRetorno.getKey();

                            Intent myIntent = new Intent(getApplicationContext(), TransporteActivity.class);
                            startActivity(myIntent);

                        } else {
                            textSenhaView.setError(getString(R.string.error_incorrect_password));
                            textSenhaView.requestFocus();
                        }
                    }
                }
            };

            ConsumeServer.sendLoginJson(getString(R.string.url_auth),
                    new UsuarioLoginTryEntity(usuario, senha), postTaskListener);
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("log","Permission is granted");
                return true;
            } else {

                Log.v("log","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("log","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("log","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

}