package com.company.UI.MenusEdicion;

import com.company.Clases.Maquina;
import com.company.Clases.Tarea;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TareaEdicion {
    private JPanel PanelTareaEdicion;
    private JTextField TextCodigo;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton VolverButton;
    private JTextField TextDescripcion;
    private JButton BorrarButton;
    private JComboBox ComboCodMaquina;
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

        //En base a si está editando o creando un objeto bloqueo ciertos botones.
        if (conObjeto == false) {
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);
            ComboCodMaquina.addItem("");


            ArrayList<Maquina> maquinas = new ArrayList<>();
            maquinas = llamadasBD.LeerMaquinas();

            for (int i = 0; i < maquinas.size(); i++) {
                ComboCodMaquina.addItem(maquinas.get(i).getCodigo());
            }

            ComboCodMaquina.setSelectedIndex(0);


        } else {
            CrearButton.setEnabled(false);
            TextCodigo.setEnabled(false);
            TextCodigo.setText(tarea.getCodigo());
            TextDescripcion.setText(tarea.getDescripcion());
            ComboCodMaquina.addItem("");
            ComboCodMaquina.addItem(tarea.getCodigoMaquina());


            ArrayList<Maquina> maquinas = new ArrayList<>();
            maquinas = llamadasBD.LeerMaquinas();

            for (int i = 0; i < maquinas.size(); i++) {

                if (!maquinas.get(i).getCodigo().equals(tarea.getCodigoMaquina())) {
                    ComboCodMaquina.addItem(maquinas.get(i).getCodigo());
                }
            }

            ComboCodMaquina.setSelectedIndex(1);
        }


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(1);
                frame.dispose();
            }
        });

        //Boton crear que crea el objeto en base a los datos introducidos y lo inseta en la base de datos.
        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea nuevaTarea = new Tarea();
                nuevaTarea.setCodigo(TextCodigo.getText());
                nuevaTarea.setDescripcion(TextDescripcion.getText());

                //Éste if detecta si hay una máquina seleccionada o el espacio está vacío. De ésta manera el objeto deja la referencia en null y no copia el texto "".
                if (ComboCodMaquina.getSelectedIndex() != 0) {
                    nuevaTarea.setCodigoMaquina(ComboCodMaquina.getSelectedItem().toString());
                }

                llamadasBD.InsertarTarea(nuevaTarea);

                //Una vez insertado, vacio los campos para evitar confusiones.
                TextCodigo.setText("");
                TextDescripcion.setText("");
                ComboCodMaquina.setSelectedIndex(0);
            }
        });


        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                llamadasBD.EliminarTarea(tarea.getCodigo());


                //Una vez eliminado, vacio los campos para evitar confusiones.
                TextCodigo.setText("");
                TextDescripcion.setText("");
                ComboCodMaquina.setSelectedIndex(0);
            }
        });


        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea nuevaTarea = new Tarea();
                nuevaTarea.setCodigo(TextCodigo.getText());
                nuevaTarea.setDescripcion(TextDescripcion.getText());

                //Éste if detecta si hay una máquina seleccionada o el espacio está vacío. De ésta manera el objeto deja la referencia en null y no copia el texto "".
                if (ComboCodMaquina.getSelectedIndex() != 0) {
                    nuevaTarea.setCodigoMaquina(ComboCodMaquina.getSelectedItem().toString());
                }else{
                    nuevaTarea.setCodigoMaquina(null);
                }

                llamadasBD.ModificarTarea(nuevaTarea);
            }
        });
    }


}
