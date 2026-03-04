package com.smarttask.model;

public class TareaUrgente extends Tarea {

    public TareaUrgente(int id, String nombre) {
        super(id, nombre, Prioridad.URGENTE);
    }

    @Override
    public String toString() {
        return "[!] " + super.toString();
    }
}