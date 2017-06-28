package br.com.idbservice.apptranspcheck.Application;

import android.content.Intent;
import android.os.AsyncTask;

import java.util.List;
import java.util.UUID;

import br.com.idbservice.apptranspcheck.Entities.UsuarioEntity;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;
import br.com.idbservice.apptranspcheck.Presentation.LoginActivity;
import br.com.idbservice.apptranspcheck.Presentation.TransporteActivity;
import br.com.idbservice.apptranspcheck.R;

public class LoginTaskApplication {

    private UUID idUsuario;
    private final String textUsuario;
    private final String textSenha;
    private final LoginActivity currentActivity;

    public LoginTaskApplication(String usuario, String senha, LoginActivity currentActivity) {

        this.textUsuario = usuario;
        this.textSenha = senha;
        this.currentActivity = currentActivity;
    }

    public AsyncTask<Void, Void, Boolean> loginAsync() {

        return new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {

                boolean usuarioValido = false;

                try {
                    // Simulate network access.
                    Thread.sleep(2000);

                    List<UsuarioEntity> usuarios = (List<UsuarioEntity>) JsonData.lerJson(UsuarioEntity.TABLE_NAME);

                    for (int i = 0; i < usuarios.size(); i++) {

                        UsuarioEntity usuario = JsonData.mapper.convertValue(usuarios.get(i), UsuarioEntity.class);

                        if (usuario.getUsuario().equals(textUsuario)) {
                            idUsuario = usuario.getId();
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
                currentActivity.setUserLoginTask(null);
                currentActivity.showProgress(false);

                if (success) {

                    Intent myIntent = new Intent(currentActivity.getApplicationContext(), TransporteActivity.class);
                    myIntent.putExtra("idUsuario", idUsuario);
                    currentActivity.startActivity(myIntent);

                } else {
                    currentActivity.getTextSenhaView().setError(currentActivity.getString(R.string.error_incorrect_password));
                    currentActivity.getTextSenhaView().requestFocus();
                }
            }

            @Override
            protected void onCancelled() {
                currentActivity.setUserLoginTask(null);
                currentActivity.showProgress(false);
            }
        };
    }
}
