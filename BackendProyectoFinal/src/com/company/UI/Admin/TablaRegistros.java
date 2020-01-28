package com.company.UI.Admin;

import com.company.Clases.Registro;
import com.company.Clases.Usuario;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TablaRegistros {
    private JTable table1;
    private JLabel TituloText;
    private JButton VolverButton;
    private JPanel PanelTablaRegistros;
    JFrame frame;

    public TablaRegistros(Usuario usuarioActivo) {
        DefaultTableModel model;
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Menu admin");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTablaRegistros);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //Creo el modelo de la tabla.
        model = new DefaultTableModel() {
            String[] columnas = {"Responsable", "Accion", "Fecha realización"};

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
        table1.getColumnModel().getColumn(1).setPreferredWidth(700);


        //Relleno la tabla con información.
        ArrayList<Registro> registros = new ArrayList<>();
        registros = llamadasBD.LeerRegistros();


        for (int i = 0; i < registros.size(); i++) {
            model.addRow(new Object[]{registros.get(i).getUsuario(), registros.get(i).getAccion(), registros.get(i).getHora()});
        }


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalAdmin principalAdmin = new PrincipalAdmin(usuarioActivo);
                frame.dispose();
            }
        });
    }
}
