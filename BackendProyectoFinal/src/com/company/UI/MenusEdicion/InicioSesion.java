package com.company.UI.MenusEdicion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesion {
    private JPanel PanelInicioDeSesion;
    private JButton IniciarSesionButton;
    private JPasswordField textContraseña;
    private JTextField TextNombre;
    private JButton SalirButton;
    JFrame frame;

    public InicioSesion() {
        frame = new JFrame("Menu principal");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelInicioDeSesion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);



        SalirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        IniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
