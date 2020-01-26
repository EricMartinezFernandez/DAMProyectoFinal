package com.company.UI.MenusInformes;

import javax.swing.*;

import com.company.LlamadasBD;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MenuPrincipalInformes {
    private JButton ButtonMensual;
    private JButton ButtonDiario;
    private JButton SalirButton;
    private JPanel PanelInformes;
    JFrame frame;

    public MenuPrincipalInformes() {
        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Menu informes");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelInformes);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        ButtonDiario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearExcelDiario();
            }
        });

    }

    public void CrearExcelDiario(){
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("AAA");

        try {
            FileOutputStream fileout = new FileOutputStream("Excel.xls");
            book.write(fileout);
            fileout.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
