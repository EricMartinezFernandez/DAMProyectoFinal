package com.company.UI;

import com.company.Clases.Usuario;
import com.company.LlamadasBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InicioDeSesion {
    private JPasswordField TextContraseña;
    private JTextField TextNombre;
    private JButton IniciarSesionButton;
    private JButton VolverButton;
    private JPanel PanelInicioSesion;
    JFrame frame;
    LlamadasBD llamadasBD = new LlamadasBD();

    public InicioDeSesion() {
        frame = new JFrame("Menu principal");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelInicioSesion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        IniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IniciarSesion();
            }
        });


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicial menuInicial = new MenuInicial();
                frame.dispose();
            }
        });


        //Éste actionlistener se ejecuta al pulsar el enter.
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IniciarSesion();
            }
        };

        TextContraseña.addActionListener(action);

    }

    public void IniciarSesion() {
        //Para evitar problemas si no hay ninguna cuenta con permisos de administrador
        //acepto las credenciales "admin, admin".

        //Guardo todos los usuarios en un array.
        ArrayList<Usuario> usuarios = new ArrayList<>();

        usuarios = llamadasBD.LeerUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {

            //Si coinciden el usuario y la contraseña procedo al acceso de la siguiente ventan.
            if (usuarios.get(i).getUsername().equals(TextNombre.getText()) && usuarios.get(i).getPassword().equals(TextContraseña.getText())) {

                Usuario usuarioActivo = new Usuario();
                usuarioActivo.setUsername(usuarios.get(i).getUsername());

                switch (usuarios.get(i).getPermisos()) {

                    case 1://DATOS
                        JOptionPane.showMessageDialog(null, "No tienes permisos.");
                        break;

                    case 2://INFORMES
                        JOptionPane.showMessageDialog(null, "No tienes permisos.");
                        break;

                    case 3://ADMINISTRADOR
                        Admin admin = new Admin();
                        frame.dispose();
                        break;
                }


            }
        }
    }

}
