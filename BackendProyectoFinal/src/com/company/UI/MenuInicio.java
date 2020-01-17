package com.company.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicio {

    private JPanel panelMenuInicio;
    private JButton DatosButton;
    private JButton InformesButton;
    private static JFrame frame;

    public MenuInicio() {
        frame = new JFrame("Menu principal");
        frame.setSize(1280, 720);
        frame.setContentPane(panelMenuInicio);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);






        DatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MenuSeleccionDeTabla menuSeleccionDeTabla = new MenuSeleccionDeTabla();
                frame.dispose();
            }
        });

        InformesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });
    }


}
