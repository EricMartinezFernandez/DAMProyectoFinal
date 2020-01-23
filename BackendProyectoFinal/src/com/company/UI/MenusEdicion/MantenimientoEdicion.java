package com.company.UI.MenusEdicion;

import com.company.Clases.Mantenimiento;
import com.company.Clases.Maquina;
import com.company.Clases.Tarea;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MantenimientoEdicion {
    private JTextField TextCodigo;
    private JTextField TextDescripcion;
    private JComboBox ComboCodMaquina;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton BorrarButton;
    private JButton VolverButton;
    private JPanel PanelMantenimientoEdicion;
    JFrame frame;

    public MantenimientoEdicion(Boolean conObjeto, Mantenimiento mantenimiento) {
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Edición de mantenimiento");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelMantenimientoEdicion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //En base a si está editando o creando un objeto bloqueo ciertos botones.
        if (conObjeto == false) {

            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);

            ArrayList<Maquina> maquinas = new ArrayList<>();
            maquinas = llamadasBD.LeerMaquinas();

            for (int i = 0; i < maquinas.size(); i++) {
                ComboCodMaquina.addItem(maquinas.get(i).getCodigo());
            }

            ComboCodMaquina.setSelectedIndex(0);


        } else {
            CrearButton.setEnabled(false);
            TextCodigo.setEnabled(false);
            TextCodigo.setText(mantenimiento.getCodigo());
            TextDescripcion.setText(mantenimiento.getDescripcion());

            if (mantenimiento.getCodigoMaquina() != null) {
                ComboCodMaquina.addItem(mantenimiento.getCodigoMaquina());
                ComboCodMaquina.setSelectedIndex(0);
            } else {
                ComboCodMaquina.setSelectedIndex(0);
            }


            ArrayList<Maquina> maquinas = new ArrayList<>();
            maquinas = llamadasBD.LeerMaquinas();

            for (int i = 0; i < maquinas.size(); i++) {

                if (!maquinas.get(i).getCodigo().equals(mantenimiento.getCodigoMaquina())) {
                    ComboCodMaquina.addItem(maquinas.get(i).getCodigo());
                }
            }


        }

        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(3);
                frame.dispose();
            }
        });

        //Boton crear que crea el objeto en base a los datos introducidos y lo inserta en la base de datos.
        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Mantenimiento nuevoMantenimiento = new Mantenimiento();
                nuevoMantenimiento.setCodigo(TextCodigo.getText());
                nuevoMantenimiento.setDescripcion(TextDescripcion.getText());
                nuevoMantenimiento.setCodigoMaquina(ComboCodMaquina.getSelectedItem().toString());


                llamadasBD.InsertarMantenimiento(nuevoMantenimiento);

                //Una vez insertado, vacio los campos para evitar confusiones.
                TextCodigo.setText("");
                TextDescripcion.setText("");
                ComboCodMaquina.setSelectedIndex(0);
            }
        });



        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                llamadasBD.EliminarMantenimiento(mantenimiento.getCodigo());

                //Una vez eliminado, vacio los campos para evitar confusiones.
                TextCodigo.setText("");
                TextDescripcion.setText("");
                ComboCodMaquina.setSelectedIndex(0);
            }
        });


        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mantenimiento nuevoMantenimiento = new Mantenimiento();
                nuevoMantenimiento.setCodigo(TextCodigo.getText());
                nuevoMantenimiento.setDescripcion(TextDescripcion.getText());
                nuevoMantenimiento.setCodigoMaquina(ComboCodMaquina.getSelectedItem().toString());

                llamadasBD.ModificarMantenimiento(nuevoMantenimiento);

            }
        });




    }
}
