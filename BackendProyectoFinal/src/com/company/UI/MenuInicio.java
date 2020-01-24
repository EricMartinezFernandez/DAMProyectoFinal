package com.company.UI;

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

                MenuSeleccionDeTabla menuSeleccionDeTabla = new MenuSeleccionDeTabla();
                frame.dispose();
            }
        });

        InformesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });


        RutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(null);
                System.out.println(fc.getSelectedFile().getAbsolutePath());
                String ruta = fc.getSelectedFile().getAbsolutePath();
                BufferedWriter writer = null;

                try {
                    writer = new BufferedWriter(new FileWriter("rutaImagenes.txt"));
                    writer.write(ruta);
                    writer.close();

                    //El siguiente try actualiza el letrero con la ruta.
                    try {
                        File file = new File("rutaImagenes.txt");
                        BufferedReader reader = null;
                        reader = new BufferedReader(new FileReader(file));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            TextRuta.setText(line);
                        }

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
