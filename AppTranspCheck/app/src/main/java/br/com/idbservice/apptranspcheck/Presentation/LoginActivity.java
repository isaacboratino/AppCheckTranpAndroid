package br.com.idbservice.apptranspcheck.Presentation;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

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

import br.com.idbservice.apptranspcheck.Application.LoginTaskApplication;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.ValidationConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.InitData;
import br.com.idbservice.apptranspcheck.R;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class LoginActivity extends BaseActivity  {

    private AsyncTask<Void, Void, Boolean> userLoginTask = null;
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
        InitData.cargaInicial();

        this.inicializarComponentes();
    }

    private void inicializarComponentes() {
        textUsuarioView = (AutoCompleteTextView) findViewById(R.id.textUsuario);

        setTextSenhaView((EditText) findViewById(R.id.textSenha));
        getTextSenhaView().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    logarUsuario();
                    return true;
                }
                return false;
            }
        });

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                logarUsuario();
            }
        });

        loginScroll = findViewById(R.id.loginScroll);
        loginProgress = findViewById(R.id.loginProgress);
    }

    private void logarUsuario() {
        if (getUserLoginTask() != null) {
            return;
        }

        textUsuarioView.setError(null);
        getTextSenhaView().setError(null);

        String usuario = textUsuarioView.getText().toString();
        String senha = getTextSenhaView().getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(senha) && !ValidationConcerns.isPasswordValid(senha)) {
            getTextSenhaView().setError(getString(R.string.error_invalid_password));
            focusView = getTextSenhaView();
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
            showProgress(true);
            setUserLoginTask(new LoginTaskApplication(usuario, senha, this).loginAsync());
            getUserLoginTask().execute((Void) null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            /*loginScroll.setVisibility(show ? View.GONE : View.VISIBLE);
            loginScroll.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginScroll.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/

            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            //loginScroll.setVisibility(show ? View.GONE : View.VISIBLE);
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

    public AsyncTask<Void, Void, Boolean> getUserLoginTask() {
        return userLoginTask;
    }

    public void setUserLoginTask(AsyncTask<Void, Void, Boolean> userLoginTask) {
        this.userLoginTask = userLoginTask;
    }

    public EditText getTextSenhaView() {
        return textSenhaView;
    }

    public void setTextSenhaView(EditText textSenhaView) {
        this.textSenhaView = textSenhaView;
    }
}