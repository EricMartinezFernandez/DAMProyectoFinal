package com.company.UI.MenusEdicion;

import com.company.Clases.Mantenimiento;
import com.company.Clases.Maquina;
import com.company.Clases.Registro;
import com.company.Clases.Usuario;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public MaquinaEdicion(boolean conObjeto, Maquina maquina, Usuario usuarioActivo) {

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
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(2, usuarioActivo);
                frame.dispose();
            }
        });


        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (TextCodigo.getText().equals("") || TextCodigo.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Faltan campos obligatorios. (*)");
                } else {

                    boolean duplicado = false;
                    ArrayList<Maquina> maquinas = new ArrayList<>();
                    maquinas = llamadasBD.LeerMaquinas();

                    for (int i = 0; i < maquinas.size(); i++) {
                        if (maquinas.get(i).getCodigo().toLowerCase().equals(TextCodigo.getText().toLowerCase())) {
                            duplicado = true;
                        }
                    }

                    if (duplicado == false) {
                        Maquina nuevaMaquina = new Maquina();
                        nuevaMaquina.setCodigo(TextCodigo.getText());
                        nuevaMaquina.setDescripcion(TextDescripcion.getText());
                        llamadasBD.InsertarMaquina(nuevaMaquina);

                        //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                        Registro registro = new Registro(date, ("Creada una nueva máquina con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
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
                llamadasBD.EliminarMaquina(TextCodigo.getText());

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Borrada una máquina con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
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
                Maquina nuevaMaquina = new Maquina();
                nuevaMaquina.setCodigo(TextCodigo.getText());
                nuevaMaquina.setDescripcion(TextDescripcion.getText());

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Editada una máquina con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);

                llamadasBD.ModificarMaquina(nuevaMaquina);
            }
        });
    }
}
