package com.company.UI;

import com.company.Clases.Mantenimiento;
import com.company.Clases.Maquina;
import com.company.Clases.Tarea;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TablaDeSeleccion {
    private JButton VolverButton;
    private JPanel PanelTablaDeSeleccion;
    private JLabel TituloText;
    private JTable table1;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton EditarButton;
    private JButton CrearButton;
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

        //Éste switch define el modelo que utilizará la tabla.
        switch (IDTabla) {

            case 0:
                //Trabajadores (Requiere de un modelo personalizado para poder visualizar las imágenes de los empleados.)

                break;

            case 1:
                //Tareas
                    model  = new DefaultTableModel(){
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
                model  = new DefaultTableModel(){
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
                model  = new DefaultTableModel(){
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

                switch (IDTabla){

                    case 0:
                        break;

                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        break;


                }

            }
        });
    }
}
