package com.sportspredictor;

import com.sportspredictor.Observer.EmailObserver;
import com.sportspredictor.Observer.GestorNotificaciones;
import com.sportspredictor.Observer.MensajeriaObserver;
import com.sportspredictor.adapter.ProveedorDatosExterno;
import com.sportspredictor.adapter.ProveedorEstadisticasAdapter;
import com.sportspredictor.chain.ManejadorControlCalidad;
import com.sportspredictor.chain.ManejadorSoporte;
import com.sportspredictor.factory.creadorPronosticoFutbol;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.ManejadorIncidente;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ReporteIncidencia;
import com.sportspredictor.shared.Usuario;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // --- Adapter ---
        ProveedorEstadisticasAdapter servicioEstadisticas =
                new ProveedorEstadisticasAdapter(new ProveedorDatosExterno("https://api.futbol.com"));

        // --- Factory Method ---
        creadorPronosticoFutbol creadorPronostico = new creadorPronosticoFutbol();

        // --- Observer ---
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        gestorNotificaciones.suscribir(new EmailObserver("usuario@correo.com"));
        gestorNotificaciones.suscribir(new MensajeriaObserver("+593999999999"));

        // --- Chain of Responsibility ---
        ManejadorSoporte soporte = new ManejadorSoporte();
        ManejadorControlCalidad controlCalidad = new ManejadorControlCalidad();
        soporte.establecerSiguiente(controlCalidad);
        ManejadorIncidente manejadorIncidente = soporte;

        // --- Integración ---
        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                servicioEstadisticas, creadorPronostico, gestorNotificaciones, manejadorIncidente);

        System.out.println("== Estadísticas ==");
        System.out.println(sistema.consultarEstadisticas("evt-001"));

        System.out.println("\n== Pronóstico ==");
        EventoFutbol evento = new EventoFutbol(
                "evt-001",
                "Final de fútbol",
                LocalDateTime.now().plusDays(1),
                "EquipoA",
                "EquipoB");
        Usuario usuario = new Usuario("usr-001", "Juan Perez", "juan.perez@mail.com", "1234");
        Pronostico pronostico = sistema.realizarPronostico(evento, usuario, "EquipoA");
        sistema.publicarResultado(pronostico, "EquipoA");

        System.out.println("\n== Incidente simple ==");
        sistema.registrarReporte(new ReporteIncidencia("inc-001", "Puntos no se sumaron", "captura.png", 2));

        System.out.println("\n== Incidente grave ==");
        sistema.registrarReporte(new ReporteIncidencia("inc-002", "Resultado incorrecto en el marcador", "video.mp4", 8));
    }
}
