
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.EstadoEvento;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventoTest {

    // CP-14
    @Test
    void CP14_cerrarPronosticos() {

        EventoFutbol evento = new EventoFutbol(
                "evt-014",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                "EquipoLocal",
                "EquipoVisitante"
        );

        evento.cerrarPronosticos();

        assertFalse(evento.estaAbierto());

        assertEquals(
                EstadoEvento.CERRADO,
                evento.getEstado()
        );
    }

    // CP-15
    @Test
    void CP15_registrarResultado() {

        EventoFutbol evento = new EventoFutbol(
                "evt-015",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                "EquipoLocal",
                "EquipoVisitante"
        );

        evento.registrarResultado("3-1");

        assertEquals(
                EstadoEvento.FINALIZADO,
                evento.getEstado()
        );
    }
}