package br.com.idbservice.apptranspcheck.Domain.Entities;

import java.util.UUID;

public class UsuarioEntity {

    public static final String TABLE_NAME = "usuarios";

    private UUID id;
    private String usuario;
    private String senha;

    public UsuarioEntity() {
        this.setId(UUID.randomUUID());
        this.setUsuario(new String());
        this.setSenha(new String());
    }

    public UsuarioEntity(String usuario, String senha) {
        this.setId(UUID.randomUUID());
        this.setUsuario(usuario);
        this.setSenha(senha);
    }

    public UsuarioEntity(UUID id, String usuario, String senha) {
        this.setId(UUID.randomUUID());
        this.setUsuario(usuario);
        this.setSenha(senha);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
