package com.company.UI;

import com.company.Clases.*;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Paso3Mantenimienos {
    private JButton CancelarButton;
    private JButton SiguienteButton;
    private JPanel PanelMantenimientos;
    private JTable TablaMantenimientosDisponibles;
    private JTable TablaMantenimientosSeleccionados;
    private JButton EliminarButton;
    private JSpinner SpinnerHoras;
    private JButton AnadirButton;
    private JSpinner SpinnerMinutos;
    JFrame frame;

    public Paso3Mantenimienos(ArrayList<TrabajoTarea> trabajoTareas, Trabajador trabajador) {

        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Paso 3: MANTENIMIENTOS");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelMantenimientos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        ArrayList<Mantenimiento> mantenimientosDisponibles = new ArrayList<>();
        ArrayList<Mantenimiento> mantenimientosSeleccionados = new ArrayList<>();

        mantenimientosDisponibles = llamadasBD.LeerMantenimientos();

        DefaultTableModel model1 = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();

        //Creo el modelo de la tabla.
        model1 = new DefaultTableModel() {
            String[] columnas = {"Codigo", "Descripción", "Codigo maquina"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        //Creo el modelo de la tabla.
        model2 = new DefaultTableModel() {
            String[] columnas = {"Codigo", "Descripción", "Codigo maquina", "Duración"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        //Hago que solo se pueda seleccionar una fila a la vez.
        TablaMantenimientosDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaMantenimientosSeleccionados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Desactivo al modificación de tablas .setDefaultEditor(Object.class, null);
        TablaMantenimientosDisponibles.setDefaultEditor(Object.class, null);
        TablaMantenimientosSeleccionados.setDefaultEditor(Object.class, null);

        //Asigno los modelos.
        TablaMantenimientosDisponibles.setModel(model1);
        TablaMantenimientosSeleccionados.setModel(model2);


        //Relleno las tablas con datos.
        for (int i = 0; i < mantenimientosDisponibles.size(); i++) {
            model1.addRow(new Object[]{mantenimientosDisponibles.get(i).getCodigo(), mantenimientosDisponibles.get(i).getDescripcion(), mantenimientosDisponibles.get(i).getCodigoMaquina()});
        }

        for (int i = 0; i < mantenimientosSeleccionados.size(); i++) {
            model2.addRow(new Object[]{mantenimientosSeleccionados.get(i).getCodigo(), mantenimientosSeleccionados.get(i).getDescripcion(), mantenimientosSeleccionados.get(i).getCodigoMaquina(), mantenimientosSeleccionados.get(i).getDuracion()});
        }


        CancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicial menuInicial = new MenuInicial();
                frame.dispose();
            }
        });


        DefaultTableModel finalModel = model2;
        AnadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (TablaMantenimientosDisponibles.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna mantenimiento.");
                } else {
                    String PK = TablaMantenimientosDisponibles.getValueAt(TablaMantenimientosDisponibles.getSelectedRow(), 0).toString();

                    //Si está duplicado no lo añado a la otra tabla.
                    boolean duplicado = false;
                    for (int i = 0; i < mantenimientosSeleccionados.size(); i++) {
                        if (mantenimientosSeleccionados.get(i).getCodigo().equals(PK)) {
                            duplicado = true;
                        }
                    }

                    if (duplicado == false) {
                        Mantenimiento nuevoMantenimiento = new Mantenimiento();
                        nuevoMantenimiento = llamadasBD.LeerMantenimientoConcreto(PK);


                        int horas = (Integer) SpinnerHoras.getValue();
                        int minutos = (Integer) SpinnerMinutos.getValue();

                        minutos = (horas * 60) + minutos;

                        nuevoMantenimiento.setDuracion(minutos);
                        mantenimientosSeleccionados.add(nuevoMantenimiento);

                        ActualzarTablaMaquinasSeleccionadas(mantenimientosSeleccionados, finalModel);

                        SpinnerHoras.setValue(0);
                        SpinnerMinutos.setValue(0);
                    }
                }
            }
        });



        EliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TablaMantenimientosSeleccionados.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ningun mantenimiento.");
                } else {

                    String PK = TablaMantenimientosSeleccionados.getValueAt(TablaMantenimientosSeleccionados.getSelectedRow(), 0).toString();


                    for (int i = 0; i < mantenimientosSeleccionados.size(); i++) {
                        if (mantenimientosSeleccionados.get(i).getCodigo().equals(PK)) {
                            mantenimientosSeleccionados.remove(i);
                        }
                    }

                    ActualzarTablaMaquinasSeleccionadas(mantenimientosSeleccionados, finalModel);

                }
            }
        });


        SiguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<TrabajoMantenimiento> trabajoMantenimientos = new ArrayList<>();

                for (int i = 0; i < mantenimientosSeleccionados.size(); i++) {
                    TrabajoMantenimiento trabajoMantenimiento = new TrabajoMantenimiento();


                    trabajoMantenimiento.setCodigoMantenimiento(mantenimientosSeleccionados.get(i).getCodigo());
                    trabajoMantenimiento.setDuracion(mantenimientosSeleccionados.get(i).getDuracion());
                    trabajoMantenimiento.setDniTrabajador(trabajador.getDni());
                    String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
                    int fecha = Integer.parseInt(date);
                    trabajoMantenimiento.setFechaRealizacion(fecha);

                }



            }
        });
    }


    private void ActualzarTablaMaquinasSeleccionadas(ArrayList<Mantenimiento> mantenimientosSeleccionados, DefaultTableModel model) {

        //Éste pequeño código sirve para limiar los datos de la tabla.
        int filas = model.getRowCount();
        //Elimino una a una las filas de la tabla.
        for (int i = filas - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        //Añado las nuevas líneas, ya filtradas.
        for (int i = 0; i < mantenimientosSeleccionados.size(); i++) {
            model.addRow(new Object[]{mantenimientosSeleccionados.get(i).getCodigo(), mantenimientosSeleccionados.get(i).getDescripcion(), mantenimientosSeleccionados.get(i).getCodigoMaquina(), mantenimientosSeleccionados.get(i).getDuracion()});
        }
    }
}
