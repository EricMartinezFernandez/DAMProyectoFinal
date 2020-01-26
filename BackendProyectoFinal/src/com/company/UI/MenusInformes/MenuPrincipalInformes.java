package com.company.UI.MenusInformes;

import javax.swing.*;

import com.company.Clases.*;
import com.company.LlamadasBD;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;

public class MenuPrincipalInformes {
    private JButton ButtonMensual;
    private JButton ButtonDiario;
    private JButton SalirButton;
    private JPanel PanelInformes;
    private JSpinner DesdeAnioSpinner;
    private JSpinner DesdeMesSpinner;
    private JSpinner DesdeDiaSpinner;
    private JSpinner HastaAnioSpinner;
    private JSpinner HastaMesSpinner;
    private JSpinner HastaDiaSpinner;
    JFrame frame;

    public MenuPrincipalInformes() {
        frame = new JFrame("Menu informes");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelInformes);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);



        //Creo los modelos de los spinner y los asigno a cada objeto.
        SpinnerModel DesdemodelAnio = new SpinnerNumberModel(2019, 1980, 2100, 1);
        SpinnerModel DesdemodelMes = new SpinnerNumberModel(01, 01, 12, 1);
        SpinnerModel DesdemodelDia = new SpinnerNumberModel(01, 01, 31, 1);

        SpinnerModel HastamodelAnio = new SpinnerNumberModel(2019, 1980, 2100, 1);
        SpinnerModel HastamodelMes = new SpinnerNumberModel(01, 01, 12, 1);
        SpinnerModel HastamodelDia = new SpinnerNumberModel(01, 01, 31, 1);

        DesdeDiaSpinner.setModel(DesdemodelDia);
        DesdeMesSpinner.setModel(DesdemodelMes);
        DesdeAnioSpinner.setModel(DesdemodelAnio);

        HastaDiaSpinner.setModel(HastamodelDia);
        HastaMesSpinner.setModel(HastamodelMes);
        HastaAnioSpinner.setModel(HastamodelAnio);

        YearMonth yearMonthObject = YearMonth.of(1999, 2);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        System.out.println(daysInMonth);


        ButtonDiario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Leo los datos de los spinner.
                //myint = (Integer) jSpinner1.getValue();


                CrearExcelDiario();
            }
        });

    }

    public void CrearExcelDiario(int desdeDia, int desdeMes, int desdeAnio, int hastaDia, int hastaMes, int hastaAnio) {
        LlamadasBD llamadasBD = new LlamadasBD();

        //Leo todos los datos que necesitaré a la hora de crear las tablas.
        ArrayList<TrabajoMantenimiento> trabajoMantenimientos = new ArrayList<>();
        ArrayList<TrabajoTarea> trabajoTareas = new ArrayList<>();
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        ArrayList<Maquina> maquinas = new ArrayList<>();
        ArrayList<Tarea> tareas = new ArrayList<>();
        ArrayList<Mantenimiento> mantenimientos = new ArrayList<>();

        trabajoMantenimientos = llamadasBD.LeerTrabajoMantenimientos();
        trabajoTareas = llamadasBD.LeerTrabajoTareas();
        trabajadores = llamadasBD.LeerTrabajadores();
        maquinas = llamadasBD.LeerMaquinas();
        tareas = llamadasBD.LeerTareas();
        mantenimientos = llamadasBD.LeerMantenimientos();

        //https://www.youtube.com/watch?v=oG18JTIKtLo

        //Creamos el book, que es una especie de tabla que luego si convertirá en archivo.
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("AAA");

        for (int i = 0; i < 5; i++) {

        }


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
