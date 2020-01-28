package com.company.UI.MenusInformes;

import javax.swing.*;

import com.company.Clases.*;
import com.company.LlamadasBD;
import com.company.UI.InicioSesion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

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
    private JSpinner AnioMensualSpinner;
    private JSpinner MesMensualSpinner;
    JFrame frame;

    public MenuPrincipalInformes(Usuario usuarioActivo) {
        frame = new JFrame("Menu informes");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelInformes);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        //Creo los modelos de los spinner y los asigno a cada objeto.
        SpinnerModel DesdemodelAnio = new SpinnerNumberModel(2020, 1980, 2100, 1);
        SpinnerModel DesdemodelMes = new SpinnerNumberModel(01, 01, 12, 1);
        SpinnerModel DesdemodelDia = new SpinnerNumberModel(27, 01, 31, 1);

        SpinnerModel HastamodelAnio = new SpinnerNumberModel(2020, 1980, 2100, 1);
        SpinnerModel HastamodelMes = new SpinnerNumberModel(01, 01, 12, 1);
        SpinnerModel HastamodelDia = new SpinnerNumberModel(28, 01, 31, 1);

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

                    System.out.println("Sigo ejecutando");
                }

            }
        });

        ButtonMensual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String MesSpinnerString = String.format("%02d", (int) MesMensualSpinner.getValue());
                int mesSpinner = Integer.parseInt(MesSpinnerString);
                int anioSpinner = (Integer) DesdeAnioSpinner.getValue();


                //CrearExcelMensual(mesSpinner, anioSpinner);

                System.out.println("Sigo ejecutando");


            }
        });


        SalirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InicioSesion inicioSesion = new InicioSesion();
                frame.dispose();

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


        //Creamos el book, que es una especie de tabla que luego se convertirá en archivo.
        Workbook book = new HSSFWorkbook();

        //Creo un estilo de tabla para los títulos.
        CellStyle style = book.createCellStyle();//Create style
        Font font = book.createFont();//Create font
        font.setBold(true);//Make font bold
        style.setFont(font);//set it to bold
        int test = 1;

        long diasTotalesCopia = diasTotales;

        for (int i = 0; i < trabajadores.size(); i++) {//Cambia de pestaña
            Sheet sheet = book.createSheet(trabajadores.get(i).getDni() + " | " + trabajadores.get(i).getNombre());

            int copiaDia = desdeDiaSpinner;
            int copiaMes = desdeMesSpinner;
            int copiaAnio = desdeAnioSpinner;


            diasTotales = diasTotalesCopia;


            //Creo la primera línea que será rellenada en el for siguiente.
            Row row = sheet.createRow(0);
            diasTotales = diasTotales + 1;//Para compensar el desfase.

            //ÉSTE FOR AÑADE LOS MESES Y LOS DÍAS
            for (int j = 0; j < diasTotales; j++) {//Dibuja los meses

                YearMonth yearMonthObject = YearMonth.of(copiaAnio, copiaMes);
                int daysInMonth = yearMonthObject.lengthOfMonth();

                int diasRestantes = daysInMonth - copiaDia;

                if (diasRestantes > 0) {//Si es un día normal del mes.

                    if (j == 0) {//La primera columna es diferente.
                        String mes = meses[copiaMes - 1];
                        row.createCell(j).setCellValue(mes);
                        row.getCell(j).setCellStyle(style);//Establezco el estilo
                        diasTotales++;
                        j++;
                    }

                    row.createCell(j).setCellValue((daysInMonth - diasRestantes));
                    row.getCell(j).setCellStyle(style);//Establezco el estilo
                    copiaDia++;

                } else {//Una vez se acabe el mes.

                    //Añado el último día del mes
                    row.createCell(j).setCellValue((daysInMonth - diasRestantes));
                    row.getCell(j).setCellStyle(style);//Establezco el estilo
                    copiaDia++;

                    //Le sumo 1 al total de días para poner el nombre del mes sin descompensar.
                    diasTotales++;
                    j++;

                    //Establezco el día en 1 para resetear el siguiente mes.
                    copiaDia = 1;

                    //Escribo el nombre del mes
                    String mes = meses[copiaMes];
                    row.createCell(j).setCellValue(mes);
                    row.getCell(j).setCellStyle(style);//Establezco el estilo

                    if (copiaMes == 12) {
                        copiaMes = 1;
                        copiaAnio++;
                    } else {
                        copiaMes++;
                    }


                }


            }

            Row row2 = sheet.createRow(1);
            row2.createCell(0).setCellValue("TAREAS (Minutos)");
            row2.getCell(0).setCellStyle(style);//Establezco el estilo

            //Ésto es para el desfase.
            diasTotales = diasTotalesCopia + 3;

            //DOS FORs ENLAZADOS PARA RELLENAR UNA LÍNEA POR TAREA
            for (int j = 0; j < tareas.size(); j++) {
                Row row3 = sheet.createRow(j + 2);
                row3.createCell(0).setCellValue(tareas.get(j).getCodigo());
                row3.getCell(0).setCellStyle(style);//Establezco el estilo

                //Vuelvo a reiniciarlos para partir de cero.
                copiaDia = desdeDiaSpinner;
                copiaMes = desdeMesSpinner;
                copiaAnio = desdeAnioSpinner;

                for (int k = 1; k < diasTotales; k++) {//Contador con días totales.

                    YearMonth yearMonthObject = YearMonth.of(copiaAnio, copiaMes);
                    int daysInMonth = yearMonthObject.lengthOfMonth();

                    int diasRestantes = daysInMonth - copiaDia;


                    if (diasRestantes > 0) {//Si es un día normal del mes.

                        //Compruebo cuantas horas a realizado la tarea el trabajador..
                        int minutosTotales = 0;

                        for (int l = 0; l < trabajoTareas.size(); l++) {


                            int dia = daysInMonth - diasRestantes;
                            String fechaConcatenada = "" + String.format("%02d", copiaAnio) + String.format("%02d", copiaMes) + String.format("%02d", dia);
                            int fechaConcatenadaInt = Integer.parseInt(fechaConcatenada);


                            //Traducción: Si el trabajador es el mismo que el de la página actual, a realizado el mismo trabajo y es en el mismo día, guardo el tiempo.
                            if (trabajoTareas.get(l).getDniTrabajador().equals(trabajadores.get(i).getDni()) && trabajoTareas.get(l).getCodigoTarea().equals(tareas.get(j).getCodigo()) && trabajoTareas.get(l).getFechaRealizacion() == fechaConcatenadaInt) {
                                minutosTotales = minutosTotales + trabajoTareas.get(l).getDuracion();
                            } else {
                                row3.createCell(k).setCellValue("X");
                            }

                        }

                        row3.createCell(k).setCellValue(minutosTotales);
                        copiaDia++;

                    } else {//Una vez se acabe el mes.
                        int minutosTotales = 0;

                        for (int l = 0; l < trabajoTareas.size(); l++) {


                            int dia = daysInMonth - diasRestantes;
                            String fechaConcatenada = "" + String.format("%02d", copiaAnio) + String.format("%02d", copiaMes) + String.format("%02d", dia);
                            int fehcaConcatenadaInt = Integer.parseInt(fechaConcatenada);

                            //Traducción: Si el trabajador es el mismo que el de la página actual, a realizado el mismo trabajo y es en el mismo día, guardo el tiempo.
                            if (trabajoTareas.get(l).getDniTrabajador().equals(trabajadores.get(i).getDni()) && trabajoTareas.get(l).getCodigoTarea().equals(tareas.get(j).getCodigo()) && trabajoTareas.get(l).getFechaRealizacion() == fehcaConcatenadaInt) {
                                minutosTotales = minutosTotales + trabajoTareas.get(l).getDuracion();
                            } else {
                                row3.createCell(k).setCellValue("X");
                            }
                        }

                        row3.createCell(k).setCellValue(minutosTotales);
                        copiaDia++;


                        //Establezco el día en 1 para resetear el siguiente mes.
                        copiaDia = 1;

                        if (copiaMes == 12) {
                            copiaAnio++;
                            copiaMes = 1;
                        } else {
                            copiaMes++;
                        }


                    }


                }


            }


            Row row4 = sheet.createRow(2 + tareas.size());
            row4.createCell(0).setCellValue("MAQUINAS (Minutos)");
            row4.getCell(0).setCellStyle(style);//Establezco el estilo


            //DOS FORs ENLAZADOS PARA RELLENAR UNA LÍNEA POR MAQUINA
            for (int j = 0; j < maquinas.size(); j++) {

                Row row5 = sheet.createRow(j + 3 + tareas.size());
                row5.createCell(0).setCellValue(maquinas.get(j).getCodigo());
                row5.getCell(0).setCellStyle(style);//Establezco el estilo

                //Vuelvo a reiniciarlos para partir de cero.
                copiaDia = desdeDiaSpinner;
                copiaMes = desdeMesSpinner;
                copiaAnio = desdeAnioSpinner;


                for (int k = 1; k < diasTotales; k++) {//Contador con días totales.

                    YearMonth yearMonthObject = YearMonth.of(copiaAnio, copiaMes);
                    int daysInMonth = yearMonthObject.lengthOfMonth();

                    int diasRestantes = daysInMonth - copiaDia;


                    if (diasRestantes > 0) {//Si es un día normal del mes.

                        //Compruebo cuantas horas a realizado la tarea el trabajador..

                        for (int l = 0; l < trabajoTareas.size(); l++) {


                            int dia = daysInMonth - diasRestantes;
                            String fechaConcatenada = "" + String.format("%02d", copiaAnio) + String.format("%02d", copiaMes) + String.format("%02d", dia);
                            int fechaConcatenadaInt = Integer.parseInt(fechaConcatenada);

                            //Traducción: Si el trabajador es el mismo que el de la página actual, a realizado el mismo trabajo y es en el mismo día, guardo el tiempo.
                            if (trabajoTareas.get(l).getDniTrabajador().equals(trabajadores.get(i).getDni()) && trabajoTareas.get(l).getCodigoMaquina().equals(maquinas.get(j).getCodigo()) && trabajoTareas.get(l).getFechaRealizacion() == fechaConcatenadaInt) {
                                row5.createCell(k).setCellValue(trabajoTareas.get(l).getDuracion());
                                break;
                            } else {
                                row5.createCell(k).setCellValue(0);
                            }


                        }

                        copiaDia++;

                    } else {//Una vez se acabe el mes.
                        int minutosTotales = 0;

                        for (int l = 0; l < trabajoTareas.size(); l++) {


                            int dia = daysInMonth - diasRestantes;
                            String fechaConcatenada = "" + String.format("%02d", copiaAnio) + String.format("%02d", copiaMes) + String.format("%02d", dia);
                            int fechaConcatenadaInt = Integer.parseInt(fechaConcatenada);

                            //Traducción: Si el trabajador es el mismo que el de la página actual, a realizado el mismo trabajo y es en el mismo día, guardo el tiempo.
                            if (trabajoTareas.get(l).getDniTrabajador().equals(trabajadores.get(i).getDni()) && trabajoTareas.get(l).getCodigoMaquina().equals(maquinas.get(j).getCodigo()) && trabajoTareas.get(l).getFechaRealizacion() == fechaConcatenadaInt) {
                                row5.createCell(k).setCellValue(trabajoTareas.get(l).getDuracion());
                                break;
                            } else {
                                row5.createCell(k).setCellValue(0);
                            }
                        }

                        copiaDia++;

                        //Establezco el día en 1 para resetear el siguiente mes.
                        copiaDia = 1;

                        if (copiaMes == 12) {
                            copiaAnio++;
                            copiaMes = 1;
                        } else {
                            copiaMes++;
                        }


                    }


                }


            }

            Row row6 = sheet.createRow(3 + tareas.size() + maquinas.size());
            row6.createCell(0).setCellValue("MANTENIMIENTO (Minutos)");
            row6.getCell(0).setCellStyle(style);//Establezco el estilo

            //DOS FORs ENLAZADOS PARA RELLENAR UNA LÍNEA POR MANTENIMIENTO
            for (int j = 0; j < mantenimientos.size(); j++) {

                Row row7 = sheet.createRow(j + 4 + tareas.size() + maquinas.size());
                row7.createCell(0).setCellValue(maquinas.get(j).getCodigo());
                row7.getCell(0).setCellStyle(style);//Establezco el estilo

                //Vuelvo a reiniciarlos para partir de cero.
                copiaDia = desdeDiaSpinner;
                copiaMes = desdeMesSpinner;
                copiaAnio = desdeAnioSpinner;


                for (int k = 1; k < diasTotales; k++) {//Contador con días totales.

                    YearMonth yearMonthObject = YearMonth.of(copiaAnio, copiaMes);
                    int daysInMonth = yearMonthObject.lengthOfMonth();

                    int diasRestantes = daysInMonth - copiaDia;


                    if (diasRestantes > 0) {//Si es un día normal del mes.

                        //Compruebo cuantas horas a realizado la tarea el trabajador..

                        for (int l = 0; l < trabajoMantenimientos.size(); l++) {


                            int dia = daysInMonth - diasRestantes;
                            String fechaConcatenada = "" + String.format("%02d", copiaAnio) + String.format("%02d", copiaMes) + String.format("%02d", dia);
                            int fechaConcatenadaInt = Integer.parseInt(fechaConcatenada);

                            //Traducción: Si el trabajador es el mismo que el de la página actual, a realizado el mismo trabajo y es en el mismo día, guardo el tiempo.
                            if (trabajoMantenimientos.get(l).getDniTrabajador().equals(trabajadores.get(i).getDni()) && trabajoMantenimientos.get(l).getCodigoMantenimiento().equals(mantenimientos.get(j).getCodigo()) && trabajoMantenimientos.get(l).getFechaRealizacion() == fechaConcatenadaInt) {
                                row7.createCell(k).setCellValue(trabajoMantenimientos.get(l).getDuracion());
                                break;
                            } else {
                                row7.createCell(k).setCellValue(0);
                            }


                        }

                        copiaDia++;

                    } else {//Una vez se acabe el mes.
                        int minutosTotales = 0;

                        for (int l = 0; l < trabajoMantenimientos.size(); l++) {


                            int dia = daysInMonth - diasRestantes;
                            String fechaConcatenada = "" + String.format("%02d", copiaAnio) + String.format("%02d", copiaMes) + String.format("%02d", dia);
                            int fechaConcatenadaInt = Integer.parseInt(fechaConcatenada);


                            //Traducción: Si el trabajador es el mismo que el de la página actual, a realizado el mismo trabajo y es en el mismo día, guardo el tiempo.
                            if (trabajoMantenimientos.get(l).getDniTrabajador().equals(trabajadores.get(i).getDni()) && trabajoMantenimientos.get(l).getCodigoMantenimiento().equals(mantenimientos.get(j).getCodigo()) && trabajoMantenimientos.get(l).getFechaRealizacion() == fechaConcatenadaInt) {
                                row7.createCell(k).setCellValue(trabajoMantenimientos.get(l).getDuracion());
                                break;
                            } else {
                                row7.createCell(k).setCellValue(0);
                            }
                        }

                        copiaDia++;

                        //Establezco el día en 1 para resetear el siguiente mes.
                        copiaDia = 1;

                        if (copiaMes == 12) {
                            copiaAnio++;
                            copiaMes = 1;
                        } else {
                            copiaMes++;
                        }


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
