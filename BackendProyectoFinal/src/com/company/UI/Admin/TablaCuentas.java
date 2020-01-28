package com.company.UI.Admin;

import com.company.Clases.Tarea;
import com.company.Clases.Usuario;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TablaCuentas {
    private JTable table1;
    private JTextField TextUserPK;
    private JButton BuscarButton;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton VolverButton;
    private JPanel PanelTablaCuentas;
    JFrame frame;

    public TablaCuentas(Usuario usuarioActivo) {
        DefaultTableModel model;
        JFileChooser fc = new JFileChooser();
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Menu seleccion de usuario");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTablaCuentas);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //Creo el modelo de la tabla.
        model = new DefaultTableModel() {
            String[] columnas = {"Usuario", "Permisos"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setDefaultEditor(Object.class, null);
        table1.setModel(model);

        //Relleno la tabla con información.
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = llamadasBD.LeerUsuarios();

        String permisos = "";

        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getPermisos() == 1) {
                permisos = "Datos";
            } else if(usuarios.get(i).getPermisos() == 2){
                permisos = "Informes";
            }else if(usuarios.get(i).getPermisos() == 3){
                permisos = "Admin";
            }

            model.addRow(new Object[]{usuarios.get(i).getUsername(), permisos});
        }



        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalAdmin principalAdmin = new PrincipalAdmin(usuarioActivo);
                frame.dispose();
            }
        });


        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario();
                UsuarioEdicion usuarioEdicion = new UsuarioEdicion(false, usuario, usuarioActivo);
                frame.dispose();
            }
        });

        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario();
                usuario = llamadasBD.LeerUsuarioConcreto(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 0)));
                UsuarioEdicion usuarioEdicion = new UsuarioEdicion(true, usuario, usuarioActivo);
                frame.dispose();
            }
        });
    }
}
