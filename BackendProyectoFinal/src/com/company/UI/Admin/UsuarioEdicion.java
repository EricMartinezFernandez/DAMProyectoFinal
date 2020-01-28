package com.company.UI.Admin;

import com.company.Clases.Maquina;
import com.company.Clases.Usuario;
import com.company.LlamadasBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioEdicion {
    private JTextField TextUsuario;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton BorrarButton;
    private JButton VolverButton;
    private JPasswordField TextPassword;
    private JPanel PanelGestionUsuario;
    private JPasswordField TextPassword2;
    private JComboBox ComboTipoCuenta;
    JFrame frame;

    public UsuarioEdicion(Boolean conObjeto, Usuario usuario) {
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Menu gestion usuario");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelGestionUsuario);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        ComboTipoCuenta.addItem("Datos");
        ComboTipoCuenta.addItem("Informes");
        ComboTipoCuenta.addItem("Admin");

//En base a si está editando o creando un objeto bloqueo ciertos botones.
        if (conObjeto == false) {
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);
        } else {
            CrearButton.setEnabled(false);
            TextUsuario.setEnabled(false);
            TextUsuario.setText(usuario.getUsername());
            TextPassword.setText(usuario.getPassword());
            TextPassword2.setText(usuario.getPassword());

            //Dejo seleccionado el tipo de cuenta correcto dentro del combobox
            switch (usuario.getPermisos()){
                case 1:
                    ComboTipoCuenta.setSelectedIndex(0);
                    break;

                case 2:
                    ComboTipoCuenta.setSelectedIndex(1);
                    break;

                case 3:
                    ComboTipoCuenta.setSelectedIndex(2);
                    break;
            }

        }


        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Tomo medidas de seguridad para evitar problemas.
                if(TextPassword.getText().equals(TextPassword2.getText())){

                    if (TextUsuario.getText().equals("") || TextUsuario.getText().equals(null)){
                        JOptionPane.showMessageDialog(null, "Faltan campos obligatorios. (*)");
                    }else{
                        Usuario nuevoUsuario = new Usuario();

                        //Lo paso a minúsculas para hacer más fácil el inicio de sesión.
                        nuevoUsuario.setUsername(TextUsuario.getText().toLowerCase());
                        nuevoUsuario.setPassword(TextPassword.getText());

                        int permiso = ComboTipoCuenta.getSelectedIndex();
                        nuevoUsuario.setPermisos(permiso + 1);
                        llamadasBD.InsertarUsuario(nuevoUsuario, true);

                        TextUsuario.setText("");
                        TextPassword.setText("");
                        TextPassword2.setText("");
                        ComboTipoCuenta.setSelectedIndex(0);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                }


            }
        });

        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaCuentas tablaCuentas = new TablaCuentas();
                frame.dispose();
            }
        });


        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamadasBD.EliminarUsuario(TextUsuario.getText());

                //Una vez eliminado, vacio los campos para evitar confusiones.
                TextUsuario.setText("");
                TextPassword.setText("");
                TextPassword2.setText("");
                ComboTipoCuenta.setSelectedIndex(0);

                //Dejo los botones listo por si se quiere crear otro objeto.
                TextUsuario.setEnabled(true);
                EditarButton.setEnabled(false);
                BorrarButton.setEnabled(false);
                CrearButton.setEnabled(true);
            }
        });
        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(TextPassword.getText().equals(TextPassword2.getText())){

                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(TextUsuario.getText());
                    nuevoUsuario.setPassword(TextPassword.getText());
                    int permiso = ComboTipoCuenta.getSelectedIndex();
                    nuevoUsuario.setPermisos(permiso + 1);
                    llamadasBD.ModificarUsuario(nuevoUsuario);

                }else{
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                }

            }
        });
    }
}
