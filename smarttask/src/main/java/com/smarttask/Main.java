package com.smarttask;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import com.smarttask.model.ActualizacionTarea;
import com.smarttask.model.Prioridad;
import com.smarttask.model.Tarea;
import com.smarttask.service.GestorTareas;

public class Main {

    public static void main(String[] args) {
        ejecutar(System.in, System.out, new GestorTareas());
    }

    public static void ejecutar(InputStream input,
                                PrintStream output,
                                GestorTareas gestor) {

        try (Scanner scanner = new Scanner(input)) {

            output.println("Bienvenido a SmartTask, tu gestor amigable de tareas.");
            int opcion = 0;

            do {
                output.println("\n===== SMART TASK =====");
                output.println("1. Agregar tarea");
                output.println("2. Listar tareas");
                output.println("3. Marcar como completada");
                output.println("4. Editar tarea");
                output.println("5. Eliminar tarea");
                output.println("0. Salir");
                output.print("Seleccione opción: ");

                if (!scanner.hasNextInt()) {
                    output.println("⚠ Opción inválida.");
                    scanner.nextLine();
                    continue;
                }

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {

                    case 1 -> {
                        output.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        output.println("Seleccione prioridad:");
                        output.println("1. URGENTE");
                        output.println("2. Media");
                        output.println("3. Baja");
                        output.print("Opción: ");

                        int prioridadOpcion = scanner.nextInt();
                        scanner.nextLine();

                        Prioridad prioridad = switch (prioridadOpcion) {
                            case 1 -> Prioridad.URGENTE;
                            case 2 -> Prioridad.MEDIA;
                            case 3 -> Prioridad.BAJA;
                            default -> Prioridad.MEDIA;
                        };

                        gestor.agregarTarea(nombre, prioridad);
                        output.println("Tarea agregada correctamente.");
                    }

                    case 2 -> {
                        List<Tarea> tareas = gestor.listarTareas();

                        if (tareas.isEmpty()) {
                            output.println("⚠ No hay tareas ingresadas.");
                        } else {
                            output.println("\nID | Nombre | Prioridad | Estado");
                            tareas.forEach(output::println);
                        }
                    }

                    case 3 -> {
                        List<Tarea> tareas = gestor.listarTareas()
                                .stream()
                                .filter(t -> !t.isCompletado())
                                .toList();

                        if (tareas.isEmpty()) {
                            output.println("⚠ No hay tareas activas para completar.");
                        } else {
                            output.println("\nTareas activas:");
                            tareas.forEach(t ->
                                    output.println(t.getId() + " | " + t.getNombre())
                            );

                            output.print("Ingrese ID a completar: ");
                            int idComp = scanner.nextInt();

                            if (gestor.marcarComoCompletada(idComp)) {
                                output.println("Tarea marcada como completada.");
                            } else {
                                output.println("ID no encontrado.");
                            }
                        }
                    }

                    case 4 -> {

                        List<Tarea> tareas = gestor.listarTareas();

                        if (tareas.isEmpty()) {
                            System.out.println("⚠ No hay tareas para editar.");
                        } else {

                            System.out.println("\nTareas registradas:");
                            tareas.forEach(t ->
                                    System.out.println(t.getId() + " | " + t.getNombre() + " | " + t.getPrioridad())
                            );

                            System.out.print("Ingrese ID a editar: ");
                            int idEditar = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Nuevo nombre (dejar vacío para no cambiar): ");
                            String nuevoNombre = scanner.nextLine();

                            System.out.println("Nueva prioridad:");
                            System.out.println("1. URGENTE");
                            System.out.println("2. Media");
                            System.out.println("3. Baja");
                            System.out.println("0. No cambiar");
                            System.out.print("Opción: ");

                            int opcionPrioridad = scanner.nextInt();
                            scanner.nextLine();

                            Prioridad nuevaPrioridad = null;

                            if (opcionPrioridad != 0) {
                                nuevaPrioridad = switch (opcionPrioridad) {
                                    case 1 -> Prioridad.URGENTE;
                                    case 2 -> Prioridad.MEDIA;
                                    case 3 -> Prioridad.BAJA;
                                    default -> null;
                                };
                            }

                            ActualizacionTarea cambios =
                                    new ActualizacionTarea(nuevoNombre, nuevaPrioridad);

                            if (gestor.editarTarea(idEditar, cambios)) {
                                System.out.println("Tarea editada con éxito.");
                            } else {
                                System.out.println("¡¡¡¡ID no encontrado!!!!");
                            }
                        }
                    }

                    case 5 -> {
                        List<Tarea> tareas = gestor.listarTareas();

                        if (tareas.isEmpty()) {
                            output.println("⚠ No hay tareas para eliminar.");
                        } else {
                            output.println("\nTareas registradas:");
                            tareas.forEach(t ->
                                    output.println(t.getId() + " | " + t.getNombre())
                            );

                            output.print("Ingrese ID a eliminar: ");
                            int idElim = scanner.nextInt();

                            if (gestor.eliminarTarea(idElim)) {
                                output.println("Tarea eliminada correctamente.");
                            } else {
                                output.println("¡¡¡¡ID no encontrado!!!!");
                            }
                        }
                    }

                    case 0 -> output.println("Saliendo de SmartTask...");

                    default -> output.println("⚠ Opción inválida.");
                }

            } while (opcion != 0);
        }
    }
}