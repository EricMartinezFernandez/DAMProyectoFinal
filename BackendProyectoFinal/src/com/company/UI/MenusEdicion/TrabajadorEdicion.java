package com.company.UI.MenusEdicion;

import com.company.Clases.Trabajador;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrabajadorEdicion {
    private JTextField TextDNI;
    private JTextField TextNombre;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton BorrarButton;
    private JButton VolverButton;
    private JPanel PanelTrabajoEdicion;
    private JTextField TextApellido1;
    private JTextField TextApellido2;
    private JButton ImagenButton;
    JFrame frame;

    public TrabajadorEdicion(Boolean conObjeto, Trabajador trabajador) {
        LlamadasBD llamadasBD = new LlamadasBD();
        JFileChooser fc = new JFileChooser();
        frame = new JFrame("Edición de trabajador");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTrabajoEdicion);
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
            TextDNI.setEnabled(false);
        }


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(0);
                frame.dispose();
            }
        });


        ImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cuando pulsan el botón de selección de imágen, abro el Filechooser restringido a archivos del tipo imagen.
                fc.setAcceptAllFileFilterUsed (false);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
                fc.showOpenDialog(null);
                System.out.println(fc.getSelectedFile().getAbsolutePath());
            }
        });

    }
}
