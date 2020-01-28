package com.company.UI.MenusEdicion;

import com.company.Clases.Maquina;
import com.company.Clases.Registro;
import com.company.Clases.Tarea;
import com.company.Clases.Usuario;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TareaEdicion {
    private JPanel PanelTareaEdicion;
    private JTextField TextCodigo;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton VolverButton;
    private JTextArea TextDescripcion;
    private JButton BorrarButton;
    private JCheckBox CheckMaquina;
    JFrame frame;

    public TareaEdicion(boolean conObjeto, Tarea tarea, Usuario usuarioActivo) {
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

        if (conObjeto == false) {//Ésta condición no tendría datos previos y por lo tanto el usuario solo podría crear un objeto desde cero.
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);

        } else {
            CrearButton.setEnabled(false);
            TextCodigo.setEnabled(false);
            TextCodigo.setText(tarea.getCodigo());
            TextDescripcion.setText(tarea.getDescripcion());
            CheckMaquina.setSelected(tarea.isMaquina());

            ArrayList<Maquina> maquinas = new ArrayList<>();
            maquinas = llamadasBD.LeerMaquinas();

        }


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(1, usuarioActivo);
                frame.dispose();
            }
        });

        //Boton crear que crea el objeto en base a los datos introducidos y lo inserta en la base de datos.
        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (TextCodigo.getText().equals("") || TextCodigo.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Faltan campos obligatorios. (*)");
                } else {

                    boolean duplicado = false;
                    ArrayList<Tarea> tareas = new ArrayList<>();
                    tareas = llamadasBD.LeerTareas();

                    for (int i = 0; i < tareas.size(); i++) {
                        if (tareas.get(i).getCodigo().toLowerCase().equals(TextCodigo.getText().toLowerCase())) {
                            duplicado = true;
                        }
                    }

                    if (duplicado == false) {
                        Tarea nuevaTarea = new Tarea();
                        nuevaTarea.setCodigo(TextCodigo.getText());
                        nuevaTarea.setDescripcion(TextDescripcion.getText());

                        nuevaTarea.setMaquina(CheckMaquina.isSelected());

                        llamadasBD.InsertarTarea(nuevaTarea);

                        //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                        Registro registro = new Registro(date, ("Creada una nueva tarea con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
                        llamadasBD.InsertarRegistro(registro);

                        //Una vez insertado, vacio los campos para evitar confusiones.
                        TextCodigo.setText("");
                        TextDescripcion.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "El código ya está en uso.");
                    }


                }
            }
        });


        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                llamadasBD.EliminarTarea(tarea.getCodigo());

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Borrada una tarea con el código: " + tarea.getCodigo()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);


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
                Tarea nuevaTarea = new Tarea();
                nuevaTarea.setCodigo(TextCodigo.getText());
                nuevaTarea.setDescripcion(TextDescripcion.getText());
                nuevaTarea.setMaquina(CheckMaquina.isSelected());


                llamadasBD.ModificarTarea(nuevaTarea);

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Editada una tarea con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);

            }
        });
    }


}
