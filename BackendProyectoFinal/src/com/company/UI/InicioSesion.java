package com.company.UI;

import com.company.Clases.Usuario;
import com.company.LlamadasBD;
import com.company.UI.Admin.PrincipalAdmin;
import com.company.UI.MenusInformes.MenuPrincipalInformes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InicioSesion {
    private JPanel PanelInicioDeSesion;
    private JButton IniciarSesionButton;
    private JPasswordField TextContraseña;
    private JTextField TextNombre;
    private JButton SalirButton;
    JFrame frame;

    public InicioSesion() {
        LlamadasBD llamadasBD = new LlamadasBD();
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
                //Para evitar problemas si no hay ninguna cuenta con permisos de administrador
                //acepto las credenciales "admin, admin".

                //Guardo todos los usuarios en un array.
                ArrayList<Usuario> usuarios = new ArrayList<>();
                usuarios = llamadasBD.LeerUsuarios();

                boolean admin = false;

                for (int i = 0; i < usuarios.size(); i++) {

                    //Si encuentra a UN solo admin no se registrará la cuenta de seguridad (admin, admin)
                    if (usuarios.get(i).getPermisos() == 3) {
                        admin = true;
                    }
                }

                if (admin == false) {
                    Usuario administrador = new Usuario("admin", "admin", 3);
                    llamadasBD.InsertarUsuario(administrador, false);
                }

                //A PARTIR DE AQUÍ SE REALIZA UN INICIO DE SESIÓN NORMAL.
                usuarios = llamadasBD.LeerUsuarios();

                for (int i = 0; i < usuarios.size(); i++) {

                    //Si coinciden el usuario y la contraseña procedo al acceso de la siguiente ventan.
                    if (usuarios.get(i).getUsername().equals(TextNombre.getText()) && usuarios.get(i).getPassword().equals(TextContraseña.getText())) {

                        Usuario usuarioActivo = new Usuario();
                        usuarioActivo.setUsername(usuarios.get(i).getUsername());

                        switch (usuarios.get(i).getPermisos()) {

                            case 1://DATOS
                                MenuSeleccionDeTabla menuSeleccionDeTabla = new MenuSeleccionDeTabla(usuarioActivo);
                                frame.dispose();
                                break;

                            case 2://INFORMES
                                MenuPrincipalInformes menuPrincipalInformes = new MenuPrincipalInformes();
                                frame.dispose();
                                break;

                            case 3://ADMINISTRADOR
                                PrincipalAdmin principalAdmin = new PrincipalAdmin();
                                frame.dispose();
                                break;
                        }


                    }
                }


            }
        });
    }
}
