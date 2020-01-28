package com.company.UI.MenusEdicion;

import com.company.Clases.*;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;

import javax.security.auth.login.CredentialException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MantenimientoEdicion {
    private JTextField TextCodigo;
    private JTextArea TextDescripcion;
    private JComboBox ComboCodMaquina;
    private JLabel TituloText;
    private JButton CrearButton;
    private JButton EditarButton;
    private JButton BorrarButton;
    private JButton VolverButton;
    private JPanel PanelMantenimientoEdicion;
    private JTextArea textAreaDescripcion;
    JFrame frame;

    public MantenimientoEdicion(Boolean conObjeto, Mantenimiento mantenimiento, Usuario usuarioActivo) {
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
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(3, usuarioActivo);
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
                    ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();
                    mantenimientos = llamadasBD.LeerMantenimientos();

                    for (int i = 0; i < mantenimientos.size(); i++) {
                        if (mantenimientos.get(i).getCodigo().toLowerCase().equals(TextCodigo.getText().toLowerCase())) {
                            duplicado = true;
                        }
                    }

                    if (duplicado == false) {
                        Mantenimiento nuevoMantenimiento = new Mantenimiento();
                        nuevoMantenimiento.setCodigo(TextCodigo.getText());
                        nuevoMantenimiento.setDescripcion(TextDescripcion.getText());
                        nuevoMantenimiento.setCodigoMaquina(ComboCodMaquina.getSelectedItem().toString());


                        llamadasBD.InsertarMantenimiento(nuevoMantenimiento);

                        //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                        Registro registro = new Registro(date, ("Creado un nuevo mantenimiento con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
                        llamadasBD.InsertarRegistro(registro);

                        //Una vez insertado, vacio los campos para evitar confusiones.
                        TextCodigo.setText("");
                        TextDescripcion.setText("");
                        ComboCodMaquina.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "El código ya está en uso.");

                    }


                }
            }
        });


        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                llamadasBD.EliminarMantenimiento(mantenimiento.getCodigo());

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Borrado mantenimiento con el código: " + mantenimiento.getCodigo()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);

                //Una vez eliminado, vacio los campos para evitar confusiones.
                TextCodigo.setText("");
                TextDescripcion.setText("");
                ComboCodMaquina.setSelectedIndex(0);

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
                Mantenimiento nuevoMantenimiento = new Mantenimiento();
                nuevoMantenimiento.setCodigo(TextCodigo.getText());
                nuevoMantenimiento.setDescripcion(TextDescripcion.getText());
                nuevoMantenimiento.setCodigoMaquina(ComboCodMaquina.getSelectedItem().toString());

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Editado mantenimiento con el código: " + TextCodigo.getText()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);

                llamadasBD.ModificarMantenimiento(nuevoMantenimiento);

            }
        });


    }
}
