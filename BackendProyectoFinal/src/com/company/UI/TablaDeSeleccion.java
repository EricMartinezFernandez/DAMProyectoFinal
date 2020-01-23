package com.company.UI;

import com.company.Clases.Mantenimiento;
import com.company.Clases.Maquina;
import com.company.Clases.Tarea;
import com.company.Clases.Trabajador;
import com.company.LlamadasBD;
import com.company.UI.MenusEdicion.MantenimientoEdicion;
import com.company.UI.MenusEdicion.MaquinaEdicion;
import com.company.UI.MenusEdicion.TareaEdicion;
import com.company.UI.MenusEdicion.TrabajadorEdicion;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
    JFrame frame;


    public TablaDeSeleccion(int IDTabla) {

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
                //Trabajadores (Requiere de un modelo personalizado para poder visualizar las imágenes de los empleados.)
                TextPK.setText("DNI: ");

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

                /*model = new DefaultTableModel() {
                    String[] columnas = {"DNI", "Nombre", "Apellido 1", "Apellido 2", "Foto"};

                    @Override
                    public int getColumnCount() {
                        return columnas.length;
                    }

                    @Override
                    public String getColumnName(int index) {
                        return columnas[index];
                    }

                };

                 */

                break;

            case 1:
                //Tareas
                TextPK.setText("Código: ");

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

            case 2:
                //Máquinas
                TextPK.setText("Código: ");

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
                TextPK.setText("Código: ");

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

        table1.setModel(model);

        //Éste switch rellena la tabla con la información.
        switch (IDTabla) {

            case 0:
                //Trabajadores (Requiere de un modelo personalizado para poder visualizar las imágenes de los empleados.)

                ArrayList<Trabajador> trabajadores = new ArrayList<>();
                trabajadores = llamadasBD.LeerTrabajadores();


                for (int i = 0; i < trabajadores.size(); i++) {

                    ImageIcon icon = new ImageIcon();
                    try {
                        URL url = new URL(trabajadores.get(i).getRutaFoto());
                        icon = new ImageIcon(url);
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

                for (int i = 0; i < tareas.size(); i++) {
                    model.addRow(new Object[]{tareas.get(i).getCodigo(), tareas.get(i).getDescripcion(), tareas.get(i).getCodigoMaquina()});
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
                MenuSeleccionDeTabla menuSeleccionDeTabla = new MenuSeleccionDeTabla();
                frame.dispose();
            }
        });


        //Éste botón localiza la posición seleccionada en la tabla y envía el objeto a la siguiente interfaz.
        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String columna = String.valueOf(table1.getValueAt(table1.getSelectedRow(), table1.getSelectedColumn()));

                switch (IDTabla) {

                    case 0:
                        //Trabajadores
                        Trabajador trabajador;

                        //Recojo la PK del la fila que a seleccionado el usuario, por ello se bloquea el campo en 0, que es la ubicación común de PK en la tabla.
                        trabajador = llamadasBD.LeerTrabajadorConcreto(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));

                        break;

                    case 1:
                        //Tareas
                        Tarea tarea;

                        tarea = llamadasBD.LeerTareaConcreta(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));

                        TareaEdicion tareaEdicion = new TareaEdicion(true, tarea);
                        frame.dispose();

                        break;

                    case 2:
                        //Máquinas
                        Maquina maquina;

                        maquina = llamadasBD.LeerMaquinaConcreta(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));
                        MaquinaEdicion maquinaEdicion = new MaquinaEdicion(true, maquina);
                        frame.dispose();

                        break;

                    case 3:
                        //Mantenimientos
                        Mantenimiento mantenimiento;

                        mantenimiento = llamadasBD.LeerMantenimientoConcreto(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));
                        MantenimientoEdicion mantenimientoEdicion = new MantenimientoEdicion(true, mantenimiento);
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
                        TrabajadorEdicion trabajadorEdicion = new TrabajadorEdicion(false, trabajador);
                        frame.dispose();
                        break;

                    case 1:
                        //Tareas
                        Tarea tarea = new Tarea();
                        TareaEdicion tareaEdicion = new TareaEdicion(false, tarea);
                        frame.dispose();
                        break;

                    case 2:
                        //Máquinas
                        Maquina maquina = new Maquina();
                        MaquinaEdicion maquinaEdicion = new MaquinaEdicion(false, maquina);
                        frame.dispose();
                        break;

                    case 3:
                        //Mantenimientos
                        Mantenimiento mantenimiento = new Mantenimiento();
                        MantenimientoEdicion mantenimientoEdicion = new MantenimientoEdicion(false, mantenimiento);
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
                        trabajadores = llamadasBD.LeerTrabajadorFiltrado(TextUserPK.getText());

                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }

                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < trabajadores.size(); i++) {
                            finalModel.addRow(new Object[]{trabajadores.get(i).getDni(), trabajadores.get(i).getNombre(), trabajadores.get(i).getApellido1(), trabajadores.get(i).getApellido2(), trabajadores.get(i).getRutaFoto()});
                        }
                        break;

                    case 1:
                        //Tareas
                        ArrayList<Tarea> tareas = new ArrayList<>();
                        tareas = llamadasBD.LeerTareaFiltrada(TextUserPK.getText());

                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }

                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < tareas.size(); i++) {
                            finalModel.addRow(new Object[]{tareas.get(i).getCodigo(), tareas.get(i).getDescripcion(), tareas.get(i).getCodigoMaquina()});
                        }
                        break;

                    case 2:
                        //Máquinas
                        ArrayList<Maquina> maquinas = new ArrayList<>();
                        maquinas = llamadasBD.LeerMaquinaFiltrada(TextUserPK.getText());

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
                        mantenimientos = llamadasBD.LeerMantenimientoFiltrado(TextUserPK.getText());

                        //Éste pequeño código sirve para limiar los datos de la tabla.
                        filas = finalModel.getRowCount();
                        //Elimino una a una las filas de la tabla.
                        for (int i = filas - 1; i >= 0; i--) {
                            finalModel.removeRow(i);
                        }
                        //Añado las nuevas líneas, ya filtradas.
                        for (int i = 0; i < mantenimientos.size(); i++) {
                            finalModel.addRow(new Object[]{mantenimientos.get(i).getCodigo(), mantenimientos.get(i).getDescripcion(), mantenimientos.get(i).getCodigoMaquina()});
                            break;
                        }


                }
            }
        });

    }
}
