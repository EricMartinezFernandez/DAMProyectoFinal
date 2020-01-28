package com.company.UI;

import com.company.Clases.*;
import com.company.LlamadasBD;
import com.company.UI.MenusEdicion.MantenimientoEdicion;
import com.company.UI.MenusEdicion.MaquinaEdicion;
import com.company.UI.MenusEdicion.TareaEdicion;
import com.company.UI.MenusEdicion.TrabajadorEdicion;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TablaDeSeleccion {
    private JButton VolverButton;
    private JPanel PanelTablaDeSeleccion;
    private JLabel TituloText;
    private JTable table1;
    private JTextField TextUserPK;
    private JButton EditarButton;
    private JButton CrearButton;
    private JButton BuscarButton;
    private JLabel TextPK;
    private JComboBox ComboColumnas;
    JFrame frame;


    public TablaDeSeleccion(int IDTabla, Usuario usuarioActivo) {

        //Instancio la clase de las llamadas a BD.
        LlamadasBD llamadasBD = new LlamadasBD();

        DefaultTableModel model = new DefaultTableModel();

        frame = new JFrame("Tabla de selección");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTablaDeSeleccion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();

        //Éste switch define el modelo que utilizará cada tabla en base a los objetos tenga que mostrar.
        switch (IDTabla) {

            case 0:
                //Trabajadores

                ComboColumnas.addItem("DNI");
                ComboColumnas.addItem("Nombre");
                ComboColumnas.addItem("Apellido 1");
                ComboColumnas.addItem("Apellido 2");

                String[] columnas = {"DNI", "Nombre", "Apellido 1", "Apellido 2", "Foto"};

                model = new DefaultTableModel(columnas, 0) {
                    @Override
                    public Class<?> getColumnClass(int column) {
                        if (getRowCount() > 0) {
                            Object value = getValueAt(0, column);
                            if (value != null) {
                                return getValueAt(0, column).getClass();
                            }
                        }

                        return super.getColumnClass(column);
                    }
                };

                break;

            case 1:
                //Tareas

                ComboColumnas.addItem("Codigo");
                ComboColumnas.addItem("Descripción");

                model = new DefaultTableModel() {
                    String[] columnas = {"Codigo", "Descripción", "Tiene máquina"};

                    @Override
                    public int getColumnCount() {
                        return columnas.length;
                    }

                    @Override
                    public String getColumnName(int index) {
                        return columnas[index];
                    }

                };


                break;

            case 2:
                //Máquinas

                ComboColumnas.addItem("Codigo");
                ComboColumnas.addItem("Descripción");

                model = new DefaultTableModel() {
                    String[] columnas = {"Codigo", "Descripción"};

                    @Override
                    public int getColumnCount() {
                        return columnas.length;
                    }

                    @Override
                    public String getColumnName(int index) {
                        return columnas[index];
                    }

                };

                break;

            case 3:
                //Mantenimiento
                ComboColumnas.addItem("Codigo");
                ComboColumnas.addItem("Descripción");
                ComboColumnas.addItem("Código máquina");


                model = new DefaultTableModel() {
                    String[] columnas = {"Codigo", "Descripción", "Código máquina"};

                    @Override
                    public int getColumnCount() {
                        return columnas.length;
                    }

                    @Override
                    public String getColumnName(int index) {
                        return columnas[index];
                    }

                };

                break;
        }

        //Asigno el modelo a la tabla, establezco que solo se pueda seleccionar una línea a la vez y desactivo la modificación de celdas.
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setDefaultEditor(Object.class, null);
        table1.setModel(model);

        //Éste switch rellena la tabla con la información.
        switch (IDTabla) {

            case 0:
                //Trabajadores

                //Primero establezco la altura de las filas para poder insertar las imágenes.
                table1.setRowHeight(150);

                ArrayList<Trabajador> trabajadores = new ArrayList<>();
                trabajadores = llamadasBD.LeerTrabajadores();


                for (int i = 0; i < trabajadores.size(); i++) {

                    ImageIcon icon = new ImageIcon();
                    try {
                        //Selecciono la imagen del empleado y convierto su ruta en URL para poderla usar en el tipo Icon.
                        URL url = new URL(new File(trabajadores.get(i).getRutaFoto()).toURI().toURL().toString());

                        //Aquí cambio el tamaño de la imágen para que se vea correctamente.
                        icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(110, 140, Image.SCALE_DEFAULT));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }


                    model.addRow(new Object[]{trabajadores.get(i).getDni(), trabajadores.get(i).getNombre(), trabajadores.get(i).getApellido1(), trabajadores.get(i).getApellido2(), icon});
                }
                break;

            case 1:
                //Tareas
                ArrayList<Tarea> tareas = new ArrayList<>();
                tareas = llamadasBD.LeerTareas();
                String maquina = "";

                for (int i = 0; i < tareas.size(); i++) {

                    if (tareas.get(i).isMaquina() == true) {
                        maquina = "SI";
                    } else {
                        maquina = "NO";
                    }

                    model.addRow(new Object[]{tareas.get(i).getCodigo(), tareas.get(i).getDescripcion(), maquina});
                }
                break;

            case 2:
                //Máquinas
                ArrayList<Maquina> maquinas = new ArrayList<>();
                maquinas = llamadasBD.LeerMaquinas();

                for (int i = 0; i < maquinas.size(); i++) {
                    model.addRow(new Object[]{maquinas.get(i).getCodigo(), maquinas.get(i).getDescripcion()});
                }
                break;

            case 3:
                //Mantenimiento
                ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();
                mantenimientos = llamadasBD.LeerMantenimientos();

                for (int i = 0; i < mantenimientos.size(); i++) {
                    model.addRow(new Object[]{mantenimientos.get(i).getCodigo(), mantenimientos.get(i).getDescripcion(), mantenimientos.get(i).getCodigoMaquina()});
                }
                break;
        }


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuSeleccionDeTabla menuSeleccionDeTabla = new MenuSeleccionDeTabla(usuarioActivo);
                frame.dispose();
            }
        });


        //Éste botón localiza la posición seleccionada en la tabla y envía el objeto a la siguiente interfaz.
        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (IDTabla) {

                    case 0:
                        //Trabajadores
                        Trabajador trabajador;

                        //Recojo la PK del la fila que a seleccionado el usuario, por ello se bloquea el campo en 0, que es la ubicación común de PK en la tabla.
                        trabajador = llamadasBD.LeerTrabajadorConcreto(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));
                        TrabajadorEdicion trabajadorEdicion = new TrabajadorEdicion(true, trabajador, usuarioActivo);
                        frame.dispose();

                        break;

                    case 1:
                        //Tareas
                        Tarea tarea;

                        tarea = llamadasBD.LeerTareaConcreta(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));

                        TareaEdicion tareaEdicion = new TareaEdicion(true, tarea, usuarioActivo);
                        frame.dispose();

                        break;

                    case 2:
                        //Máquinas
                        Maquina maquina;

                        maquina = llamadasBD.LeerMaquinaConcreta(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));
                        MaquinaEdicion maquinaEdicion = new MaquinaEdicion(true, maquina, usuarioActivo);
                        frame.dispose();

                        break;

                    case 3:
                        //Mantenimientos
                        Mantenimiento mantenimiento;

                        mantenimiento = llamadasBD.LeerMantenimientoConcreto(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));
                        MantenimientoEdicion mantenimientoEdicion = new MantenimientoEdicion(true, mantenimiento, usuarioActivo);
                        frame.dispose();
                        break;


                }

            }
        });


        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (IDTabla) {
                    case 0:
                        //Trabajadores
                        Trabajador trabajador = new Trabajador();
                        TrabajadorEdicion trabajadorEdicion = new TrabajadorEdicion(false, trabajador, usuarioActivo);
                        frame.dispose();
                        break;

                    case 1:
                        //Tareas
                        Tarea tarea = new Tarea();
                        TareaEdicion tareaEdicion = new TareaEdicion(false, tarea, usuarioActivo);
                        frame.dispose();
                        break;

                    case 2:
                        //Máquinas
                        Maquina maquina = new Maquina();
                        MaquinaEdicion maquinaEdicion = new MaquinaEdicion(false, maquina, usuarioActivo);
                        frame.dispose();
                        break;

                    case 3:
                        //Mantenimientos
                        Mantenimiento mantenimiento = new Mantenimiento();
                        MantenimientoEdicion mantenimientoEdicion = new MantenimientoEdicion(false, mantenimiento, usuarioActivo);
                        frame.dispose();
                        break;
                }


            }
        });

        //Éste botón filtra las filas de la lista en base a lo introducido en la búsqueda.
        DefaultTableModel finalModel = model;
        BuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filas;

                switch (IDTabla) {
                    case 0:
                        //Trabajadores
                        ArrayList<Trabajador> trabajadores = new ArrayList<>();

                        switch (ComboColumnas.getSelectedIndex()) {
                            case 0:
                                trabajadores = llamadasBD.LeerTrabajadorFiltradoDNI(TextUserPK.getText());
                                break;

                            case 1:
                                trabajadores = llamadasBD.LeerTrabajadorFiltradoNombre(TextUserPK.getText());
                                break;

                            case 2:
                                trabajadores = llamadasBD.LeerTrabajadorFiltradoApellido1(TextUserPK.getText());
                                break;

                            case 3:
                                trabajadores = llamadasBD.LeerTrabajadorFiltradoApellido2(TextUserPK.getText());
                                break;
                        }

                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }

                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < trabajadores.size(); i++) {


                            ImageIcon icon = new ImageIcon();
                            try {

                                //Selecciono la imagen del empleado y convierto su ruta en URL para poderla usar en el tipo Icon.
                                URL url = new URL(new File(trabajadores.get(i).getRutaFoto()).toURI().toURL().toString());
                                //Aquí cambio el tamaño de la imágen para que se vea correctamente.
                                icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(110, 140, Image.SCALE_DEFAULT));

                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }


                            finalModel.addRow(new Object[]{trabajadores.get(i).getDni(), trabajadores.get(i).getNombre(), trabajadores.get(i).getApellido1(), trabajadores.get(i).getApellido2(), icon});
                        }
                        break;

                    case 1:
                        //Tareas
                        ArrayList<Tarea> tareas = new ArrayList<>();

                        switch (ComboColumnas.getSelectedIndex()) {
                            case 0:
                                tareas = llamadasBD.LeerTareaFiltradaCodigo(TextUserPK.getText());
                                break;

                            case 1:
                                tareas = llamadasBD.LeerTareaFiltradaDescripcion(TextUserPK.getText());
                                break;
                        }




                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }

                        String maquina;

                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < tareas.size(); i++) {


                            if (tareas.get(i).isMaquina() == true) {
                                maquina = "SI";
                            } else {
                                maquina = "NO";
                            }

                            finalModel.addRow(new Object[]{tareas.get(i).getCodigo(), tareas.get(i).getDescripcion(), maquina});
                        }
                        break;

                    case 2:
                        //Máquinas
                        ArrayList<Maquina> maquinas = new ArrayList<>();


                        switch (ComboColumnas.getSelectedIndex()) {
                            case 0:
                                maquinas = llamadasBD.LeerMaquinaFiltradaCodigo(TextUserPK.getText());
                                break;

                            case 1:
                                maquinas = llamadasBD.LeerMaquinaFiltradaDescripcion(TextUserPK.getText());
                                break;
                        }

                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }
                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < maquinas.size(); i++) {
                            finalModel.addRow(new Object[]{maquinas.get(i).getCodigo(), maquinas.get(i).getDescripcion()});
                        }
                        break;

                    case 3:
                        //Mantenimientos
                        ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();


                        switch (ComboColumnas.getSelectedIndex()) {
                            case 0:
                                mantenimientos = llamadasBD.LeerMantenimientoFiltradoCodigo(TextUserPK.getText());
                                break;

                            case 1:
                                mantenimientos = llamadasBD.LeerMantenimientoFiltradoDescripcion(TextUserPK.getText());
                                break;

                            case 2:
                                mantenimientos = llamadasBD.LeerMantenimientoFiltradoCodMaquina(TextUserPK.getText());
                                break;
                        }



                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }
                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < mantenimientos.size(); i++) {
                            finalModel.addRow(new Object[]{mantenimientos.get(i).getCodigo(), mantenimientos.get(i).getDescripcion(), mantenimientos.get(i).getCodigoMaquina()});
                        }


                }
            }
        });

    }
}
