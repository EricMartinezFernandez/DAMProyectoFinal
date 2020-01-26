package com.company.UI;

import com.company.Clases.Trabajador;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Paso1Usuarios {
    private JTable table1;
    private JButton SiguienteButton;
    private JButton CancelarButton;
    private JPanel PanelUsuarios;
    JFrame frame;

    public Paso1Usuarios() {
        LlamadasBD llamadasBD = new LlamadasBD();
        DefaultTableModel model = new DefaultTableModel();


        frame = new JFrame("Paso 1: USUARIO");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelUsuarios);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        String[] columnas = {"Nombre", "Apellido 1", "Apellido 2", "Foto"};

        //Creo el modelo de la tabla.

        model = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (getRowCount() > 0) {
                    Object value = getValueAt(0, column);
                    if (value != null) {
                        return getValueAt(0, column).getClass();
                    }
                }

                return super.getColumnClass(column);
            }
        };

        //Asigno el modelo a la tabla y establezco que solo se pueda seleccionar una línea a la vez.
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setModel(model);

        //Desactivo al modificación de tablas
        table1.setDefaultEditor(Object.class, null);


        //Relleno la tabla con datos.

        //Primero establezco la altura de las filas para poder insertar las imágenes.
        table1.setRowHeight(150);

        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        trabajadores = llamadasBD.LeerTrabajadores();


        for (int i = 0; i < trabajadores.size(); i++) {

            ImageIcon icon = new ImageIcon();
            try {
                //Selecciono la imagen del empleado y convierto su ruta en URL para poderla usar en el tipo Icon.
                URL url = new URL(new File(trabajadores.get(i).getRutaFoto()).toURI().toURL().toString());

                //Aquí cambio el tamaño de la imágen para que se vea correctamente.
                icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(110, 140, Image.SCALE_DEFAULT));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            model.addRow(new Object[]{trabajadores.get(i).getNombre(), trabajadores.get(i).getApellido1(), trabajadores.get(i).getApellido2(), icon});
        }

        ArrayList<Trabajador> finalTrabajadores = trabajadores;
        SiguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trabajador trabajador;

                if (!table1.getSelectionModel().isSelectionEmpty()) {
                    //Recojo la PK del la fila que a seleccionado el usuario, por ello se bloquea el campo en 0, que es la ubicación común de PK en la tabla.
                    String DNI = finalTrabajadores.get(table1.getSelectedRow()).getDni();
                    trabajador = llamadasBD.LeerTrabajadorConcreto(DNI);
                    Paso2Tareas paso2Tareas = new Paso2Tareas(trabajador);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No has seleccionado al trabajador.");
                }

            }
        });

        //Sin cancela, regresa al menú principal.
        CancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicial menuInicial = new MenuInicial();
                frame.dispose();
            }
        });

    }
}
