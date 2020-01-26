package com.company.UI;

import com.company.Clases.Mantenimiento;
import com.company.Clases.Trabajador;
import com.company.Clases.TrabajoMantenimiento;
import com.company.Clases.TrabajoTarea;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Paso4Confirmar {
    private JPanel PanelConfirmar;
    private JButton CancelarButton;
    private JTable TableTrabajador;
    private JTable TableTareas;
    private JTable TableMantenimientos;
    private JButton FinalizarButton;
    private JLabel TextTiempoTotal;
    JFrame frame;


    public Paso4Confirmar(Trabajador trabajador, ArrayList<TrabajoMantenimiento> trabajoMantenimientos, ArrayList<TrabajoTarea> trabajoTareas) {
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Paso 4: Confirmar datos");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelConfirmar);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //Calcular el tiempo total
        int minutosTotales = 0;

        for (int i = 0; i < trabajoMantenimientos.size(); i++) {
            minutosTotales = minutosTotales + trabajoMantenimientos.get(i).getDuracion();
        }

        for (int i = 0; i < trabajoTareas.size(); i++) {
            minutosTotales = minutosTotales + trabajoTareas.get(i).getDuracion();
        }

        int hours = minutosTotales / 60; //since both are ints, you get an int
        int minutes = minutosTotales % 60;

        TextTiempoTotal.setText("TIEMPO TOTAL: " + hours + " horas y " + minutes + " minutos");


        DefaultTableModel model1 = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        DefaultTableModel model3 = new DefaultTableModel();

        //Creo el modelo de las tablas.
        String[] columnas = {"Nombre", "Apellido 1", "Apellido 2", "Foto"};

        model1 = new DefaultTableModel(columnas, 0) {
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

        model2 = new DefaultTableModel() {
            String[] columnas = {"Tarea", "Tiene máquina", "Maquina", "Duracion (Minutos)"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        model3 = new DefaultTableModel() {
            String[] columnas = {"Mantenimiento", "Maquina", "Duracion (Minutos)"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };


        //Asigno el modelo a la tabla y establezco que solo se pueda seleccionar una línea a la vez.
        TableTrabajador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableTrabajador.setModel(model1);

        TableTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableTareas.setModel(model2);

        TableMantenimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableMantenimientos.setModel(model3);

        //Desactivo al modificación de tablas
        TableTrabajador.setDefaultEditor(Object.class, null);
        TableTareas.setDefaultEditor(Object.class, null);
        TableMantenimientos.setDefaultEditor(Object.class, null);

        //Establezco la altura para que la imagen entre.
        TableTrabajador.setRowHeight(150);

        //RELLENAR DATOS

        ImageIcon icon = new ImageIcon();
        try {
            //Selecciono la imagen del empleado y convierto su ruta en URL para poderla usar en el tipo Icon.
            URL url = new URL(new File(trabajador.getRutaFoto()).toURI().toURL().toString());

            //Aquí cambio el tamaño de la imágen para que se vea correctamente.
            icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(110, 140, Image.SCALE_DEFAULT));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < trabajoTareas.size(); i++) {
            String maquina;

            if (trabajoTareas.get(i).getCodigoMaquina().equals("")) {
                maquina = "NO";
            } else {
                maquina = "SI";
            }

            model2.addRow(new Object[]{trabajoTareas.get(i).getCodigoTarea(), maquina, trabajoTareas.get(i).getCodigoMaquina(), trabajoTareas.get(i).getDuracion()});
        }


        for (int i = 0; i < trabajoMantenimientos.size(); i++) {

            Mantenimiento mantenimiento;
            String maquina;

            mantenimiento = llamadasBD.LeerMantenimientoConcreto(trabajoMantenimientos.get(i).getCodigoMantenimiento());
            maquina = mantenimiento.getCodigoMaquina();

            model3.addRow(new Object[]{trabajoMantenimientos.get(i).getCodigoMantenimiento(), maquina, trabajoMantenimientos.get(i).getDuracion()});
        }

        model1.addRow(new Object[]{trabajador.getNombre(), trabajador.getApellido1(), trabajador.getApellido2(), icon});


        CancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicial menuInicial = new MenuInicial();
                frame.dispose();
            }
        });


        FinalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Si el usuario pulsa finalizar inserto todos los datos en la BD.

                for (int i = 0; i < trabajoTareas.size(); i++) {
                    llamadasBD.InsertarTrabajoTarea(trabajoTareas.get(i));
                }

                for (int i = 0; i < trabajoMantenimientos.size(); i++) {
                    llamadasBD.InsertarTrabajoMantenimiento(trabajoMantenimientos.get(i));
                }

                MenuInicial menuInicial = new MenuInicial();
                frame.dispose();
            }
        });
    }
}
