package com.smarttask.model;

/**
 * Clase que representa una tarea.
 */
public class Tarea implements Accionable {

    private final int id;
    private String nombre;
    private Prioridad prioridad;;
    private boolean completado;

    public Tarea(int id, String nombre, Prioridad prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.completado = false;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public boolean isCompletado() {
        return completado;
    }

    @Override
    public void marcarComoCompletada() {
        this.completado = true;
    }

    public void editarNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }
        this.nombre = nuevoNombre.trim();
    }

    public void editarPrioridad(Prioridad nuevaPrioridad) {
        if (nuevaPrioridad == null) {
            throw new IllegalArgumentException("La prioridad no puede ser nula.");
        }
        this.prioridad = nuevaPrioridad;
    }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + prioridad + " | " + 
               (completado ? "✔ Completada" : "Activa");
    }
}