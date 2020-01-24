package com.company.UI.MenusEdicion;

import com.company.Clases.Trabajador;
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

    public TrabajadorEdicion(Boolean conObjeto, Trabajador trabajador) {
        final LlamadasBD[] llamadasBD = {new LlamadasBD()};
        JFileChooser fc = new JFileChooser();
        frame = new JFrame("Edición de trabajador");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTrabajoEdicion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        final String[] rutaFinal = {""};

        if (conObjeto == false) {
            EditarButton.setEnabled(false);
            BorrarButton.setEnabled(false);
        } else {
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

                /*BufferedImage myPicture = null;
                MostarImagen = new JLabel("");
                MostarImagen.setIcon(new ImageIcon(TrabajadorEdicion.class.getResource(rutaFinal[0])));
                MostarImagen.setBounds(10, 11, 321, 227);
                PanelDeImagen.add(MostarImagen);
                 */
                System.out.println(rutaFinal[0]);
                MostarImagen = new JLabel(new ImageIcon(rutaFinal[0]));

            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //MostarImagen = new JLabel(new ImageIcon("C:\\Users\\erica\\Desktop\\Imagenes\\Foto1.jpg"));
        MostarImagen = new JLabel(new ImageIcon(new ImageIcon("C:\\Users\\erica\\Desktop\\Imagenes\\Foto1.jpg").getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT)));
        //MostarImagen.setIcon(new ImageIcon(new ImageIcon("icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
    }



}
