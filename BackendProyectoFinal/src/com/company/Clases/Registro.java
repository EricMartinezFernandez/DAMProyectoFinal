package com.company.Clases;

public class Registro {
    private String hora;
    private String accion;
    private String usuario;

    public Registro() {
    }

    public Registro(String hora, String accion, String usuario) {
        this.hora = hora;
        this.accion = accion;
        this.usuario = usuario;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
