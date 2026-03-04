package com.smarttask.model;

public class ActualizacionTarea {

    private final String nombre;
    private final Prioridad prioridad;

    public ActualizacionTarea(String nombre, Prioridad prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }
}