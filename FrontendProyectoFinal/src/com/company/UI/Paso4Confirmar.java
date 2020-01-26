package com.company.UI;

import com.company.LlamadasBD;

import javax.swing.*;

public class Paso4Confirmar {
    private JPanel PanelConfirmar;
    private JButton CancelarButton;
    private JButton SiguienteButton;
    private JTable TablaMantenimientosDisponibles;
    private JTable TablaMantenimientosSeleccionados;
    private JTable TableTrabajador;
    private JScrollPane TableTareas;
    private JScrollPane TableMantenimientos;
    JFrame frame;


    public Paso4Confirmar() {
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Paso 4: Confirmar datos");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelConfirmar);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

    }
}
