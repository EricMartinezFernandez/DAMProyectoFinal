package com.company.UI.MenusEdicion;

import com.company.Clases.Maquina;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaquinaEdicion {
    private JTextField TextCodigo;
    private JTextField TextDescripcion;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton BorrarButton;
    private JButton VolverButton;
    private JPanel PanelMaquinaEdicion;
    JFrame frame;

    public MaquinaEdicion(boolean conObjeto, Maquina maquina) {

        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Edición de máquina");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelMaquinaEdicion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //En base a si está editando o creando un objeto bloqueo ciertos botones.
        if (conObjeto == false) {
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);
        } else {
            CrearButton.setEnabled(false);
            TextCodigo.setEnabled(false);
            TextCodigo.setText(maquina.getCodigo());
            TextDescripcion.setText(maquina.getDescripcion());
        }


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(2);
                frame.dispose();
            }
        });


        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (TextCodigo.getText().equals("") || TextCodigo.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Faltan campos obligatorios. (*)");
                }else{
                    Maquina nuevaMaquina = new Maquina();
                    nuevaMaquina.setCodigo(TextCodigo.getText());
                    nuevaMaquina.setDescripcion(TextDescripcion.getText());
                    llamadasBD.InsertarMaquina(nuevaMaquina);
                    //Una vez insertado, vacio los campos para evitar confusiones.
                    TextCodigo.setText("");
                    TextDescripcion.setText("");
                }


            }
        });


        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamadasBD.EliminarMaquina(TextCodigo.getText());

                //Una vez eliminado, vacio los campos para evitar confusiones.
                TextCodigo.setText("");
                TextDescripcion.setText("");

                TextCodigo.setEnabled(true);

                //Dejo los botones listo por si se quiere crear otro objeto.
                EditarButton.setEnabled(false);
                BorrarButton.setEnabled(false);
                CrearButton.setEnabled(true);
            }
        });


        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Maquina nuevaMaquina = new Maquina();
                nuevaMaquina.setCodigo(TextCodigo.getText());
                nuevaMaquina.setDescripcion(TextDescripcion.getText());

                llamadasBD.ModificarMaquina(nuevaMaquina);
            }
        });
    }
}
