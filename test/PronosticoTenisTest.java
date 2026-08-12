
import com.sportspredictor.factory.pronosticoTenis;
import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoTenis;
import com.sportspredictor.shared.Usuario;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PronosticoTenisTest {

    // CP-09
    @Test
    void CP09_resultadoCorrectoConSets() {

        EventoTenis evento = new EventoTenis(
                "evt-009",
                "Partido de tenis",
                LocalDateTime.now().plusDays(1),
                "Jugador1",
                "Jugador2"
        );

        Usuario usuario = new Usuario(
                "usr-009",
                "Juan",
                "juan@email.com",
                "1234"
        );

        pronosticoTenis pronostico =
                new pronosticoTenis(
                        evento,
                        usuario,
                        "Jugador1,3"
                );

        pronostico.evaluar("Jugador1,3");

        assertEquals(
                EstadoPronostico.ACERTADO,
                pronostico.obtenerEstado()
        );

        assertEquals(
                10,
                pronostico.calcularPuntos()
        );

        assertEquals(
                10,
                usuario.getPuntos()
        );
    }

    // CP-10
    @Test
    void CP10_ganadorIncorrecto() {

        EventoTenis evento = new EventoTenis(
                "evt-010",
                "Partido de tenis",
                LocalDateTime.now().plusDays(1),
                "Jugador1",
                "Jugador2"
        );

        Usuario usuario = new Usuario(
                "usr-010",
                "Pedro",
                "pedro@email.com",
                "1234"
        );

        pronosticoTenis pronostico =
                new pronosticoTenis(
                        evento,
                        usuario,
                        "Jugador1,3"
                );

        pronostico.evaluar("Jugador2,3");

        assertEquals(
                EstadoPronostico.FALLIDO,
                pronostico.obtenerEstado()
        );

        assertEquals(
                0,
                pronostico.calcularPuntos()
        );
    }

    // CP-11
    @Test
    void CP11_entradaIncompleta() {

        EventoTenis evento = new EventoTenis(
                "evt-011",
                "Partido de tenis",
                LocalDateTime.now().plusDays(1),
                "Jugador1",
                "Jugador2"
        );

        Usuario usuario = new Usuario(
                "usr-011",
                "Carlos",
                "carlos@email.com",
                "1234"
        );

        pronosticoTenis pronostico =
                new pronosticoTenis(
                        evento,
                        usuario,
                        "Jugador1,3"
                );

        pronostico.evaluar("Jugador1");

        assertEquals(
                EstadoPronostico.EN_REVISION,
                pronostico.obtenerEstado()
        );

        assertEquals(
                0,
                pronostico.calcularPuntos()
        );
    }
}