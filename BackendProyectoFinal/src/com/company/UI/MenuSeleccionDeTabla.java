package com.company.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSeleccionDeTabla {
    private JButton TareasButton;
    private JButton TrabajadoresButton;
    private JButton MaquinasButton;
    private JButton MantenimientosButton;
    private JButton VolverButton;
    private JPanel PanelMenuSeleccionDeTabla;
    JFrame frame;

    public MenuSeleccionDeTabla() {
        frame = new JFrame("Seleccion de datos");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelMenuSeleccionDeTabla);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicio menuInicio = new MenuInicio();
                frame.dispose();
            }
        });







    }
}
