package com.company.UI;

import com.company.Clases.Usuario;
import com.company.LlamadasBD;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MenuInicial {
    private JButton IniciarButton;
    private JLabel TextRuta;
    private JPanel PanelInicio;
    private JButton AdminButton;
    JFrame frame;
    LlamadasBD llamadasBD = new LlamadasBD();


    public MenuInicial() {


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


        JFileChooser fc = new JFileChooser();
        frame = new JFrame("Menu principal");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelInicio);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        IniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paso1Usuarios paso1Usuarios = new Paso1Usuarios();
                frame.dispose();
            }
        });


        AdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InicioDeSesion inicioDeSesion = new InicioDeSesion();
                frame.dispose();
            }
        });
    }


}
