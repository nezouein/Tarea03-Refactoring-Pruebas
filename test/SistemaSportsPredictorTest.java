package test;

import com.sportspredictor.Observer.GestorNotificaciones;
import com.sportspredictor.SistemaSportsPredictor;
import com.sportspredictor.factory.creadorPronosticoFutbol;
import com.sportspredictor.shared.Estadistica;
import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.ManejadorIncidente;
import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ReporteIncidencia;
import com.sportspredictor.shared.ServicioEstadisticas;
import com.sportspredictor.shared.Tendencia;
import com.sportspredictor.shared.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SistemaSportsPredictorTest {

    @Test
    void cp21_consultarEstadisticas_delegaEnElServicio() {
        ServicioEstadisticasStub servicio = new ServicioEstadisticasStub();
        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                servicio,
                new creadorPronosticoFutbol(),
                new GestorNotificaciones(),
                manejadorVacio()
        );

        List<Estadistica> estadisticas = sistema.consultarEstadisticas("evt-001");

        assertIterableEquals(servicio.estadisticasEsperadas, estadisticas);
        assertEquals("evt-001", estadisticas.get(0).getId());
    }

    @Test
    void cp22_realizarPronostico_creaPronosticoPendiente() {
        EventoFutbol evento = new EventoFutbol("evt-001", "Real Madrid vs Barcelona",
                LocalDateTime.now().plusDays(1), "Real Madrid", "Barcelona");
        Usuario usuario = new Usuario("u-001", "Ana", "ana@example.com", "1234");
        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                new ServicioEstadisticasStub(),
                new creadorPronosticoFutbol(),
                new GestorNotificaciones(),
                manejadorVacio()
        );

        Pronostico pronostico = sistema.realizarPronostico(evento, usuario, "Real Madrid");

        assertNotNull(pronostico);
        assertEquals(EstadoPronostico.PENDIENTE, pronostico.obtenerEstado());
        assertTrue(pronostico instanceof com.sportspredictor.factory.pronosticoFutbol);
    }

    @Test
    void cp23_publicarResultado_evaluaPronosticoYNotifica() {
        EventoFutbol evento = new EventoFutbol("evt-001", "Real Madrid vs Barcelona",
                LocalDateTime.now().plusDays(1), "Real Madrid", "Barcelona");
        Usuario usuario = new Usuario("u-001", "Ana", "ana@example.com", "1234");
        GestorNotificaciones gestor = new GestorNotificaciones();
        List<Notificacion> notificaciones = new ArrayList<>();
        gestor.suscribir(notificacion -> notificaciones.add(notificacion));

        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                new ServicioEstadisticasStub(),
                new creadorPronosticoFutbol(),
                gestor,
                manejadorVacio()
        );

        Pronostico pronostico = sistema.realizarPronostico(evento, usuario, "Real Madrid");
        sistema.publicarResultado(pronostico, "2-1");

        assertSame(EstadoPronostico.ACERTADO, pronostico.obtenerEstado());
        assertEquals(10, usuario.getPuntos());
        assertEquals(1, notificaciones.size());
        assertEquals("Resultado disponible", notificaciones.get(0).getTitulo());
    }

    private static ManejadorIncidente manejadorVacio() {
        return new ManejadorIncidente() {
            @Override
            public ManejadorIncidente establecerSiguiente(ManejadorIncidente manejador) {
                return manejador;
            }

            @Override
            public void manejar(ReporteIncidencia reporte) {
                // no se hace nada en la prueba
            }
        };
    }

    private static class ServicioEstadisticasStub implements ServicioEstadisticas {
        private final List<Estadistica> estadisticasEsperadas = List.of(
                new Estadistica("evt-001", "resumen", "Datos del evento", 1.0)
        );

        @Override
        public List<Estadistica> obtenerEstadisticas(String eventoId) {
            return estadisticasEsperadas;
        }

        @Override
        public List<Estadistica> obtenerHistorial(String eventoId) {
            return estadisticasEsperadas;
        }

        @Override
        public List<Tendencia> obtenerTendencias(String eventoId) {
            return List.of(new Tendencia("Racha de victorias", 60.0));
        }
    }
}
