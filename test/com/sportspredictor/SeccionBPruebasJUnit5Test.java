package test.com.sportspredictor;

import com.sportspredictor.SistemaSportsPredictor;
import com.sportspredictor.Observer.EmailObserver;
import com.sportspredictor.Observer.GestorNotificaciones;
import com.sportspredictor.Observer.MensajeriaObserver;
import com.sportspredictor.adapter.ProveedorDatosExterno;
import com.sportspredictor.adapter.ProveedorEstadisticasAdapter;
import com.sportspredictor.chain.ManejadorControlCalidad;
import com.sportspredictor.factory.creadorPronosticoFutbol;
import com.sportspredictor.shared.Estadistica;
import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EstadoReporte;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.ManejadorIncidente;
import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.ObservadorNotificacion;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ReglaPuntuacion;
import com.sportspredictor.shared.ReporteIncidencia;
import com.sportspredictor.shared.Tendencia;
import com.sportspredictor.shared.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeccionBPruebasJUnit5Test {

    @Test
    void cp17_gestorNotificaciones_suscribeYNotificaATodosLosObservadores() {
        GestorNotificaciones gestor = new GestorNotificaciones();
        List<Notificacion> recibidasEmail = new ArrayList<>();
        List<Notificacion> recibidasMensajeria = new ArrayList<>();

        ObservadorNotificacion email = notificacion -> recibidasEmail.add(notificacion);
        ObservadorNotificacion mensajeria = notificacion -> recibidasMensajeria.add(notificacion);

        gestor.suscribir(email);
        gestor.suscribir(mensajeria);

        Notificacion notificacion = new Notificacion("Partido importante", "Se ha publicado una nueva actualización");
        gestor.notificar(notificacion);

        assertEquals(1, recibidasEmail.size(), "Debe avisar al observador de email");
        assertEquals(1, recibidasMensajeria.size(), "Debe avisar al observador de mensajería");
        assertEquals("Partido importante", recibidasEmail.get(0).getTitulo());
        assertTrue(recibidasMensajeria.contains(notificacion));
    }

    @Test
    void cp18_gestorNotificaciones_desuscribeYNoNotificaAlObservadorRemovido() {
        GestorNotificaciones gestor = new GestorNotificaciones();
        List<Notificacion> recibidasEmail = new ArrayList<>();
        ObservadorNotificacion email = new EmailObserver("usuario@demo.com") {
            @Override
            public void actualizar(Notificacion notificacion) {
                recibidasEmail.add(notificacion);
            }
        };
        ObservadorNotificacion mensajeria = new MensajeriaObserver("+34123456789");

        gestor.suscribir(email);
        gestor.suscribir(mensajeria);
        gestor.desuscribir(email);

        Notificacion notificacion = new Notificacion("Desuscripción", "Este usuario ya no debe recibir avisos");
        assertDoesNotThrow(() -> gestor.notificar(notificacion));

        assertTrue(recibidasEmail.isEmpty(), "El observador desuscrito no debe recibir ninguna notificación");
        assertFalse(recibidasEmail.contains(notificacion));
    }

    @Test
    void cp19_proveedorEstadisticasAdapter_obtenerEstadisticas_adaptaDatosDelProveedorExterno() {
        ProveedorDatosExterno proveedor = new ProveedorDatosExterno("https://api.sportspredictor.local");
        ProveedorEstadisticasAdapter adapter = new ProveedorEstadisticasAdapter(proveedor);

        List<Estadistica> estadisticas = adapter.obtenerEstadisticas("evt-001");

        assertNotNull(estadisticas);
        assertEquals(1, estadisticas.size());
        assertEquals("evt-001", estadisticas.get(0).getId());
        assertEquals("resumen", estadisticas.get(0).getTipo());
        assertTrue(estadisticas.get(0).getDescripcion().contains("posesion"));
    }

    @Test
    void cp20_proveedorEstadisticasAdapter_obtenerTendencias_devuelveListaValida() {
        ProveedorEstadisticasAdapter adapter = new ProveedorEstadisticasAdapter(new ProveedorDatosExterno("https://api.example.com"));

        List<Tendencia> tendencias = adapter.obtenerTendencias("evt-001");

        assertNotNull(tendencias);
        assertEquals(1, tendencias.size());
        assertAll(
                () -> assertEquals("Racha de victorias", tendencias.get(0).getDescripcion()),
                () -> assertEquals(60.0, tendencias.get(0).getPorcentaje()),
                () -> assertTrue(tendencias.get(0).toString().contains("Racha de victorias"))
        );
    }

    @Test
    void cp21_sistemaSportsPredictor_consultarEstadisticas_delegaEnElServicio() {
        ServicioEstadisticasStub servicio = new ServicioEstadisticasStub();
        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                servicio,
                new creadorPronosticoFutbol(),
                new GestorNotificaciones(),
                new ManejadorIncidente() {
                    @Override
                    public ManejadorIncidente establecerSiguiente(ManejadorIncidente manejador) {
                        return manejador;
                    }

                    @Override
                    public void manejar(ReporteIncidencia reporte) {
                        // no se hace nada en la prueba
                    }
                }
        );

        List<Estadistica> estadisticas = sistema.consultarEstadisticas("evt-001");

        assertIterableEquals(servicio.estadisticasEsperadas, estadisticas);
        assertEquals("evt-001", estadisticas.get(0).getId());
    }

    @Test
    void cp22_sistemaSportsPredictor_realizarPronostico_creaPronosticoPendiente() {
        EventoFutbol evento = new EventoFutbol("evt-001", "Real Madrid vs Barcelona",
                LocalDateTime.now().plusDays(1), "Real Madrid", "Barcelona");
        Usuario usuario = new Usuario("u-001", "Ana", "ana@example.com", "1234");
        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                new ServicioEstadisticasStub(),
                new creadorPronosticoFutbol(),
                new GestorNotificaciones(),
                new ManejadorIncidente() {
                    @Override
                    public ManejadorIncidente establecerSiguiente(ManejadorIncidente manejador) {
                        return manejador;
                    }

                    @Override
                    public void manejar(ReporteIncidencia reporte) {
                        // no se hace nada en la prueba
                    }
                }
        );

        Pronostico pronostico = sistema.realizarPronostico(evento, usuario, "Real Madrid");

        assertNotNull(pronostico);
        assertEquals(EstadoPronostico.PENDIENTE, pronostico.obtenerEstado());
        assertTrue(pronostico instanceof com.sportspredictor.factory.pronosticoFutbol);
    }

    @Test
    void cp23_sistemaSportsPredictor_publicarResultado_evaluaPronosticoYNotifica() {
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
                new ManejadorIncidente() {
                    @Override
                    public ManejadorIncidente establecerSiguiente(ManejadorIncidente manejador) {
                        return manejador;
                    }

                    @Override
                    public void manejar(ReporteIncidencia reporte) {
                        // no se hace nada en la prueba
                    }
                }
        );

        Pronostico pronostico = sistema.realizarPronostico(evento, usuario, "Real Madrid");
        sistema.publicarResultado(pronostico, "2-1");

        assertSame(EstadoPronostico.ACERTADO, pronostico.obtenerEstado());
        assertEquals(10, usuario.getPuntos());
        assertEquals(1, notificaciones.size());
        assertEquals("Resultado disponible", notificaciones.get(0).getTitulo());
    }

    @Test
    void cp24_manejadorControlCalidad_manejar_procesaReporte() {
        ManejadorControlCalidad manejador = new ManejadorControlCalidad();
        ReporteIncidencia reporte = new ReporteIncidencia("rep-001", "Error de cálculo", "Pantalla no carga", 2);

        assertDoesNotThrow(() -> manejador.manejar(reporte));
        assertEquals("rep-001", reporte.getId());
        assertNotNull(reporte.toString());
    }

    @Test
    void cp25_reporteIncidencia_actualizarEstado_cambiaEstadoCorrectamente() {
        ReporteIncidencia reporte = new ReporteIncidencia("rep-002", "Fallo de sincronización", "No actualiza", 3);

        reporte.actualizarEstado(EstadoReporte.EN_REVISION);

        assertEquals(EstadoReporte.EN_REVISION, reporte.getEstado());
        assertNotEquals(EstadoReporte.REGISTRADO, reporte.getEstado());
    }

    @Test
    void cp26_reporteIncidencia_cerrar_resuelveElReporte() {
        ReporteIncidencia reporte = new ReporteIncidencia("rep-003", "Error visual", "Botón no responde", 1);

        reporte.cerrar();

        assertEquals(EstadoReporte.RESUELTO, reporte.getEstado());
        assertSame(EstadoReporte.RESUELTO, reporte.getEstado());
    }

    @Test
    void cp27_reglaPuntuacion_validar_trueCuandoPuntosSonPositivos() {
        ReglaPuntuacion regla = new ReglaPuntuacion("r-01", "general", 5);

        assertTrue(regla.validar());
        assertEquals(5, regla.getPuntosPorAcierto());
    }

    @Test
    void cp28_reglaPuntuacion_validar_falseCuandoPuntosSonCero() {
        ReglaPuntuacion regla = new ReglaPuntuacion("r-02", "general", 0);

        assertFalse(regla.validar());
        assertEquals(0, regla.getPuntosPorAcierto());
    }

    @Test
    void cp29_usuarioSistema_iniciarSesion_devuelveTrue() {
        Usuario usuario = new Usuario("u-010", "Luis", "luis@example.com", "pass");

        assertTrue(usuario.iniciarSesion());
        assertEquals("u-010", usuario.getId());
    }

    @Test
    void cp30_usuarioSistema_cerrarSesion_noLanzaExcepcion() {
        Usuario usuario = new Usuario("u-011", "Marta", "marta@example.com", "pass");

        assertDoesNotThrow(usuario::cerrarSesion);
        assertEquals("Marta", usuario.getNombre());
    }

    private static class ServicioEstadisticasStub implements com.sportspredictor.shared.ServicioEstadisticas {
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
