package br.com.idbservices.transpcheckserver.Domain.Entities;

import java.util.UUID;

public class TransporteEntity {

    public static final char STATUS_AGUARDANDO = 'G';
    public static final char STATUS_ATIVO = 'A';
    public static final char STATUS_CONCLUIDO = 'C';

    private UUID id;
    private String enderecoOrigem;
    private String enderecoDestino;
    private String imagemCanhoto;
    private String imagemAssinatura;
    private char status;

    private UsuarioEntity usuarioEntity;

    public TransporteEntity() {
        this.setId(UUID.randomUUID());
        this.setEnderecoOrigem(new String());
        this.setEnderecoDestino(new String());
        this.setImagemCanhoto(new String());
        this.setImagemAssinatura(new String());
        this.setStatus(TransporteEntity.STATUS_AGUARDANDO);
        this.setUsuarioEntity(new UsuarioEntity());
    }

    public TransporteEntity(String enderecoOrigem, String enderecoDestino, String imagemCanhoto, String imagemAssinatura,
                            char status, UsuarioEntity usuarioEntity) {
        this.setId(UUID.randomUUID());
        this.setEnderecoOrigem(enderecoOrigem);
        this.setEnderecoDestino(enderecoDestino);
        this.setImagemCanhoto(imagemCanhoto);
        this.setImagemAssinatura(imagemAssinatura);
        this.setStatus(status);
        this.setUsuarioEntity(usuarioEntity);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEnderecoOrigem() {
        return enderecoOrigem;
    }

    public void setEnderecoOrigem(String enderecoOrigem) {
        this.enderecoOrigem = enderecoOrigem;
    }

    public String getEnderecoDestino() {
        return enderecoDestino;
    }

    public void setEnderecoDestino(String enderecoDestino) {
        this.enderecoDestino = enderecoDestino;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public String getImagemCanhoto() {
        return imagemCanhoto;
    }

    public void setImagemCanhoto(String imagemCanhoto) {
        this.imagemCanhoto = imagemCanhoto;
    }

    public String getImagemAssinatura() {
        return imagemAssinatura;
    }

    public void setImagemAssinatura(String imagemAssinatura) {
        this.imagemAssinatura = imagemAssinatura;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}
