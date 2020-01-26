package com.company.Clases;

public class Mantenimiento {

    private String codigo;
    private String descripcion;
    private String codigoMaquina;
    private int duracion;


    public Mantenimiento() {
    }

    public Mantenimiento(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Mantenimiento(String codigo, String descripcion, String codigoMaquina) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.codigoMaquina = codigoMaquina;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
