package test;

import com.sportspredictor.shared.Administrador;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.EstadoEvento;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdministradorTest {

    // CP-16
    @Test
    void CP16_publicarResultado() {

        EventoFutbol evento = new EventoFutbol(
                "evt-016",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                "EquipoLocal",
                "EquipoVisitante"
        );

        Administrador administrador = new Administrador(
                "admin-001",
                "Administrador",
                "admin@email.com",
                "1234"
        );

        administrador.publicarResultado(
                evento,
                "3-1"
        );

        assertEquals(
                EstadoEvento.FINALIZADO,
                evento.getEstado()
        );
    }
}