package com.company.UI;

import com.company.UI.MenusInformes.MenuPrincipalInformes;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuInicio {

    private JPanel panelMenuInicio;
    private JButton DatosButton;
    private JButton InformesButton;
    private JButton RutaButton;
    private JLabel TextRuta;
    private static JFrame frame;

    public MenuInicio() {
        JFileChooser fc = new JFileChooser();

//Ésto sirve para que la ruta del label esté actualizada en el momento de abrir la ventana.
        try {
            File file = new File("rutaImagenes.txt");
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                TextRuta.setText(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        frame = new JFrame("Menu principal");
        frame.setSize(1280, 720);
        frame.setContentPane(panelMenuInicio);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        DatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //MenuSeleccionDeTabla menuSeleccionDeTabla = new MenuSeleccionDeTabla();
               // frame.dispose();
            }
        });

        InformesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipalInformes menuPrincipalInformes = new MenuPrincipalInformes();
                frame.dispose();
            }
        });


        RutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  /*
                Básicamente para que las aplicaciones compartan las imágenes deben de buscar el directorio con un
                FileChooser. Una vez localizado, copio la ruta y la guardo en un txt a la altura de éste proyecto.
                 */

                //Creo el Filechooser cuando pulsan el boton de establecer ruta.
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(null);
                System.out.println(fc.getSelectedFile().getAbsolutePath());
                //Obtengo la ruta y creo un Writer para escribirla en un txt.
                String ruta = fc.getSelectedFile().getAbsolutePath();
                BufferedWriter writer = null;

                try {
                    writer = new BufferedWriter(new FileWriter("rutaImagenes.txt"));
                    writer.write(ruta);
                    writer.close();

                    //El siguiente try actualiza el letrero con la ruta.
                    try {

                        //Leo el archivo y escribo su información en un label.
                        File file = new File("rutaImagenes.txt");
                        BufferedReader reader = null;
                        reader = new BufferedReader(new FileReader(file));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            TextRuta.setText(line);
                        }

                        //Informar nunca está de más.
                        JOptionPane.showMessageDialog(null, "Ruta asignada correctamente.");

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }


}
