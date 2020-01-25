package com.company;

import com.company.Clases.*;

import javax.swing.*;
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
        String borrarTRABAJOMANTENIMIENTO = "DROP TABLE IF EXISTS TRABAJOMANTENIMIENTOS";
        String borrarREGISTROS = "DROP TABLE IF EXISTS REGISTROS";
        String borrarUSUARIOS = "DROP TABLE IF EXISTS USUARIOS";

        try {
            preparedStatement = con.prepareStatement(borrarTRABAJOMANTENIMIENTO);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOMANTENIMIENTO eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJOMANTENIMIENTO.");
            System.out.println(e);
            e.getMessage();
        }

        try {
            preparedStatement = con.prepareStatement(borrarTRABAJOTAREAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJOTAREAS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJOTAREAS.");
            e.getMessage();
            System.out.println(e);
        }

        try {
            preparedStatement = con.prepareStatement(borrarTRABAJADORES);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJADORES eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TRABAJADORES.");
            System.out.println(e);
        }

        try {
            preparedStatement = con.prepareStatement(borrarMANTENIMIENTOS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MANTENIMIENTOS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla MANTENIMIENTOS.");
            e.getMessage();
            System.out.println(e);
        }

        try {
            preparedStatement = con.prepareStatement(borrarTAREAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TAREAS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla TAREAS.");
            e.getMessage();
            System.out.println(e);
        }


        try {
            preparedStatement = con.prepareStatement(borrarMAQUINAS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla MAQUINAS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla MAQUINAS.");
            e.getMessage();
            System.out.println(e);
        }

        try {
            preparedStatement = con.prepareStatement(borrarREGISTROS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla REGISTROS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla REGISTROS.");
            e.getMessage();
            System.out.println(e);
        }


        try {
            preparedStatement = con.prepareStatement(borrarUSUARIOS);
            preparedStatement.executeUpdate();

            System.out.println("Tabla USUARIOS eliminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar la tabla USUARIOS.");
            e.getMessage();
            System.out.println(e);
        }

        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
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
                "MAQUINA BIT NOT NULL);";

        String createTrabajoTareas = "CREATE TABLE TRABAJOTAREAS(" +
                "CODIGO INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "DURACION INTEGER NOT NULL," +
                "FECHAREALIZACION INTEGER NOT NULL," +
                "DNITRABAJADOR VARCHAR(9) NOT NULL," +
                "CODIGOTAREA VARCHAR(255) NOT NULL," +
                "CODIGOMAQUINA VARCHAR(255)," +
                "FOREIGN KEY (CODIGOMAQUINA) REFERENCES MAQUINAS(CODIGO) ON DELETE CASCADE," +
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

        String createUsuarios = "CREATE TABLE USUARIOS(" +
                "USERNAME VARCHAR(255) PRIMARY KEY NOT NULL," +
                "PASSWORD VARCHAR(255) NOT NULL," +
                "TIPOCUENTA INTEGER NOT NULL);";

        String createRegistros = "CREATE TABLE REGISTROS(" +
                "CODIGO INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "HORA VARCHAR(255) NOT NULL," +
                "ACCION VARCHAR(255) NOT NULL," +
                "USUARIO VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (USUARIO) REFERENCES USUARIOS(USERNAME) ON DELETE NO ACTION);";

        //TABLA TRABAJADORES
        try {
            preparedStatement = con.prepareStatement(createTrabajadores);
            preparedStatement.executeUpdate();

            System.out.println("Tabla TRABAJADORES creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla TRABAJADORES.\n" + e.getMessage());
            System.out.println(e);
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

        //TABLA USUARIOS
        try {
            preparedStatement = con.prepareStatement(createUsuarios);
            preparedStatement.executeUpdate();

            System.out.println("Tabla USUARIOS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla USUARIOS.\n" + e.getMessage());
        }

        //TABLA REGISTROS
        try {
            preparedStatement = con.prepareStatement(createRegistros);
            preparedStatement.executeUpdate();

            System.out.println("Tabla REGISTROS creada correctamente.");

        } catch (Exception e) {
            System.err.println("No se han podido crear la tabla REGISTROS.\n" + e.getMessage());
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
            JOptionPane.showMessageDialog(null, "Trabajador creado correctamente.");


        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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

    public ArrayList<Trabajador> LeerTrabajadorFiltradoDNI(String dniPK) {

        ArrayList<Trabajador> trabajadores = new ArrayList<>();

        try {
            Connection con = Conexion();

            String query = "SELECT * FROM TRABAJADORES WHERE DNI LIKE '" + dniPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String foto = resultSet.getString("FOTO");


                trabajadores.add(new Trabajador(dni, nombre, apellido1, apellido2, foto));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajadores;
    }

    public ArrayList<Trabajador> LeerTrabajadorFiltradoNombre(String nombrePK) {

        ArrayList<Trabajador> trabajadores = new ArrayList<>();

        try {
            Connection con = Conexion();

            String query = "SELECT * FROM TRABAJADORES WHERE NOMBRE LIKE '" + nombrePK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String foto = resultSet.getString("FOTO");


                trabajadores.add(new Trabajador(dni, nombre, apellido1, apellido2, foto));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajadores;
    }

    public ArrayList<Trabajador> LeerTrabajadorFiltradoApellido1(String apellido1PK) {

        ArrayList<Trabajador> trabajadores = new ArrayList<>();

        try {
            Connection con = Conexion();

            String query = "SELECT * FROM TRABAJADORES WHERE APELLIDO1 LIKE '" + apellido1PK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String foto = resultSet.getString("FOTO");


                trabajadores.add(new Trabajador(dni, nombre, apellido1, apellido2, foto));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajadores;
    }

    public ArrayList<Trabajador> LeerTrabajadorFiltradoApellido2(String apellido2PK) {

        ArrayList<Trabajador> trabajadores = new ArrayList<>();

        try {
            Connection con = Conexion();

            String query = "SELECT * FROM TRABAJADORES WHERE APELLIDO2 LIKE '" + apellido2PK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String foto = resultSet.getString("FOTO");


                trabajadores.add(new Trabajador(dni, nombre, apellido1, apellido2, foto));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return trabajadores;
    }

    public void EliminarTrabajador(String dniPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM TRABAJADORES WHERE DNI = '" + dniPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Trabajador eliminado correctamente.");


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarTrabajador(Trabajador trabajador) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;


        try {
            String query = "UPDATE TRABAJADORES" +
                    " set DNI = '" + trabajador.getDni() + "'" +
                    ",NOMBRE = '" + trabajador.getNombre() + "'" +
                    ",APELLIDO1 = '" + trabajador.getApellido1() + "'" +
                    ",APELLIDO2 = '" + trabajador.getApellido2() + "'" +
                    ",FOTO = '" + trabajador.getRutaFoto() + "'" +
                    " where DNI = '" + trabajador.getDni() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Trabajador modificado correctamente.");


        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            e.getMessage();
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
            JOptionPane.showMessageDialog(null, "Máquina creada correctamente.");


        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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

    public ArrayList<Maquina> LeerMaquinaFiltradaCodigo(String codigoPK) {

        ArrayList<Maquina> maquinas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MAQUINAS WHERE CODIGO LIKE '" + codigoPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");


                maquinas.add(new Maquina(codigo, descripcion));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return maquinas;
    }

    public ArrayList<Maquina> LeerMaquinaFiltradaDescripcion(String descripcionPK) {

        ArrayList<Maquina> maquinas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MAQUINAS WHERE DESCRIPCION LIKE '%" + descripcionPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");


                maquinas.add(new Maquina(codigo, descripcion));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return maquinas;
    }

    public void EliminarMaquina(String codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM MAQUINAS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Máquina eliminada correctamente.");


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarMaquina(Maquina maquina) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {
            String query = "UPDATE MAQUINAS" +
                    " set CODIGO = '" + maquina.getCodigo() + "'" +
                    ",DESCRIPCION = '" + maquina.getDescripcion() + "'" +
                    " where CODIGO = '" + maquina.getCodigo() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Máquina modificada correctamente.");

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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
            JOptionPane.showMessageDialog(null, "Mantenimiento creado correctamente.");


        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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

    public ArrayList<Mantenimiento> LeerMantenimientoFiltradoCodigo(String codigoPK) {

        ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MANTENIMIENTOS WHERE CODIGO LIKE '" + codigoPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                mantenimientos.add(new Mantenimiento(codigo, descripcion, codigoMaquina));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return mantenimientos;
    }

    public ArrayList<Mantenimiento> LeerMantenimientoFiltradoDescripcion(String descripcionPK) {

        ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MANTENIMIENTOS WHERE DESCRIPCION LIKE '%" + descripcionPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                mantenimientos.add(new Mantenimiento(codigo, descripcion, codigoMaquina));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return mantenimientos;
    }

    public ArrayList<Mantenimiento> LeerMantenimientoFiltradoCodMaquina(String codMaquina) {

        ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM MANTENIMIENTOS WHERE CODIGOMAQUINA LIKE '" + codMaquina + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String codigoMaquina = resultSet.getString("CODIGOMAQUINA");

                mantenimientos.add(new Mantenimiento(codigo, descripcion, codigoMaquina));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return mantenimientos;
    }

    public void EliminarMantenimiento(String codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM MANTENIMIENTOS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Mantenimiento eliminado correctamente.");

        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarMantenimiento(Mantenimiento mantenimiento) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {
            String query = "UPDATE MANTENIMIENTOS" +
                    " set CODIGO = '" + mantenimiento.getCodigo() + "'" +
                    ",DESCRIPCION = '" + mantenimiento.getDescripcion() + "'" +
                    ",CODIGOMAQUINA = '" + mantenimiento.getCodigoMaquina() + "'" +
                    " where CODIGO = '" + mantenimiento.getCodigo() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Mantenimiento modificado correctamente.");

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            e.getMessage();
        }
    }


    //TAREAS
    public void InsertarTarea(Tarea nuevaTarea) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO TAREAS (CODIGO, DESCRIPCION, MAQUINA)"
                    + " VALUES (?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, nuevaTarea.getCodigo());
            preparedStatement.setString(2, nuevaTarea.getDescripcion());

            if (nuevaTarea.isMaquina() == true) {
                preparedStatement.setInt(3, 1);
            } else {
                preparedStatement.setInt(3, 0);
            }

            preparedStatement.execute();

            System.out.println("Operación existosa");


            JOptionPane.showMessageDialog(null, "Tarea creada correctamente.");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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
                String maquina = resultSet.getString("MAQUINA");

                Tarea tarea;

                if (Integer.parseInt(maquina) == 1) {
                    tarea = new Tarea(codigo, descripcion, true);
                } else {
                    tarea = new Tarea(codigo, descripcion, false);
                }

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
                String maquina = resultSet.getString("MAQUINA");

                Boolean maquinaBool = false;

                if (Integer.parseInt(maquina) == 1) {
                    maquinaBool = true;
                } else {
                    maquinaBool = false;
                }

                tarea = new Tarea(codigo, descripcion, maquinaBool);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return tarea;
    }

    public ArrayList<Tarea> LeerTareaFiltradaCodigo(String codigoPK) {

        ArrayList<Tarea> tareas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TAREAS WHERE CODIGO LIKE '" + codigoPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String maquina = resultSet.getString("MAQUINA");

                Tarea tarea;

                if (Integer.parseInt(maquina) == 1) {
                    tarea = new Tarea(codigo, descripcion, true);
                } else {
                    tarea = new Tarea(codigo, descripcion, false);
                }

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

    public ArrayList<Tarea> LeerTareaFiltradaDescripcion(String descripcionPK) {

        ArrayList<Tarea> tareas = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM TAREAS WHERE DESCRIPCION LIKE '%" + descripcionPK + "%'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String codigo = resultSet.getString("CODIGO");
                String descripcion = resultSet.getString("DESCRIPCION");
                String maquina = resultSet.getString("MAQUINA");

                Tarea tarea;

                if (Integer.parseInt(maquina) == 1) {
                    tarea = new Tarea(codigo, descripcion, true);
                } else {
                    tarea = new Tarea(codigo, descripcion, false);
                }

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

    public void EliminarTarea(String codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM TAREAS WHERE CODIGO LIKE '" + codigoPK + "%'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Tarea eliminada correctamente.");


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarTarea(Tarea tarea) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        int maquinaInt = 0;

        if(tarea.isMaquina() == true){
            maquinaInt = 1;
        }else{
            maquinaInt = 0;
        }

        try {
            String query = "UPDATE TAREAS" +
                    " set CODIGO = '" + tarea.getCodigo() + "'" +
                    ",DESCRIPCION = '" + tarea.getDescripcion() + "'" +
                    ",MAQUINA = " + maquinaInt +
                    " where CODIGO = '" + tarea.getCodigo() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Tarea modificada correctamente.");

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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

    public void EliminarTrabajoTarea(String codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM TRABAJOTAREAS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarTrabajoTarea(TrabajoTarea trabajoTarea) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {
            String query = "UPDATE TRABAJOTAREAS" +
                    " set DURACION = '" + trabajoTarea.getDuracion() + "'" +
                    ",FECHAREALIZACION = '" + trabajoTarea.getFechaRealizacion() + "'" +
                    ",DNITRABAJADOR = '" + trabajoTarea.getDniTrabajador() + "'" +
                    ",CODIGOTAREA = '" + trabajoTarea.getCodigoTarea() + "'" +
                    " where CODIGO = '" + trabajoTarea.getCodigo() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();


        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            e.getMessage();
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
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
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

    public void EliminarTrabajoMantenimiento(String codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM TRABAJOMANTENIMIENTOS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarTrabajoMantenimiento(TrabajoMantenimiento trabajoMantenimiento) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {
            String query = "UPDATE TRABAJOMANTENIMIENTOS" +
                    " set DURACION = '" + trabajoMantenimiento.getDuracion() + "'" +
                    ",FECHAREALIZACION = '" + trabajoMantenimiento.getFechaRealizacion() + "'" +
                    ",DNITRABAJADOR = '" + trabajoMantenimiento.getDniTrabajador() + "'" +
                    ",CODIGOTAREA = '" + trabajoMantenimiento.getCodigoMantenimiento() + "'" +
                    " where CODIGO = '" + trabajoMantenimiento.getCodigo() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();


        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            e.getMessage();
        }
    }


    //USUARIOS
    public void InsertarUsuario(Usuario usuario) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO USUARIOS (USERNAME, PASSWORD, TIPOCUENTA)"
                    + " VALUES (?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, usuario.getUsername());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.setInt(3, usuario.getPermisos());

            preparedStatement.execute();

            System.out.println("Operación existosa");


            JOptionPane.showMessageDialog(null, "Usuario creado correctamente.");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<Usuario> LeerUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM USUARIOS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                int tipocuenta = resultSet.getInt("TIPOCUENTA");

                Usuario usuario = new Usuario();
                usuario.setUsername(username);
                usuario.setPassword(password);
                usuario.setPermisos(tipocuenta);

                usuarios.add(usuario);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return usuarios;
    }

    public Usuario LeerUsuarioConcreto(String usernamePK) {
        Usuario usuario = new Usuario();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM USUARIOS WHERE USERNAME = '" + usernamePK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                int tipocuenta = resultSet.getInt("TIPOCUENTA");


                usuario.setUsername(username);
                usuario.setPassword(password);
                usuario.setPermisos(tipocuenta);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return usuario;
    }

    public void EliminarUsuario(String usernamePK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM USUARIOS WHERE USERNAME = '" + usernamePK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");

        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarUsuario(Usuario usuario) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {
            String query = "UPDATE USUARIOS" +
                    " set USERNAME = '" + usuario.getUsername() + "'" +
                    ",PASSWORD = '" + usuario.getPassword() + "'" +
                    ",TIPOCUENTA = '" + usuario.getPermisos() + "'" +
                    " where USERNAME = '" + usuario.getUsername() + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Usuario modificado correctamente.");

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            e.getMessage();
        }
    }


    //REGISTROS
    public void InsertarRegistro(Registro registro) {
        PreparedStatement preparedStatement;
        Connection con = Conexion();

        try {

            String query = " INSERT INTO REGISTROS (HORA, ACCION, USUARIO)"
                    + " VALUES (?, ?, ?)";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, registro.getHora());
            preparedStatement.setString(2, registro.getAccion());
            preparedStatement.setString(3, registro.getUsuario());

            preparedStatement.execute();

            System.out.println("Operación existosa");


            JOptionPane.showMessageDialog(null, "Registro creado correctamente.");

        } catch (Exception e) {
            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }
    }

    public ArrayList<Registro> LeerRegistros() {

        ArrayList<Registro> registros = new ArrayList<>();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM REGISTROS";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                String hora = resultSet.getString("HORA");
                String accion = resultSet.getString("ACCION");
                String usuario = resultSet.getString("USUARIO");

                Registro registro = new Registro();
                registro.setHora(hora);
                registro.setAccion(accion);
                registro.setUsuario(usuario);

                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return registros;
    }

    public Registro LeerRegistroConcreto(String codigoPK) {
        Registro registro = new Registro();

        try {
            Connection con = Conexion();


            String query = "SELECT * FROM REGISTROS WHERE CODIGO = '" + codigoPK + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {

                String hora = resultSet.getString("HORA");
                String accion = resultSet.getString("ACCION");
                String usuario = resultSet.getString("USUARIO");

                registro.setHora(hora);
                registro.setAccion(accion);
                registro.setUsuario(usuario);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            System.out.println(e);
        }

        return registro;
    }

    public void EliminarRegistro(String codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {

            String query = "DELETE FROM REGISTROS WHERE CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

        } catch (SQLException e) {

            System.out.println("A ocurrido un ERROR");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            System.out.println(e);
        }

    }

    public void ModificarRegistro(Registro registro, int codigoPK) {
        Connection con = Conexion();
        PreparedStatement preparedStatement;

        try {
            String query = "UPDATE REGISTROS" +
                    " set HORA = '" + registro.getHora() + "'" +
                    ",ACCION = '" + registro.getAccion() + "'" +
                    ",USUARIO = '" + registro.getUsuario() + "'" +
                    " where CODIGO = '" + codigoPK + "'";

            preparedStatement = con.prepareStatement(query);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");

        } catch (Exception e) {
            System.err.println("A ocurrido un ERROR.");
            JOptionPane.showMessageDialog(null, "A ocurrido un ERROR");
            e.getMessage();
        }
    }




}
