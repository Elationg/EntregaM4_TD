package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.smarttask.Main;
import com.smarttask.model.ActualizacionTarea;
import com.smarttask.model.Prioridad;
import com.smarttask.model.Tarea;
import com.smarttask.service.GestorTareas;

public class GestorTareasTest {

    private GestorTareas gestor;

    //Al inicio de cada test se crea un nuevo gestor para asegurar independencia entre pruebas.

    @BeforeEach
    void setUp() {
        gestor = new GestorTareas();
    }

    // =========================
    // AGREGAR
    // =========================

    @Test
    void testAgregarTarea() {
        gestor.agregarTarea("Estudiar Java", Prioridad.MEDIA);

        assertEquals(1, gestor.listarTareas().size());
        assertEquals("Estudiar Java", gestor.listarTareas().get(0).getNombre());
    }

    // =========================
    // LISTAR
    // =========================

    @Test
    void testListarTareasVacia() {
        assertTrue(gestor.listarTareas().isEmpty());
    }

    @Test
    void testListarTareasConDatos() {
        gestor.agregarTarea("Tarea 1", Prioridad.URGENTE);
        gestor.agregarTarea("Tarea 2", Prioridad.MEDIA);

        assertEquals(2, gestor.listarTareas().size());
    }

    // =========================
    // MARCAR COMO COMPLETADA
    // =========================

    @Test
    void testMarcarComoCompletada() {
        gestor.agregarTarea("Estudiar", Prioridad.MEDIA);

        boolean resultado = gestor.marcarComoCompletada(1);

        assertTrue(resultado);
        assertTrue(gestor.listarTareas().get(0).isCompletado());
    }

    @Test
    void testMarcarComoCompletadaSinTareas() {
        boolean resultado = gestor.marcarComoCompletada(1);

        assertFalse(resultado);
    }

    @Test
    void testMarcarComoCompletadaIdInexistente() {
        gestor.agregarTarea("Tarea", Prioridad.MEDIA);

        boolean resultado = gestor.marcarComoCompletada(99);

        assertFalse(resultado);
    }

    // =========================
    // ELIMINAR
    // =========================

    @Test
    void testEliminarTarea() {
        gestor.agregarTarea("Eliminar", Prioridad.BAJA);

        boolean resultado = gestor.eliminarTarea(1);

        assertTrue(resultado);
        assertTrue(gestor.listarTareas().isEmpty());
    }

    @Test
    void testEliminarSinTareas() {
        boolean resultado = gestor.eliminarTarea(1);

        assertFalse(resultado);
    }

    @Test
    void testEliminarIdInexistente() {
        gestor.agregarTarea("Tarea", Prioridad.MEDIA);

        boolean resultado = gestor.eliminarTarea(99);

        assertFalse(resultado);
        assertEquals(1, gestor.listarTareas().size());
    }

    // =========================
    // EDITAR
    // =========================

    @Test
    void testEditarNombre() {

        gestor.agregarTarea("Nombre viejo", Prioridad.MEDIA);

        ActualizacionTarea cambios =
                new ActualizacionTarea("Nombre nuevo", null);

        gestor.editarTarea(1, cambios);

        Tarea tarea = gestor.listarTareas().get(0);

        assertEquals("Nombre nuevo", tarea.getNombre());
        assertEquals(Prioridad.MEDIA, tarea.getPrioridad()); // prioridad no cambia
    }

    @Test
    void testEditarPrioridad() {

        gestor.agregarTarea("Tarea", Prioridad.BAJA);

        ActualizacionTarea cambios =
                new ActualizacionTarea(null, Prioridad.URGENTE);

        gestor.editarTarea(1, cambios);

        Tarea tarea = gestor.listarTareas().get(0);

        assertEquals("Tarea", tarea.getNombre()); // nombre no cambia
        assertEquals(Prioridad.URGENTE, tarea.getPrioridad());
    }

    
    @Test
    void testEditarAmbosCampos() {

        gestor.agregarTarea("Viejo", Prioridad.BAJA);

        ActualizacionTarea cambios =
                new ActualizacionTarea("Nuevo", Prioridad.MEDIA);

        gestor.editarTarea(1, cambios);

        Tarea tarea = gestor.listarTareas().get(0);

        assertEquals("Nuevo", tarea.getNombre());
        assertEquals(Prioridad.MEDIA, tarea.getPrioridad());
    }

    @Test
    void testEditarIdInexistente() {

        ActualizacionTarea cambios =
                new ActualizacionTarea("Nuevo", Prioridad.MEDIA);

        boolean resultado = gestor.editarTarea(99, cambios);

        assertFalse(resultado);
    }

    @Test
    void testEditarNombreEnBlancoNoModifica() {

        gestor.agregarTarea("Original", Prioridad.MEDIA);

        ActualizacionTarea cambios =
                new ActualizacionTarea("   ", null);

        gestor.editarTarea(1, cambios);

        Tarea tarea = gestor.listarTareas().get(0);

        assertEquals("Original", tarea.getNombre());
    }

    //Testea que el id se incremente correctamente y que el flujo completo de agregar, marcar como completada y eliminar funcione como se espera.

    @Test
    void testIdIncremental() {
        gestor.agregarTarea("T1", Prioridad.MEDIA);
        gestor.agregarTarea("T2", Prioridad.MEDIA);

        assertEquals(1, gestor.listarTareas().get(0).getId());
        assertEquals(2, gestor.listarTareas().get(1).getId());
    }

    //Testea que al agregar una tarea con prioridad urgente, se cree una instancia de TareaUrgente.

    @Test
    void testCrearTareaUrgente() {
        gestor.agregarTarea("Importante", Prioridad.URGENTE);

        assertTrue(gestor.listarTareas().get(0)
                .getClass()
                .getSimpleName()
                .equals("TareaUrgente"));
    }

    //Testea un flujo completo de interacción simulando la entrada del usuario y verificando la salida.

    @Test
    void testFlujoCompleto() {

        String inputSimulado = """
                1
                Estudiar
                2
                2
                3
                1
                4
                1
                No Estudiar
                1
                5
                1
                0
                """;

        ByteArrayInputStream in =
                new ByteArrayInputStream(inputSimulado.getBytes());

        ByteArrayOutputStream outContent =
                new ByteArrayOutputStream();

        PrintStream out = new PrintStream(outContent);

        Main.ejecutar(in, out, gestor);

        String salida = outContent.toString();

        assertTrue(salida.contains("Tarea agregada correctamente."));
        assertTrue(salida.contains("Tarea marcada como completada."));
        assertTrue(salida.contains("Tarea eliminada correctamente."));
    }
}
