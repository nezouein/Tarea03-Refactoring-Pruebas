package test;

import com.sportspredictor.chain.ManejadorControlCalidad;
import com.sportspredictor.shared.EstadoReporte;
import com.sportspredictor.shared.ReporteIncidencia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReporteIncidenciaTest {

    @Test
    void cp24_manejadorControlCalidad_manejar_procesaReporte() {
        ManejadorControlCalidad manejador = new ManejadorControlCalidad();
        ReporteIncidencia reporte = new ReporteIncidencia("rep-001", "Error de cálculo", "Pantalla no carga", 2);

        assertDoesNotThrow(() -> manejador.manejar(reporte));
        assertEquals("rep-001", reporte.getId());
        assertNotNull(reporte.toString());
    }

    @Test
    void cp25_actualizarEstado_cambiaEstadoCorrectamente() {
        ReporteIncidencia reporte = new ReporteIncidencia("rep-002", "Fallo de sincronización", "No actualiza", 3);

        reporte.actualizarEstado(EstadoReporte.EN_REVISION);

        assertEquals(EstadoReporte.EN_REVISION, reporte.getEstado());
        assertNotEquals(EstadoReporte.REGISTRADO, reporte.getEstado());
    }

    @Test
    void cp26_cerrar_resuelveElReporte() {
        ReporteIncidencia reporte = new ReporteIncidencia("rep-003", "Error visual", "Botón no responde", 1);

        reporte.cerrar();

        assertEquals(EstadoReporte.RESUELTO, reporte.getEstado());
        assertSame(EstadoReporte.RESUELTO, reporte.getEstado());
    }
}
