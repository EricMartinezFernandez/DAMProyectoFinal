package com.company.Clases;

public class Tarea {
    private String codigo;
    private String descripcion;
    private boolean maquina;

    public Tarea() {
    }

    public Tarea(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Tarea(String codigo, String descripcion, boolean maquina) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.maquina = maquina;
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

    public boolean isMaquina() {
        return maquina;
    }

    public void setMaquina(boolean maquina) {
        this.maquina = maquina;
    }
}
