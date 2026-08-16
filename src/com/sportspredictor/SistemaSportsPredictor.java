package com.sportspredictor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sportspredictor.Observer.GestorNotificaciones;
import com.sportspredictor.factory.creadorPronostico;
import com.sportspredictor.shared.Estadistica;
import com.sportspredictor.shared.Evento;
import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ReporteIncidencia;
import com.sportspredictor.shared.ServicioEstadisticas;
import com.sportspredictor.shared.Usuario;
import com.sportspredictor.shared.ManejadorIncidente;

public class SistemaSportsPredictor {
    private ServicioEstadisticas servicioEstadisticas;

    // Refactor "Registro de creadores" para el code smell Shotgun Surgery:
    // antes había un único CreadorPronostico inyectado, por lo que el
    // sistema solo podía atender un deporte a la vez (si le llegaba un
    // Evento de otro tipo, el creador fallaba con
    // IllegalArgumentException). Ahora se despacha dinámicamente según
    // el tipo concreto de Evento, y agregar un deporte nuevo ya no
    // requiere tocar esta clase: solo registrar su creador (en el
    // constructor o con registrarCreador()).
    private final Map<Class<? extends Evento>, creadorPronostico> creadores;

    private GestorNotificaciones gestorNotificaciones;
    private ManejadorIncidente manejadorIncidente;

    public SistemaSportsPredictor(ServicioEstadisticas servicioEstadisticas,
                                   Map<Class<? extends Evento>, creadorPronostico> creadores,
                                   GestorNotificaciones gestorNotificaciones,
                                   ManejadorIncidente manejadorIncidente) {
        this.servicioEstadisticas = servicioEstadisticas;
        this.creadores = new HashMap<>(creadores);
        this.gestorNotificaciones = gestorNotificaciones;
        this.manejadorIncidente = manejadorIncidente;
    }

    /**
     * Registra (o reemplaza) el creador de pronosticos para un tipo de
     * evento. Permite agregar soporte para un deporte nuevo sin volver
     * a construir el sistema ni tocar esta clase.
     */
    public void registrarCreador(Class<? extends Evento> tipoEvento, creadorPronostico creador) {
        creadores.put(tipoEvento, creador);
    }

    public List<Estadistica> consultarEstadisticas(String eventoId) {
        return servicioEstadisticas.obtenerEstadisticas(eventoId);
    }

    public Pronostico realizarPronostico(Evento evento, Usuario usuario, Object datosPrediccion) {
        creadorPronostico creador = creadores.get(evento.getClass());
        if (creador == null) {
            throw new IllegalArgumentException(
                    "No hay un CreadorPronostico registrado para " + evento.getClass().getSimpleName());
        }
        Pronostico pronostico = creador.crearPronostico(evento, usuario, datosPrediccion);
        System.out.println("Pronostico creado: " + pronostico.obtenerEstado());
        return pronostico;
    }

    public void publicarResultado(Pronostico pronostico, String resultado) {
        pronostico.evaluar(resultado);
        gestorNotificaciones.notificar(
                new Notificacion("Resultado disponible",
                        "Tu pronostico quedo en estado: " + pronostico.obtenerEstado()));
    }

    public void registrarReporte(ReporteIncidencia reporte) {
        manejadorIncidente.manejar(reporte);
    }
}
