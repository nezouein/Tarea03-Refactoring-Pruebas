package com.sportspredictor;

import com.sportspredictor.Observer.EmailObserver;
import com.sportspredictor.Observer.GestorNotificaciones;
import com.sportspredictor.Observer.MensajeriaObserver;
import com.sportspredictor.adapter.ProveedorDatosExterno;
import com.sportspredictor.adapter.ProveedorEstadisticasAdapter;
import com.sportspredictor.chain.ManejadorControlCalidad;
import com.sportspredictor.chain.ManejadorSoporte;
import com.sportspredictor.factory.creadorPronostico;
import com.sportspredictor.factory.CreadorPronosticoBaloncesto;
import com.sportspredictor.factory.creadorPronosticoFutbol;
import com.sportspredictor.factory.creadorPronosticoTenis;
import com.sportspredictor.shared.Equipos;
import com.sportspredictor.shared.Evento;
import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.EventoTenis;
import com.sportspredictor.shared.ManejadorIncidente;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ReporteIncidencia;
import com.sportspredictor.shared.Usuario;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // --- Adapter ---
        ProveedorEstadisticasAdapter servicioEstadisticas =
                new ProveedorEstadisticasAdapter(new ProveedorDatosExterno());

        // --- Factory Method: registro de creadores por tipo de evento ---
        Map<Class<? extends Evento>, creadorPronostico> creadores = new HashMap<>();
        creadores.put(EventoFutbol.class, new creadorPronosticoFutbol());
        creadores.put(EventoBaloncesto.class, new CreadorPronosticoBaloncesto());
        creadores.put(EventoTenis.class, new creadorPronosticoTenis());

        // --- Observer ---
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        gestorNotificaciones.suscribir(new EmailObserver("usuario@correo.com"));
        gestorNotificaciones.suscribir(new MensajeriaObserver("+593999999999"));

        // --- Chain of Responsibility ---
        ManejadorSoporte soporte = new ManejadorSoporte();
        ManejadorControlCalidad controlCalidad = new ManejadorControlCalidad();
        soporte.establecerSiguiente(controlCalidad);
        ManejadorIncidente manejadorIncidente = soporte;

        // --- Integracion ---
        SistemaSportsPredictor sistema = new SistemaSportsPredictor(
                servicioEstadisticas, creadores, gestorNotificaciones, manejadorIncidente);

        System.out.println("== Estadisticas ==");
        System.out.println(sistema.consultarEstadisticas("evt-001"));

        System.out.println("\n== Pronostico de futbol ==");
        EventoFutbol eventoFutbol = new EventoFutbol(
                "evt-001",
                "Final de futbol",
                LocalDateTime.now().plusDays(1),
                new Equipos("EquipoA", "EquipoB"));
        Usuario usuario = new Usuario("usr-001", "Juan Perez", "juan.perez@mail.com", "1234");
        Pronostico pronosticoFutbol = sistema.realizarPronostico(eventoFutbol, usuario, "EquipoA");
        sistema.publicarResultado(pronosticoFutbol, "EquipoA");

        System.out.println("\n== Pronostico de baloncesto (mismo sistema, otro deporte) ==");
        EventoBaloncesto eventoBaloncesto = new EventoBaloncesto(
                "evt-002",
                "Final de baloncesto",
                LocalDateTime.now().plusDays(1),
                new Equipos("EquipoC", "EquipoD"));
        Pronostico pronosticoBaloncesto = sistema.realizarPronostico(eventoBaloncesto, usuario, "EquipoC");
        sistema.publicarResultado(pronosticoBaloncesto, "EquipoC");

        System.out.println("\n== Incidente simple ==");
        sistema.registrarReporte(new ReporteIncidencia("inc-001", "Puntos no se sumaron", "captura.png", 2));

        System.out.println("\n== Incidente grave ==");
        sistema.registrarReporte(new ReporteIncidencia("inc-002", "Resultado incorrecto en el marcador", "video.mp4", 8));
    }
}
