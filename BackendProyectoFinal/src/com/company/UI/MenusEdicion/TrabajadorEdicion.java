package com.company.UI.MenusEdicion;

import com.company.Clases.Registro;
import com.company.Clases.Trabajador;
import com.company.Clases.Usuario;
import com.company.LlamadasBD;
import com.company.UI.TablaDeSeleccion;
import com.sun.media.sound.ModelOscillatorStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private JPanel PanelDeImagen;
    private JLabel MostarImagen;
    JFrame frame;
    final String[] rutaFinal = {""};

    public TrabajadorEdicion(Boolean conObjeto, Trabajador trabajador, Usuario usuarioActivo) {
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


        if (conObjeto == false) {
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);
        } else {
            CrearButton.setEnabled(false);
            TextDNI.setEnabled(false);

            //Rellenamos los campos con los datos el objeto a editar.
            TextDNI.setText(trabajador.getDni());
            TextNombre.setText(trabajador.getNombre());
            TextApellido1.setText(trabajador.getApellido1());
            TextApellido2.setText(trabajador.getApellido2());

            ImageIcon imageIcon = new ImageIcon(new ImageIcon(trabajador.getRutaFoto()).getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
            MostarImagen.setIcon(imageIcon);
        }


        VolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaDeSeleccion tablaDeSeleccion = new TablaDeSeleccion(0, usuarioActivo);
                frame.dispose();
            }
        });


        ImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cuando pulsan el botón de selección de imágen, abro el Filechooser restringido a archivos del tipo imagen.
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
                fc.showOpenDialog(null);
                File selected = fc.getSelectedFile();
                System.out.println(fc.getSelectedFile().getAbsolutePath());

                //Con éste try accedo a la ruta donde se almacenará la imágen seleccionada.

                String ruta = "";

                try {
                    File file = new File("rutaImagenes.txt");
                    BufferedReader reader = null;
                    reader = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        ruta = line;
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }


                //Aquí copio la imagen en la ruta común.
                FileInputStream in = null;
                try {
                    in = new FileInputStream(fc.getSelectedFile().getAbsolutePath());
                    FileOutputStream ou = new FileOutputStream(ruta + "\\" + selected.getName());
                    rutaFinal[0] = ruta + "\\" + selected.getName();

                    BufferedInputStream bin = new BufferedInputStream(in);
                    BufferedOutputStream bou = new BufferedOutputStream(ou);
                    int b = 0;

                    while (b != -1) {
                        b = bin.read();
                        bou.write(b);
                    }

                    bin.close();
                    bou.close();

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //Ahora a dibujar la imágen en su sitio
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(rutaFinal[0]).getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
                MostarImagen.setIcon(imageIcon);

            }
        });


        CrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Verifico que no haya campos vacíos
                if (TextDNI.getText().equals("") || TextDNI.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Faltan campos obligatorios. (*)");
                } else {

                    //Verifico que el DNI tenga el número de carácteres correcto.
                    if (TextDNI.getText().length() == 9) {
                        Trabajador nuevoTrabajador = new Trabajador();
                        nuevoTrabajador.setDni(TextDNI.getText());
                        nuevoTrabajador.setNombre(TextNombre.getText());
                        nuevoTrabajador.setApellido1(TextApellido1.getText());
                        nuevoTrabajador.setApellido2(TextApellido2.getText());
                        nuevoTrabajador.setRutaFoto(rutaFinal[0]);

                        llamadasBD.InsertarTrabajador(nuevoTrabajador);

                        //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                        Registro registro = new Registro(date, ("Creado un nuevo trabajador con el código: " + TextDNI.getText()), usuarioActivo.getUsername());
                        llamadasBD.InsertarRegistro(registro);



                        TextDNI.setText("");
                        TextNombre.setText("");
                        TextApellido1.setText("");
                        TextApellido2.setText("");
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
                        MostarImagen.setIcon(imageIcon);
                    } else {
                        JOptionPane.showMessageDialog(null, "DNI Incorrecto.");

                    }
                }

            }
        });


        BorrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamadasBD.EliminarTrabajador(trabajador.getDni());

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Borrado un trabajador con el código: " + trabajador.getDni()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);

                //Tras borrar los datos de la BD, ahora toca borrar la imágen de la memoria.
                File file = new File(trabajador.getRutaFoto());
                file.delete();

                //Vacío los campos para evitar confusiones.
                TextDNI.setText("");
                TextNombre.setText("");
                TextApellido1.setText("");
                TextApellido2.setText("");

                //Vaciar los campos incluye adjuntar una ruta falsa al ImageIcon.
                ImageIcon imageIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
                MostarImagen.setIcon(imageIcon);

                TextDNI.setEnabled(true);

                //Dejo los botones listo por si se quiere crear otro objeto.
                EditarButton.setEnabled(false);
                BorrarButton.setEnabled(false);
                CrearButton.setEnabled(true);
            }
        });


        EditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Trabajador nuevoTrabajador = new Trabajador();
                nuevoTrabajador.setDni(trabajador.getDni());
                nuevoTrabajador.setNombre(TextNombre.getText());
                nuevoTrabajador.setApellido1(TextApellido1.getText());
                nuevoTrabajador.setApellido2(TextApellido2.getText());


                if (rutaFinal[0].equals("") || rutaFinal[0].equals(null)) {
                    nuevoTrabajador.setRutaFoto(trabajador.getRutaFoto().replace("\\", "/"));
                } else {
                    nuevoTrabajador.setRutaFoto(rutaFinal[0].replace("\\", "/"));
                }
                llamadasBD.ModificarTrabajador(nuevoTrabajador);

                //Ahora realizo el registro para dejar constancia de los movimientos realizados.
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                Registro registro = new Registro(date, ("Modificado un trabajador con el código: " + trabajador.getDni()), usuarioActivo.getUsername());
                llamadasBD.InsertarRegistro(registro);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //MostarImagen = new JLabel(new ImageIcon("C:\\Users\\erica\\Desktop\\Imagenes\\Foto1.jpg"));
        //MostarImagen = new JLabel(new ImageIcon(new ImageIcon("C:\\Users\\erica\\Desktop\\Imagenes\\Foto1.jpg").getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT)));
        MostarImagen = new JLabel(new ImageIcon(new ImageIcon(rutaFinal[0]).getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT)));
    }


}
