package com.company.UI;

import com.company.Clases.Maquina;
import com.company.Clases.Tarea;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Paso2Tareas {
    private JPanel PanelTareas;
    private JTable TablaTareas;
    private JTable TablaMaquinasDisponibles;
    private JTable TablaMaquinasSeleccionadas;
    private JButton CancelarButton;
    private JButton SiguienteButton;
    JFrame frame;

    public Paso2Tareas() {

        ArrayList<Tarea> tareas = new ArrayList<>();
        ArrayList<Maquina> maquinas = new ArrayList<>();
        ArrayList<Maquina> maquinasSeleccionadas = new ArrayList<>();

        DefaultTableModel model1 = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        DefaultTableModel model3 = new DefaultTableModel();


        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Paso 2: TAREAS");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTareas);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        //Defino los modelos de las tres tablas.

        //TABLA TAREAS
        model1 = new DefaultTableModel() {
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

        //TABLA MAQUINAS DISPONIBLES


    }
}
