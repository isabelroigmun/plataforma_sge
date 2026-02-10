import com.example.plataforma_sge.FormProyectosController;
import com.example.plataforma_sge.InicioController;
import com.example.plataforma_sge.ProyectoOB;
import com.example.plataforma_sge.SQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    InicioController controller;

    @BeforeEach
    public void before(){
        controller= new InicioController();
        SQL.listaProyectos = new ArrayList<>();
        SQL.listaProyectos.add(new ProyectoOB(1, "Proyecto 1", null, null, null, "IT", "Activo", null, false, false, 0, 0, ""));
        SQL.listaProyectos.add(new ProyectoOB(2, "Proyecto 2", null, null, null, "I+D", "Inactivo", null, false, false, 0, 0, ""));
        SQL.listaProyectos.add(new ProyectoOB(3, "Proyecto 3", null, null, null, "IT", "Activo", null, false, false, 0, 0, ""));
    }

    @Test
    void testObtenerProyectosActivos() {
        int activos = controller.obtenerProyectosActivos();
        assertEquals(2, activos);
    }

    @Test
    void testObtenerProyectosInactivos() {
        int inactivos = controller.obtenerProyectosInactivos();
        assertEquals(1, inactivos);
    }

    @Test
    void testContarTipo() {
        int it = controller.contarTipo("IT");
        assertEquals(2, it);
    }

    @Test
    void testConvertirEstadoAInt() {
        FormProyectosController controller = new FormProyectosController();

        assertEquals(1, controller.convertirEstado("activo"));
        assertEquals(1, controller.convertirEstado("ACTIVO"));
        assertEquals(0, controller.convertirEstado("inactivo"));
    }

    @Test
    void testComprobarFechaValida() {
        FormProyectosController controller = new FormProyectosController();

        LocalDate creacion = LocalDate.of(2026, 2, 10);
        LocalDate ejecucionValida = LocalDate.of(2026, 2, 12);
        LocalDate ejecucionInvalida = LocalDate.of(2026, 2, 8);

        assertTrue(controller.comprobarFechaValida(ejecucionInvalida, creacion));
        assertFalse(controller.comprobarFechaValida(ejecucionValida, creacion));
    }

}
