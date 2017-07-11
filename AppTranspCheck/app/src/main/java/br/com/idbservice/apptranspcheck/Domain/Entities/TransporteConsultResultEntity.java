package br.com.idbservice.apptranspcheck.Domain.Entities;


public class TransporteConsultResultEntity {

    public static final String TABLE_NAME = "transportes";

    public static final char STATUS_AGUARDANDO = 'G';
    public static final char STATUS_ATIVO = 'A';
    public static final char STATUS_CONCLUIDO = 'C';

    private String origin;
    private String dest;
    private String label;
    private long dispatchTimeMs;
    private String motoristName;

    public TransporteConsultResultEntity()
    {
        super();
    }

    public TransporteConsultResultEntity(String origin, String dest, String label, long dispatchTimeMs, String motoristName) {
        super();
        this.origin = origin;
        this.dest = dest;
        this.label = label;
        this.dispatchTimeMs = dispatchTimeMs;
        this.motoristName = motoristName;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDest() {
        return dest;
    }

    public String getLabel() {
        return label;
    }

    public long getDispatchTimeMs() {
        return dispatchTimeMs;
    }

    public String getMotoristName() {
        return motoristName;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDispatchTimeMs(long dispatchTimeMs) {
        this.dispatchTimeMs = dispatchTimeMs;
    }

    public void setMotoristName(String motoristName) {
        this.motoristName = motoristName;
    }
}
