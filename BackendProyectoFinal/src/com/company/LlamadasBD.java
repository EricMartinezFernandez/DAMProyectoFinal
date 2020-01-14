package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LlamadasBD {

    public void ReiniciarBasesDeDatos(){
        Connection con = Conexion();

        BorrarBasesDeDatos(con);
        CrearBasesDeDatos(con);

    }

    private static Connection Conexion(){
        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestiontrabajo", "root", "");

        }catch (Exception e) {
            System.err.println("No se ha podido conectar a la base de datos GestionTrabajo\n"+e.getMessage());
        }

        return con;
    }

    public void BorrarBasesDeDatos(Connection con){

        PreparedStatement preparedStatement = null;

        String borrarTRABAJADORES =  "DROP TABLE IF EXISTS TRABAJADORES";
        String borrarMAQUINAS =  "DROP TABLE IF EXISTS MAQUINAS";
        String borrarMANTENIMIENTOS =  "DROP TABLE IF EXISTS MANTENIMIENTOS";
        String borrarTAREAS =  "DROP TABLE IF EXISTS TAREAS";
        String borrarTRABAJOTAREAS =  "DROP TABLE IF EXISTS TRABAJOTAREAS";
        String borrarTRABAJOMANTENIMIENTO =  "DROP TABLE IF EXISTS TRABAJOMANTENIMIENTO";

        try{
            preparedStatement = con.prepareStatement(borrarTRABAJOMANTENIMIENTO);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOMANTENIMIENT eliminada correctamente.");

        }catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJOMANTENIMIENT.");
            e.getMessage();
        }

        try{
            preparedStatement = con.prepareStatement(borrarTRABAJOTAREAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOTAREAS eliminada correctamente.");

        }catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJOTAREAS.");
            e.getMessage();
        }

        try{
            preparedStatement = con.prepareStatement(borrarTAREAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TAREAS eliminada correctamente.");

        }catch (Exception e) {
            System.err.println("Error al eliminar la tabla TAREAS.");
            e.getMessage();
        }


        try{
            preparedStatement = con.prepareStatement(borrarMANTENIMIENTOS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MANTENIMIENTOS eliminada correctamente.");

        }catch (Exception e) {
            System.err.println("Error al eliminar la tabla MANTENIMIENTOS.");
            e.getMessage();
        }


        try{
            preparedStatement = con.prepareStatement(borrarMAQUINAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MAQUINAS eliminada correctamente.");

        }catch (Exception e) {
            System.err.println("Error al eliminar la tabla MAQUINAS.");
            e.getMessage();
        }


        try{
            preparedStatement = con.prepareStatement(borrarTRABAJADORES);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJADORES eliminada correctamente.");

        }catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJADORES.");
            e.getMessage();
        }


    }

    public void CrearBasesDeDatos(Connection con){
        /*
    ORIGINAL
    • Trabajador: DNI, nombre, primer apellido, segundo apellidos y foto.
    • Mantenimiento: código y descripción.
    • Tarea: código y descripción.
    • Máquina: código y descripción.

    MODIFICADO
    • Trabajador: DNI, nombre, primer apellido, segundo apellidos y foto.
    • Máquina: código, descripción.
    • Mantenimiento: código, descripción.
    • Tarea: código, descripción.
    • TrabajoTarea: Código (Auto incremental), DNI (Trabajador), Código (Tarea), tiempo(En formato int).
    • TrabajoMantenimiento: Código (Auto incremental), DNI (Trabajador), Código (Mantenimiento), tiempo(En formato int).
 */

        PreparedStatement preparedStatement = null;

        String createTrabajadores = "CREATE TABLE TRABAJADORES(" +
                "DNI VARCHAR(9) PRIMARY KEY NOT NULL," +
                "NOMBRE VARCHAR(255) NOT NULL," +
                "APELLIDO1 VARCHAR(255) NOT NULL," +
                "APELLIDO2 VARCHAR(255)," +
                "FOTO VARCHAR(255));";

        String createMaquinas = "CREATE TABLE MAQUINAS(" +
                "CODIGO VARCHAR(255) PRIMARY KEY NOT NULL," +
                "DESCRIPCION VARCHAR(255));";

        String createMantenimientos = "CREATE TABLE MANTENIMIENTOS(" +
                "CODIGO VARCHAR(255) PRIMARY KEY NOT NULL," +
                "DESCRIPCION VARCHAR(255)," +
                "CODIGOMAQUINA VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (CODIGOMAQUINA) REFERENCES MAQUINAS(CODIGO) ON DELETE CASCADE);";

        String createTareas = "CREATE TABLE TAREAS(" +
                "CODIGO VARCHAR(255) PRIMARY KEY NOT NULL," +
                "DESCRIPCION VARCHAR(255)," +
                "CODIGOMAQUINA VARCHAR(255)," +
                "FOREIGN KEY (CODIGOMAQUINA) REFERENCES MAQUINAS(CODIGO) ON DELETE CASCADE);";

        String createTrabajoTareas = "CREATE TABLE TRABAJOTAREAS(" +
                "CODIGO INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "TIEMPO INTEGER NOT NULL," +
                "DNITRABAJADOR VARCHAR(9) NOT NULL," +
                "CODIGOTAREA VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (DNITRABAJADOR) REFERENCES TRABAJADORES(DNI) ON DELETE CASCADE," +
                "FOREIGN KEY (CODIGOTAREA) REFERENCES TAREAS(CODIGO) ON DELETE CASCADE);";

        String createTrabajoMantenimientos = "CREATE TABLE TRABAJOMANTENIMIENTO(" +
                "CODIGO INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "TIEMPO INTEGER NOT NULL," +
                "DNITRABAJADOR VARCHAR(9) NOT NULL," +
                "CODIGOMANTENIMIENTO VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (DNITRABAJADOR) REFERENCES TRABAJADORES(DNI) ON DELETE CASCADE," +
                "FOREIGN KEY (CODIGOMANTENIMIENTO) REFERENCES MANTENIMIENTOS(CODIGO) ON DELETE CASCADE);";

        //TABLA TRABAJADORES
        try{
            preparedStatement = con.prepareStatement(createTrabajadores);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJADORES creada correctamente.");

        }catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJADORES.\n"+e.getMessage());
        }

        //TABLA MAQUINAS
        try{
            preparedStatement = con.prepareStatement(createMaquinas);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MAQUINAS creada correctamente.");

        }catch (Exception e) {
            System.err.println("No se han podido crear la tabla MÁQUINAS.\n"+e.getMessage());
        }


        //TABLA MANTENIMIENTOS
        try{
            preparedStatement = con.prepareStatement(createMantenimientos);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MANTENIMIENTOS creada correctamente.");

        }catch (Exception e) {
            System.err.println("No se han podido crear la tabla MANTENIMIENTOS.\n"+e.getMessage());
        }


        //TABLA TAREAS
        try{
            preparedStatement = con.prepareStatement(createTareas);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TAREAS creada correctamente.");

        }catch (Exception e) {
            System.err.println("No se han podido crear la tabla TAREAS.\n"+e.getMessage());
        }

        //TABLA TRABAJOTAREAS
        try{
            preparedStatement = con.prepareStatement(createTrabajoTareas);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOTAREAS creada correctamente.");

        }catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJOTAREAS.\n"+e.getMessage());
        }

        //TABLA TRABAJOMANTENIMIENTOS
        try{
            preparedStatement = con.prepareStatement(createTrabajoMantenimientos);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOMANTENIMIENTOS creada correctamente.");

        }catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJOMANTENIMIENTOS.\n"+e.getMessage());
        }





    }

}
