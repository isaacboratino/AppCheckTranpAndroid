package br.com.idbservice.apptranspcheck.Domain.Entities;

public class TransporteEndEntity {

    public static final String TABLE_NAME = "transportes";

    public static final char STATUS_AGUARDANDO = 'G';
    public static final char STATUS_ATIVO = 'A';
    public static final char STATUS_CONCLUIDO = 'C';

    private String key;
    private String photo;
    private String signature;

    public TransporteEndEntity()
    {
        super();
    }

    public TransporteEndEntity(String key, String photo, String signature)
    {
        super();
        this.key = key;
        this.photo = photo;
        this.signature = signature;
    }

    public String getKey() {
        return key;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSignature() {
        return signature;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
