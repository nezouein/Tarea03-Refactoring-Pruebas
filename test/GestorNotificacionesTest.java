package test;

import com.sportspredictor.Observer.EmailObserver;
import com.sportspredictor.Observer.GestorNotificaciones;
import com.sportspredictor.Observer.MensajeriaObserver;
import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.ObservadorNotificacion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorNotificacionesTest {

    @Test
    void cp17_suscribeYNotificaATodosLosObservadores() {
        GestorNotificaciones gestor = new GestorNotificaciones();
        List<Notificacion> recibidasEmail = new ArrayList<>();
        List<Notificacion> recibidasMensajeria = new ArrayList<>();

        ObservadorNotificacion email = notificacion -> recibidasEmail.add(notificacion);
        ObservadorNotificacion mensajeria = notificacion -> recibidasMensajeria.add(notificacion);

        gestor.suscribir(email);
        gestor.suscribir(mensajeria);

        Notificacion notificacion = new Notificacion("Partido importante", "Se ha publicado una nueva actualización");
        gestor.notificar(notificacion);

        assertEquals(1, recibidasEmail.size());
        assertEquals(1, recibidasMensajeria.size());
        assertEquals("Partido importante", recibidasEmail.get(0).getTitulo());
        assertTrue(recibidasMensajeria.contains(notificacion));
    }

    @Test
    void cp18_desuscribeYNoNotificaAlObservadorRemovido() {
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

        assertTrue(recibidasEmail.isEmpty());
        assertFalse(recibidasEmail.contains(notificacion));
    }
}
