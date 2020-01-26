package com.company.UI;

import com.company.Clases.Maquina;
import com.company.Clases.Tarea;
import com.company.Clases.Trabajador;
import com.company.Clases.TrabajoTarea;
import com.company.LlamadasBD;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Paso2Tareas {
    private JPanel PanelTareas;
    private JTable TablaTareas;
    private JTable TablaMaquinasDisponibles;
    private JTable TablaMaquinasSeleccionadas;
    private JButton AnadirButton;
    private JButton SiguienteButton;
    private JButton EliminarButton;
    private JButton CancelarButton;
    JFrame frame;
    JSpinner SpinnerHoras;
    private JSpinner SpinnerMinutos;

    public Paso2Tareas(Trabajador trabajador) {

        ArrayList<Tarea> tareas = new ArrayList<>();
        ArrayList<Maquina> maquinas = new ArrayList<>();
        ArrayList<Maquina> maquinasSeleccionadas = new ArrayList<>();

        DefaultTableModel model1 = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        DefaultTableModel model3 = new DefaultTableModel();


        LlamadasBD llamadasBD = new LlamadasBD();
        frame = new JFrame("Paso 2: TAREAS");
        frame.setSize(1280, 720);
        frame.setContentPane(PanelTareas);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //Creo los modelos de los Spinner para que sean más fáciles de usar y evitar errores por parte del usuario.
        SpinnerModel modelMinutos = new SpinnerNumberModel(0, 0, 60, 5);
        SpinnerModel modelHoras = new SpinnerNumberModel(0, 0, 24, 1);

        SpinnerMinutos.setModel(modelMinutos);
        SpinnerHoras.setModel(modelHoras);

        //Defino los modelos de las tres tablas.

        //TABLA TAREAS
        model1 = new DefaultTableModel() {
            String[] columnas = {"Codigo", "Descripción", "Tiene máquina"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        //TABLA MAQUINAS DISPONIBLES
        model2 = new DefaultTableModel() {
            String[] columnas = {"Codigo", "Descripción"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        //TABLA MAQUINAS SELECCIONADAS
        model3 = new DefaultTableModel() {
            String[] columnas = {"Codigo", "Descripción", "Duración (Minutos totales)"};

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int index) {
                return columnas[index];
            }

        };

        //Hago que solo se pueda seleccionar una fila a la vez.
        TablaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaMaquinasDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaMaquinasSeleccionadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Desactivo al modificación de tablas
        TablaTareas.setDefaultEditor(Object.class, null);
        TablaMaquinasDisponibles.setDefaultEditor(Object.class, null);
        TablaMaquinasSeleccionadas.setDefaultEditor(Object.class, null);


        //Asigno los modelos.
        TablaTareas.setModel(model1);
        TablaMaquinasDisponibles.setModel(model2);
        TablaMaquinasSeleccionadas.setModel(model3);

        //Desactivo las tablas y los botones hasta que se seleccione una tarea con máquina.
        DesactivarMaquinas();


        //Relleno las tablas con contenido.

        //TABLA TAREAS
        tareas = llamadasBD.LeerTareas();
        String maquina = "";

        for (int i = 0; i < tareas.size(); i++) {

            if (tareas.get(i).isMaquina() == true) {
                maquina = "SI";
            } else {
                maquina = "NO";
            }

            model1.addRow(new Object[]{tareas.get(i).getCodigo(), tareas.get(i).getDescripcion(), maquina});
        }

        //TABLA MAQUINAS DISPONIBLES
        maquinas = llamadasBD.LeerMaquinas();
        for (int i = 0; i < maquinas.size(); i++) {
            model2.addRow(new Object[]{maquinas.get(i).getCodigo(), maquinas.get(i).getDescripcion()});
        }


        //Éste action se ejecuta cuando el usuario selecciona una fila de la tabla.
        TablaTareas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (TablaTareas.getSelectedRow() > -1) {
                    //Verifico si la línea seleccionada dispone de máquinas o no.
                    if (TablaTareas.getValueAt(TablaTareas.getSelectedRow(), 2).toString().equals("NO")) {
                        DesactivarMaquinas();
                    } else {
                        ActivarMaquinas();
                    }

                }
            }
        });


        //Sin cancela, regresa al menú principal.
        CancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicial menuInicial = new MenuInicial();
                frame.dispose();
            }
        });

        DefaultTableModel finalModel1 = model3;


        AnadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (TablaMaquinasDisponibles.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna máquina.");
                } else {
                    String PK = TablaMaquinasDisponibles.getValueAt(TablaMaquinasDisponibles.getSelectedRow(), 0).toString();

                    //Si está duplicado no lo añado a la otra tabla.
                    boolean duplicado = false;
                    for (int i = 0; i < maquinasSeleccionadas.size(); i++) {
                        if (maquinasSeleccionadas.get(i).getCodigo().equals(PK)) {
                            duplicado = true;
                        }
                    }

                    if (duplicado == false) {
                        Maquina nuevaMaquina = new Maquina();
                        nuevaMaquina = llamadasBD.LeerMaquinaConcreta(PK);


                        int horas = (Integer) SpinnerHoras.getValue();
                        int minutos = (Integer) SpinnerMinutos.getValue();

                        minutos = (horas * 60) + minutos;

                        nuevaMaquina.setDuracion(minutos);
                        maquinasSeleccionadas.add(nuevaMaquina);

                        ActualzarTablaMaquinasSeleccionadas(maquinasSeleccionadas, finalModel1);

                        SpinnerHoras.setValue(0);
                        SpinnerMinutos.setValue(0);
                    }
                }
            }
        });

        EliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TablaMaquinasSeleccionadas.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna máquina.");
                } else {

                    String PK = TablaMaquinasSeleccionadas.getValueAt(TablaMaquinasSeleccionadas.getSelectedRow(), 0).toString();


                    for (int i = 0; i < maquinasSeleccionadas.size(); i++) {
                        if (maquinasSeleccionadas.get(i).getCodigo().equals(PK)) {
                            maquinasSeleccionadas.remove(i);
                        }
                    }

                    ActualzarTablaMaquinasSeleccionadas(maquinasSeleccionadas, finalModel1);

                }
            }
        });


        SiguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Antes de pasar al siguiente formulario, creo los objetos con todos los datos, los almaceno en un Array y
                //los envio a la próxima ventana.

                //Con éste if verifico que se haya seleccionado al menos una tarea.
                if (TablaTareas.getSelectionModel().isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna tarea.");
                } else {


                    //Verifico si la línea seleccionada dispone de máquinas o no.
                    if (TablaTareas.getValueAt(TablaTareas.getSelectedRow(), 2).toString().equals("SI") && maquinasSeleccionadas.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No has seleccionado máquinas.");
                    } else {

                        ArrayList<TrabajoTarea> trabajoTareas = new ArrayList<>();
                        Tarea tareaSeleccionada;
                        tareaSeleccionada = llamadasBD.LeerTareaConcreta(TablaTareas.getValueAt(TablaTareas.getSelectedRow(), 0).toString());


                        //Si es una tarea sin máquina la genero de manera diferente.
                        if(TablaTareas.getValueAt(TablaTareas.getSelectedRow(), 2).toString().equals("NO")){

                            TrabajoTarea trabajoTarea = new TrabajoTarea();
                            trabajoTarea.setCodigoTarea(tareaSeleccionada.getCodigo());
                            trabajoTarea.setDniTrabajador(trabajador.getDni());

                            int horas = (Integer) SpinnerHoras.getValue();
                            int minutos = (Integer) SpinnerMinutos.getValue();

                            minutos = (horas * 60) + minutos;

                            trabajoTarea.setDuracion(minutos);

                            trabajoTarea.setCodigoMaquina("");

                            String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
                            int fecha = Integer.parseInt(date);
                            trabajoTarea.setFechaRealizacion(fecha);
                            trabajoTareas.add(trabajoTarea);



                        }else{

                            for (int i = 0; i < maquinasSeleccionadas.size(); i++) {

                                TrabajoTarea trabajoTarea = new TrabajoTarea();
                                trabajoTarea.setCodigoMaquina(maquinasSeleccionadas.get(i).getCodigo());
                                trabajoTarea.setCodigoTarea(tareaSeleccionada.getCodigo());
                                trabajoTarea.setDniTrabajador(trabajador.getDni());
                                trabajoTarea.setDuracion(maquinasSeleccionadas.get(i).getDuracion());
                                String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
                                int fecha = Integer.parseInt(date);
                                trabajoTarea.setFechaRealizacion(fecha);

                                trabajoTareas.add(trabajoTarea);

                            }
                        }



                        Paso3Mantenimienos paso3Mantenimienos = new Paso3Mantenimienos(trabajoTareas, trabajador);
                        frame.dispose();
                    }



                }


            }
        });
    }


    private void ActivarMaquinas() {
        TablaMaquinasDisponibles.setEnabled(true);
        TablaMaquinasSeleccionadas.setEnabled(true);
        EliminarButton.setEnabled(true);
        AnadirButton.setEnabled(true);
    }

    private void DesactivarMaquinas() {
        TablaMaquinasDisponibles.setEnabled(false);
        TablaMaquinasSeleccionadas.setEnabled(false);
        EliminarButton.setEnabled(false);
        AnadirButton.setEnabled(false);
    }

    private void ActualzarTablaMaquinasSeleccionadas(ArrayList<Maquina> maquinas, DefaultTableModel model) {

        //Éste pequeño código sirve para limiar los datos de la tabla.
        int filas = model.getRowCount();
        //Elimino una a una las filas de la tabla.
        for (int i = filas - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        //Añado las nuevas líneas, ya filtradas.
        for (int i = 0; i < maquinas.size(); i++) {
            model.addRow(new Object[]{maquinas.get(i).getCodigo(), maquinas.get(i).getDescripcion(), maquinas.get(i).getDuracion()});
            //model.addRow(new Object[]{trabajadores.get(i).getDni(), trabajadores.get(i).getNombre(), trabajadores.get(i).getApellido1(), trabajadores.get(i).getApellido2(), icon});

        }
    }

}
