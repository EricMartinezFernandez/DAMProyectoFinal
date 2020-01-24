package com.company.Clases;

public class Usuario {
    private String username;
    private String password;
    private int permisos;

    public Usuario() {
    }

    public Usuario(String username, String password, int permisos) {
        this.username = username;
        this.password = password;
        this.permisos = permisos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermisos() {
        return permisos;
    }

    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }
}
