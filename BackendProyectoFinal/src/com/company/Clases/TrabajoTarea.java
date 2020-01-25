package com.company.Clases;

public class TrabajoTarea {
    private int codigo;
    private int duracion;
    private int fechaRealizacion;
    private String dniTrabajador;
    private String codigoTarea;
    private String codigoMaquina;

    public TrabajoTarea() {
    }

    public TrabajoTarea(int duracion, int fechaRealizacion) {
        this.duracion = duracion;
        this.fechaRealizacion = fechaRealizacion;
    }

    public TrabajoTarea(int duracion, int fechaRealizacion, String dniTrabajador, String codigoTarea) {
        this.duracion = duracion;
        this.fechaRealizacion = fechaRealizacion;
        this.dniTrabajador = dniTrabajador;
        this.codigoTarea = codigoTarea;
    }

    public TrabajoTarea(int codigo, int duracion, int fechaRealizacion, String dniTrabajador, String codigoTarea, String codigoMaquina) {
        this.codigo = codigo;
        this.duracion = duracion;
        this.fechaRealizacion = fechaRealizacion;
        this.dniTrabajador = dniTrabajador;
        this.codigoTarea = codigoTarea;
        this.codigoMaquina = codigoMaquina;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(int fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getDniTrabajador() {
        return dniTrabajador;
    }

    public void setDniTrabajador(String dniTrabajador) {
        this.dniTrabajador = dniTrabajador;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }
}