package br.com.idbservice.apptranspcheck.Domain.Entities;

public class UsuarioLoginResponseEntity {

    private String name;
    private String key;
    private String type;

    public UsuarioLoginResponseEntity()
    {
        super();
    }

    public UsuarioLoginResponseEntity(String name, String key, String type) {
        super();
        this.name = name;
        this.key = key;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }
}
