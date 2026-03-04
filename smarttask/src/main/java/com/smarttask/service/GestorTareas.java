package com.smarttask.service;

import java.util.ArrayList;
import java.util.List;

import com.smarttask.model.ActualizacionTarea;
import com.smarttask.model.Prioridad;
import com.smarttask.model.Tarea;
import com.smarttask.model.TareaUrgente;

public class GestorTareas {

    private final List<Tarea> tareas;
    private int contadorId;

    public GestorTareas() {
        tareas = new ArrayList<>();
        contadorId = 1;
    }

    public void agregarTarea(String nombre, Prioridad prioridad) {
        Tarea nueva;

            if (prioridad == Prioridad.URGENTE) {
                nueva = new TareaUrgente(contadorId++, nombre);
            } else {
                nueva = new Tarea(contadorId++, nombre, prioridad);
            }

            tareas.add(nueva);
        }

    public List<Tarea> listarTareas() {
        return tareas;
    }

    public boolean editarTarea(int id, ActualizacionTarea cambios) {

        for (Tarea t : tareas) {

            if (t.getId() == id) {

                if (cambios.getNombre() != null && !cambios.getNombre().isBlank()) {
                    t.editarNombre(cambios.getNombre());
                }

                if (cambios.getPrioridad() != null) {
                    t.editarPrioridad(cambios.getPrioridad());
                }

                return true;
            }
        }

        return false;
    }

    public boolean marcarComoCompletada(int id) {
        for (Tarea t : tareas) {
            if (t.getId() == id) {
                t.marcarComoCompletada();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarTarea(int id) {
        return tareas.removeIf(t -> t.getId() == id);
    }
}
