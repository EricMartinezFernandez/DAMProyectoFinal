package com.company.UI.MenusInformes;

import javax.swing.*;

import com.company.Clases.*;
import com.company.LlamadasBD;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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


        ButtonDiario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Leo los datos de los spinner.
                //myint = (Integer) jSpinner1.getValue();


                String desdeDiaSpinner = String.format("%02d", (int) DesdeDiaSpinner.getValue());
                String desdeMesSpinner = String.format("%02d", (int) DesdeMesSpinner.getValue());
                int desdeAnioSpinner = (Integer) DesdeAnioSpinner.getValue();
                String hastaDiaSpinner = String.format("%02d", (int) HastaDiaSpinner.getValue());
                String hastaMesSpinner = String.format("%02d", (int) HastaMesSpinner.getValue());
                int hastaAnioSpinner = (Integer) HastaAnioSpinner.getValue();

                String desdeString = "" + desdeAnioSpinner + desdeMesSpinner + desdeDiaSpinner;
                String hastaString = "" + hastaAnioSpinner + hastaMesSpinner + hastaDiaSpinner;


                int desde = Integer.parseInt(desdeString);
                int hasta = Integer.parseInt(hastaString);

                if (desde > hasta) {
                    JOptionPane.showMessageDialog(null, "La fecha de inicio es menor a la final");
                } else {

                    CrearExcelDiario(desde, hasta, desdeAnioSpinner, Integer.parseInt(desdeMesSpinner), Integer.parseInt(desdeDiaSpinner));

                }

            }
        });

    }

    public void CrearExcelDiario(int desde, int hasta, int desdeAnioSpinner, int desdeMesSpinner, int desdeDiaSpinner) {
        LlamadasBD llamadasBD = new LlamadasBD();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
        long diasTotales = 0;

        try {
            Date date1 = formatoFecha.parse(Integer.toString(desde));
            Date date2 = formatoFecha.parse(Integer.toString(hasta));
            diasTotales = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        diasTotales = TimeUnit.DAYS.convert(diasTotales, TimeUnit.MILLISECONDS);

        String meses[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

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

        //Creamos el book, que es una especie de tabla que luego se convertirá en archivo.
        Workbook book = new HSSFWorkbook();

        for (int i = 0; i < trabajadores.size(); i++) {//Cambia de pestaña
            Sheet sheet = book.createSheet(trabajadores.get(i).getDni() + " | " + trabajadores.get(i).getNombre());

            int copiaDia = desdeDiaSpinner;
            int copiaMes = desdeMesSpinner;
            int copiaAnio = desdeAnioSpinner;

            Row row = sheet.createRow(0);
            diasTotales = diasTotales + 1;//Para compensar el desfase.

            for (int j = 0; j < diasTotales; j++) {//Dibuja los meses

                YearMonth yearMonthObject = YearMonth.of(copiaAnio, copiaMes);
                int daysInMonth = yearMonthObject.lengthOfMonth();

                int diasRestantes = daysInMonth - copiaDia;

                if (diasRestantes > 0) {

                    if(j == 0){
                        String mes = meses[copiaMes - 1];
                        row.createCell(j).setCellValue(mes);
                        diasTotales++;
                        j++;
                    }

                    row.createCell(j).setCellValue((daysInMonth - diasRestantes));
                    copiaDia++;

                } else {

                    row.createCell(j).setCellValue((daysInMonth - diasRestantes));
                    copiaDia++;

                    //Le sumo 1 al total de días para poner el nombre del mes.
                    diasTotales++;
                    j++;

                    copiaDia = 1;

                    String mes = meses[copiaMes];
                    row.createCell(j).setCellValue(mes);


                    if (copiaMes == 12) {
                        copiaMes = 1;
                    } else {
                        copiaMes++;
                    }


                }


            }


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
