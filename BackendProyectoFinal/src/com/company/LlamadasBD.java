package com.company;

import com.company.Clases.*;

import java.sql.*;
import java.util.ArrayList;

public class LlamadasBD {

    public void ReiniciarBasesDeDatos() {
        Connection con = Conexion();

        BorrarBasesDeDatos(con);
        CrearBasesDeDatos(con);

    }

    private static Connection Conexion() {
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestiontrabajo", "root", "");

        } catch (Exception e) {
            System.err.println("No se ha podido conectar a la base de datos GestionTrabajo\n" + e.getMessage());
        }

        return con;
    }

    public void BorrarBasesDeDatos(Connection con) {

        PreparedStatement preparedStatement = null;

        String borrarTRABAJADORES = "DROP TABLE IF EXISTS TRABAJADORES";
        String borrarMAQUINAS = "DROP TABLE IF EXISTS MAQUINAS";
        String borrarMANTENIMIENTOS = "DROP TABLE IF EXISTS MANTENIMIENTOS";
        String borrarTAREAS = "DROP TABLE IF EXISTS TAREAS";
        String borrarTRABAJOTAREAS = "DROP TABLE IF EXISTS TRABAJOTAREAS";
        String borrarTRABAJOMANTENIMIENTO = "DROP TABLE IF EXISTS TRABAJOMANTENIMIENTO";

        try {
            preparedStatement = con.prepareStatement(borrarTRABAJOMANTENIMIENTO);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOMANTENIMIENT eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJOMANTENIMIENT.");
            e.getMessage();
        }

        try {
            preparedStatement = con.prepareStatement(borrarTRABAJOTAREAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOTAREAS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJOTAREAS.");
            e.getMessage();
        }

        try {
            preparedStatement = con.prepareStatement(borrarTAREAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TAREAS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TAREAS.");
            e.getMessage();
        }


        try {
            preparedStatement = con.prepareStatement(borrarMANTENIMIENTOS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MANTENIMIENTOS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla MANTENIMIENTOS.");
            e.getMessage();
        }


        try {
            preparedStatement = con.prepareStatement(borrarMAQUINAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MAQUINAS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla MAQUINAS.");
            e.getMessage();
        }


        try {
            preparedStatement = con.prepareStatement(borrarTRABAJADORES);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJADORES eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJADORES.");
            e.getMessage();
        }

        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void CrearBasesDeDatos(Connection con) {
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
                "DURACION INTEGER NOT NULL," +
                "FECHAREALIZACION INTEGER NOT NULL," +
                "DNITRABAJADOR VARCHAR(9) NOT NULL," +
                "CODIGOTAREA VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (DNITRABAJADOR) REFERENCES TRABAJADORES(DNI) ON DELETE CASCADE," +
                "FOREIGN KEY (CODIGOTAREA) REFERENCES TAREAS(CODIGO) ON DELETE CASCADE);";

        String createTrabajoMantenimientos = "CREATE TABLE TRABAJOMANTENIMIENTOS(" +
                "CODIGO INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "DURACION INTEGER NOT NULL," +
                "FECHAREALIZACION INTEGER NOT NULL," +
                "DNITRABAJADOR VARCHAR(9) NOT NULL," +
                "CODIGOMANTENIMIENTO VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (DNITRABAJADOR) REFERENCES TRABAJADORES(DNI) ON DELETE CASCADE," +
                "FOREIGN KEY (CODIGOMANTENIMIENTO) REFERENCES MANTENIMIENTOS(CODIGO) ON DELETE CASCADE);";

        //TABLA TRABAJADORES
        try {
            preparedStatement = con.prepareStatement(createTrabajadores);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJADORES creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJADORES.\n" + e.getMessage());
        }

        //TABLA MAQUINAS
        try {
            preparedStatement = con.prepareStatement(createMaquinas);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MAQUINAS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla MÁQUINAS.\n" + e.getMessage());
        }


        //TABLA MANTENIMIENTOS
        try {
            preparedStatement = con.prepareStatement(createMantenimientos);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MANTENIMIENTOS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla MANTENIMIENTOS.\n" + e.getMessage());
        }


        //TABLA TAREAS
        try {
            preparedStatement = con.prepareStatement(createTareas);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TAREAS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla TAREAS.\n" + e.getMessage());
        }

        //TABLA TRABAJOTAREAS
        try {
            preparedStatement = con.prepareStatement(createTrabajoTareas);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOTAREAS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJOTAREAS.\n" + e.getMessage());
        }

        //TABLA TRABAJOMANTENIMIENTOS
        try {
            preparedStatement = con.prepareStatement(createTrabajoMantenimientos);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOMANTENIMIENTOS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJOMANTENIMIENTOS.\n" + e.getMessage());
        }


        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //TRABAJADORES
    public void InsertarTrabajador(Trabajador nuevoTrabajador) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO TRABAJADORES (DNI, NOMBRE, APELLIDO1, APELLIDO2, FOTO)"
                    + " VALUES (?, ?, ?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, nuevoTrabajador.getDni());
            preparedStatement.setString(2, nuevoTrabajador.getNombre());
            preparedStatement.setString(3, nuevoTrabajador.getApellido1());
            preparedStatement.setString(4, nuevoTrabajador.getApellido2());
            preparedStatement.setString(5, nuevoTrabajador.getRutaFoto());
            preparedStatement.execute();

            System.out.println("Operación existosa");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<Trabajador> LeerTrabajadores() {

        ArrayList<Trabajador> trabajadores = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TRABAJADORES";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String foto = resultSet.getString("FOTO");


                Trabajador trabajador = new Trabajador(dni, nombre, apellido1, apellido2, foto);
                trabajadores.add(trabajador);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajadores;
    }

    public Trabajador LeerTrabajadorConcreto(String dniPK) {

        Trabajador trabajador = new Trabajador();

        try {
            Connection con = Conexion();

            String query = "SELECT * FROM TRABAJADORES WHERE DNI = '" + dniPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String foto = resultSet.getString("FOTO");


                trabajador = new Trabajador(dni, nombre, apellido1, apellido2, foto);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajador;
    }

    public void EliminarTrabajador(String dniPK){
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query =  "DELETE FROM TRABAJADORES WHERE DNI = '" + dniPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }

    }


    //MÁQUINAS
    public void InsertarMaquina(Maquina nuevaMaquina) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO MAQUINAS (CODIGO, DESCRIPCION)"
                    + " VALUES (?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, nuevaMaquina.getCodigo());
            preparedStatement.setString(2, nuevaMaquina.getDescripcion());

            preparedStatement.execute();

            System.out.println("Operación existosa");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<Maquina> LeerMaquinas() {

        ArrayList<Maquina> maquinas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MAQUINAS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");


                Maquina maquina = new Maquina(codigo, descripcion);
                maquinas.add(maquina);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return maquinas;
    }

    public Maquina LeerMaquinaConcreta(String codigoPK) {

        Maquina maquina = new Maquina();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MAQUINAS WHERE CODIGO = '" + codigoPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");


                maquina = new Maquina(codigo, descripcion);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return maquina;
    }

    public void EliminarMaquina(String codigoPK){
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query =  "DELETE FROM MAQUINAS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }

    }


    //MANTENIMIENTOS
    public void InsertarMantenimiento(Mantenimiento nuevoMantenimiento) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO MANTENIMIENTOS (CODIGO, DESCRIPCION, CODIGOMAQUINA)"
                    + " VALUES (?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, nuevoMantenimiento.getCodigo());
            preparedStatement.setString(2, nuevoMantenimiento.getDescripcion());
            preparedStatement.setString(3, nuevoMantenimiento.getCodigoMaquina());
            preparedStatement.execute();

            System.out.println("Operación existosa");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<Mantenimiento> LeerMantenimientos() {

        ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MANTENIMIENTOS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                Mantenimiento mantenimiento = new Mantenimiento(codigo, descripcion, codigoMaquina);
                mantenimientos.add(mantenimiento);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return mantenimientos;
    }

    public Mantenimiento LeerMantenimientoConcreto(String codigoPK) {

        Mantenimiento mantenimiento = new Mantenimiento();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MANTENIMIENTOS WHERE CODIGO = '" + codigoPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                mantenimiento = new Mantenimiento(codigo, descripcion, codigoMaquina);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return mantenimiento;
    }

    public void EliminarMantenimiento(String codigoPK){
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query =  "DELETE FROM MANTENIMIENTOS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }

    }


    //TAREAS
    public void InsertarTarea(Tarea nuevaTarea) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO TAREAS (CODIGO, DESCRIPCION, CODIGOMAQUINA)"
                    + " VALUES (?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, nuevaTarea.getCodigo());
            preparedStatement.setString(2, nuevaTarea.getDescripcion());
            preparedStatement.setString(3, nuevaTarea.getCodigoMaquina());
            preparedStatement.execute();

            System.out.println("Operación existosa");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<Tarea> LeerTareas() {

        ArrayList<Tarea> tareas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TAREAS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                Tarea tarea = new Tarea(codigo, descripcion, codigoMaquina);
                tareas.add(tarea);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return tareas;
    }

    public Tarea LeerTareaConcreta(String codigoPK) {

        Tarea tarea = new Tarea();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TAREAS WHERE CODIGO = '" + codigoPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                tarea = new Tarea(codigo, descripcion, codigoMaquina);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return tarea;
    }

    public void EliminarTarea(String codigoPK){
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query =  "DELETE FROM TAREAS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }

    }



    //TRABAJOTAREA
    public void InsertarTrabajoTarea(TrabajoTarea nuevoTrabajoTarea) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO TRABAJOTAREAS (DURACION, FECHAREALIZACION, DNITRABAJADOR, CODIGOTAREA)"
                    + " VALUES (?, ?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, nuevoTrabajoTarea.getDuracion());
            preparedStatement.setInt(2, nuevoTrabajoTarea.getFechaRealizacion());
            preparedStatement.setString(3, nuevoTrabajoTarea.getDniTrabajador());
            preparedStatement.setString(4, nuevoTrabajoTarea.getCodigoTarea());
            preparedStatement.execute();

            System.out.println("Operación existosa");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<TrabajoTarea> LeerTrabajoTareas() {

        ArrayList<TrabajoTarea> trabajoTareas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TRABAJOTAREAS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String duracion = resultSet.getString("DURACION");
                String fechaRealizacion = resultSet.getString("FECHAREALIZACION");
                String dniTrabajador = resultSet.getString("DNITRABAJADOR");
                String codigoTarea = resultSet.getString("CODIGOTAREA");

                TrabajoTarea trabajoTarea = new TrabajoTarea(Integer.parseInt(duracion), Integer.parseInt(fechaRealizacion), dniTrabajador, codigoTarea);
                trabajoTareas.add(trabajoTarea);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajoTareas;
    }

    public TrabajoTarea LeerTrabajoTareaConcreta(String codigoPK) {

        TrabajoTarea trabajoTarea = new TrabajoTarea();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TRABAJOTAREAS WHERE CODIGO = '" + codigoPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String duracion = resultSet.getString("DURACION");
                String fechaRealizacion = resultSet.getString("FECHAREALIZACION");
                String dniTrabajador = resultSet.getString("DNITRABAJADOR");
                String codigoTarea = resultSet.getString("CODIGOTAREA");

                trabajoTarea = new TrabajoTarea(Integer.parseInt(duracion), Integer.parseInt(fechaRealizacion), dniTrabajador, codigoTarea);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajoTarea;
    }

    public void EliminarTrabajoTarea(String codigoPK){
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query =  "DELETE FROM TRABAJOTAREAS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }

    }




    //TRABAJOMANTENIMIENTO
    public void InsertarTrabajoMantenimiento(TrabajoMantenimiento nuevoTrabajoMantenimiento) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO TRABAJOMANTENIMIENTO (DURACION, FECHAREALIZACION, DNITRABAJADOR, CODIGOTAREA)"
                    + " VALUES (?, ?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, nuevoTrabajoMantenimiento.getDuracion());
            preparedStatement.setInt(2, nuevoTrabajoMantenimiento.getFechaRealizacion());
            preparedStatement.setString(3, nuevoTrabajoMantenimiento.getDniTrabajador());
            preparedStatement.setString(4, nuevoTrabajoMantenimiento.getCodigoMantenimiento());
            preparedStatement.execute();

            System.out.println("Operación existosa");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<TrabajoMantenimiento> LeerTrabajoMantenimientos() {

        ArrayList<TrabajoMantenimiento> trabajoMantenimientos = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TRABAJOMANTENIMIENTOS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String duracion = resultSet.getString("DURACION");
                String fechaRealizacion = resultSet.getString("FECHAREALIZACION");
                String dniTrabajador = resultSet.getString("DNITRABAJADOR");
                String codigomantenimiento = resultSet.getString("CODIGOMANTENIMIENTO");

                TrabajoMantenimiento trabajoMantenimiento = new TrabajoMantenimiento(Integer.parseInt(duracion), Integer.parseInt(fechaRealizacion), dniTrabajador, codigomantenimiento);
                trabajoMantenimientos.add(trabajoMantenimiento);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajoMantenimientos;
    }

    public TrabajoMantenimiento LeerTrabajoMantenimientoConcreto(String codigoPK) {

        TrabajoMantenimiento trabajoMantenimiento = new TrabajoMantenimiento();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TRABAJOMANTENIMIENTOS WHERE CODIGO = '" + codigoPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String duracion = resultSet.getString("DURACION");
                String fechaRealizacion = resultSet.getString("FECHAREALIZACION");
                String dniTrabajador = resultSet.getString("DNITRABAJADOR");
                String codigoMantenimiento = resultSet.getString("CODIGOMANTENIMIENTO");

                trabajoMantenimiento = new TrabajoMantenimiento(Integer.parseInt(duracion), Integer.parseInt(fechaRealizacion), dniTrabajador, codigoMantenimiento);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajoMantenimiento;
    }

    public void EliminarTrabajoMantenimiento(String codigoPK){
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query =  "DELETE FROM TRABAJOMANTENIMIENTOS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            System.out.println(e);
        }

    }





}
