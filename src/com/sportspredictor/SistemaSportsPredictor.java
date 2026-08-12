package com.sportspredictor;

import java.util.List;

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
    private creadorPronostico creadorPronostico;
    private GestorNotificaciones gestorNotificaciones;
    private ManejadorIncidente manejadorIncidente;

    public SistemaSportsPredictor(ServicioEstadisticas servicioEstadisticas,
                                   creadorPronostico creadorPronostico,
                                   GestorNotificaciones gestorNotificaciones,
                                   ManejadorIncidente manejadorIncidente) {
        this.servicioEstadisticas = servicioEstadisticas;
        this.creadorPronostico = creadorPronostico;
        this.gestorNotificaciones = gestorNotificaciones;
        this.manejadorIncidente = manejadorIncidente;
    }

    public List<Estadistica> consultarEstadisticas(String eventoId) {
        return servicioEstadisticas.obtenerEstadisticas(eventoId);
    }

    public Pronostico realizarPronostico(Evento evento, Usuario usuario, Object datosPrediccion) {
        Pronostico pronostico = creadorPronostico.crearPronostico(evento, usuario, datosPrediccion);
        System.out.println("Pronóstico creado: " + pronostico.obtenerEstado());
        return pronostico;
    }

    public void publicarResultado(Pronostico pronostico, String resultado) {
        pronostico.evaluar(resultado);
        gestorNotificaciones.notificar(
                new Notificacion("Resultado disponible",
                        "Tu pronóstico quedó en estado: " + pronostico.obtenerEstado()));
    }

    public void registrarReporte(ReporteIncidencia reporte) {
        manejadorIncidente.manejar(reporte);
    }
}
