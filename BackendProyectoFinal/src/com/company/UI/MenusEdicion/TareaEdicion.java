package com.company.UI.MenusEdicion;

import com.company.Clases.Tarea;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TareaEdicion {
    private JPanel PanelTareaEdicion;
    private JTextField TextCodigo;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton VolverButton;
    private JTextField TextDescripcion;
    private JTextField TextCodMaquina;
    private JButton BorrarButton;
    JFrame frame;

    public TareaEdicion(boolean conObjeto, Tarea tarea) {
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Edición de tarea");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTareaEdicion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if(conObjeto == false){
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);
        }else{
            CrearButton.setEnabled(false);
        }



        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(1);
                frame.dispose();
            }
        });


    }


}
