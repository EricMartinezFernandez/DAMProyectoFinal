package com.company.Clases;

public class TrabajoMantenimiento {

    private int codigo;
    private int duracion;
    private int fechaRealizacion;
    private String dniTrabajador;
    private String codigoMantenimiento;

    public TrabajoMantenimiento() {
    }

    public TrabajoMantenimiento(int duracion, int fechaRealizacion) {
        this.duracion = duracion;
        this.fechaRealizacion = fechaRealizacion;
    }

    public TrabajoMantenimiento(int duracion, int fechaRealizacion, String dniTrabajador, String codigoMantenimiento) {
        this.duracion = duracion;
        this.fechaRealizacion = fechaRealizacion;
        this.dniTrabajador = dniTrabajador;
        this.codigoMantenimiento = codigoMantenimiento;
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

    public String getCodigoMantenimiento() {
        return codigoMantenimiento;
    }

    public void setCodigoMantenimiento(String codigoMantenimiento) {
        this.codigoMantenimiento = codigoMantenimiento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
