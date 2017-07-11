package br.com.idbservice.apptranspcheck.Domain.Entities;

import java.util.UUID;

public class UsuarioLoginTryEntity {

    public static final String TABLE_NAME = "usuarios";

    private String login;
    private String password;
    private String key;

    public UsuarioLoginTryEntity() {
        this.setLogin(new String());
        this.setPassword(new String());
    }

    public UsuarioLoginTryEntity(String login, String password) {
        this.setLogin(login);
        this.setPassword(password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
