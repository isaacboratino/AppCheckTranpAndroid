package br.com.idbservice.apptranspcheck.Presentation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import br.com.idbservice.apptranspcheck.Entities.UsuarioEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.InitData;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;
import br.com.idbservice.apptranspcheck.R;


public class LoginActivity extends AppCompatActivity  {

    private UserLoginTask userLoginTask = null;

    private AutoCompleteTextView textUsuarioView;
    private EditText textSenhaView;
    private View loginProgress;
    private View loginScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Carga inicial caso nao exista
        InitData.CargaInicial();

        setContentView(R.layout.activity_login);

        textUsuarioView = (AutoCompleteTextView) findViewById(R.id.textUsuario);

        textSenhaView = (EditText) findViewById(R.id.textSenha);
        textSenhaView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginScroll = findViewById(R.id.loginScroll);
        loginProgress = findViewById(R.id.loginProgress);
    }

    private void attemptLogin() {
        if (userLoginTask != null) {
            return;
        }

        textUsuarioView.setError(null);
        textSenhaView.setError(null);

        String email = textUsuarioView.getText().toString();
        String password = textSenhaView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            textSenhaView.setError(getString(R.string.error_invalid_password));
            focusView = textSenhaView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            textUsuarioView.setError(getString(R.string.error_field_required));
            focusView = textUsuarioView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            textUsuarioView.setError(getString(R.string.error_invalid_email));
            focusView = textUsuarioView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            userLoginTask = new UserLoginTask(email, password);
            userLoginTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {

        return true; //email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() >= 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginScroll.setVisibility(show ? View.GONE : View.VISIBLE);
            loginScroll.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginScroll.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            loginScroll.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private UUID idUsuario;
        private final String textUsuario;
        private final String textSenha;

        UserLoginTask(String usuario, String senha) {
            this.textUsuario = usuario;
            this.textSenha = senha;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean usuarioValido = false;

            try {
                // Simulate network access.
                Thread.sleep(2000);

                List<UsuarioEntity> usuarios = (List<UsuarioEntity>)JsonData.lerJson(UsuarioEntity.TABLE_NAME);

                for (int i = 0; i < usuarios.size(); i++) {

                    UsuarioEntity usuario = JsonData.mapper.convertValue(usuarios.get(i), UsuarioEntity.class);

                    if (usuario.getUsuario().equals(textUsuario)) {
                        this.idUsuario = usuario.getId();
                        usuarioValido = usuario.getSenha().equals(textSenha);
                        break;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return usuarioValido;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            userLoginTask = null;
            showProgress(false);

            if (success) {

                Intent myIntent = new Intent(getApplicationContext(), TransporteActivity.class);
                myIntent.putExtra("idUsuario",this.idUsuario);
                startActivity(myIntent);

            } else {
                textSenhaView.setError(getString(R.string.error_incorrect_password));
                textSenhaView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            userLoginTask = null;
            showProgress(false);
        }
    }
}