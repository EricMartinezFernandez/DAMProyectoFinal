package com.company;

import com.company.Clases.Trabajador;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hola mundo");
        LlamadasBD llamadasBD = new LlamadasBD();

        llamadasBD.ReiniciarBasesDeDatos();
        Trabajador trabajador = new Trabajador();
        trabajador.setNombre("Eric");
        trabajador.setApellido1("Martínez");
        trabajador.setApellido2("Fernández");
        trabajador.setDni("72855640F");
        trabajador.setRutaFoto("Ruta desconocida");

        llamadasBD.InsertarTrabajador(trabajador);
    }
}
