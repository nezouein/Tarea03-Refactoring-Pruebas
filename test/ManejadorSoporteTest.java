import com.sportspredictor.chain.ManejadorControlCalidad;
import com.sportspredictor.chain.ManejadorSoporte;
import com.sportspredictor.shared.EstadoReporte;
import com.sportspredictor.shared.ReporteIncidencia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManejadorSoporteTest {

    // CP-33
    @Test
    void CP33_resuelveDirectamenteEnElUmbral() {

        ManejadorSoporte soporte = new ManejadorSoporte();

        ReporteIncidencia reporte = new ReporteIncidencia(
                "rep-033",
                "Puntos no se sumaron",
                "captura.png",
                2
        );

        assertDoesNotThrow(() -> soporte.manejar(reporte));

        assertEquals(
                EstadoReporte.RESUELTO,
                reporte.getEstado()
        );
    }

    // CP-34
    @Test
    void CP34_escalaAControlCalidadSobreElUmbral() {

        ManejadorSoporte soporte = new ManejadorSoporte();
        ManejadorControlCalidad controlCalidad = new ManejadorControlCalidad();
        soporte.establecerSiguiente(controlCalidad);

        ReporteIncidencia reporte = new ReporteIncidencia(
                "rep-034",
                "Resultado incorrecto en el marcador",
                "video.mp4",
                3
        );

        assertDoesNotThrow(() -> soporte.manejar(reporte));

        assertEquals(
                EstadoReporte.ESCALADO,
                reporte.getEstado()
        );
    }

    // CP-35
    @Test
    void CP35_sinSiguienteManejadorNoLanzaExcepcionNiCambiaEstado() {

        ManejadorSoporte soporte = new ManejadorSoporte();

        ReporteIncidencia reporte = new ReporteIncidencia(
                "rep-035",
                "Incidencia grave sin equipo de control de calidad asignado",
                "log.txt",
                5
        );

        assertDoesNotThrow(() -> soporte.manejar(reporte));

        // No hay manejador siguiente configurado: la cadena queda "sin resolver"
        // y el reporte conserva su estado inicial.
        assertEquals(
                EstadoReporte.REGISTRADO,
                reporte.getEstado()
        );
    }
}
