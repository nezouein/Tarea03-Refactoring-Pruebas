import com.sportspredictor.factory.pronosticoFutbol;
import com.sportspredictor.shared.Equipos;
import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.Usuario;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PronosticoFutbolTest {

    // CP-04
    @Test
    void CP04_resultadoCorrecto() {

        EventoFutbol evento = new EventoFutbol(
                "evt-004",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                new Equipos("EquipoLocal","EquipoVisitante")
        );

        Usuario usuario = new Usuario(
                "usr-004",
                "Juan",
                "juan@email.com",
                "1234"
        );

        pronosticoFutbol pronostico = new pronosticoFutbol(
                evento,
                usuario,
                "EquipoLocal"
        );

        pronostico.evaluar("3-1");

        assertEquals(
                EstadoPronostico.ACERTADO,
                pronostico.obtenerEstado()
        );

        assertEquals(
                10,
                pronostico.calcularPuntos()
        );

        // Corrección: registrarAcierto() también suma puntos al usuario;
        // el caso original no lo verificaba, a diferencia de CP-07 y CP-09.
        assertEquals(
                10,
                usuario.getPuntos()
        );
    }

    // CP-05
    @Test
    void CP05_formatoResultadoInvalido() {

        EventoFutbol evento = new EventoFutbol(
                "evt-005",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                new Equipos("EquipoLocal","EquipoVisitante")
        );

        Usuario usuario = new Usuario(
                "usr-005",
                "Pedro",
                "pedro@email.com",
                "1234"
        );

        pronosticoFutbol pronostico = new pronosticoFutbol(
                evento,
                usuario,
                "EquipoLocal"
        );

        pronostico.evaluar("3x1");

        assertEquals(
                EstadoPronostico.EN_REVISION,
                pronostico.obtenerEstado()
        );

        assertEquals(
                0,
                pronostico.calcularPuntos()
        );
    }

    // CP-06
    @Test
    void CP06_resultadoIncorrecto() {

        EventoFutbol evento = new EventoFutbol(
                "evt-006",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                new Equipos("EquipoLocal","EquipoVisitante")
        );

        Usuario usuario = new Usuario(
                "usr-006",
                "Carlos",
                "carlos@email.com",
                "1234"
        );

        pronosticoFutbol pronostico = new pronosticoFutbol(
                evento,
                usuario,
                "EquipoLocal"
        );

        pronostico.evaluar("1-2");

        assertEquals(
                EstadoPronostico.FALLIDO,
                pronostico.obtenerEstado()
        );

        assertEquals(
                0,
                pronostico.calcularPuntos()
        );
    }

    // CP-32 (nuevo)
    @Test
    void CP32_resultadoEmpate() {

        EventoFutbol evento = new EventoFutbol(
                "evt-032",
                "Partido de fútbol",
                LocalDateTime.now().plusDays(1),
                new Equipos("EquipoLocal","EquipoVisitante")
        );

        Usuario usuario = new Usuario(
                "usr-032",
                "Diana",
                "diana@email.com",
                "1234"
        );

        pronosticoFutbol pronostico = new pronosticoFutbol(
                evento,
                usuario,
                "EquipoLocal"
        );

        // determinarGanador() devuelve "Empate" cuando el marcador es igual,
        // lo cual no coincide con ninguna predicción posible.
        pronostico.evaluar("2-2");

        assertEquals(
                EstadoPronostico.FALLIDO,
                pronostico.obtenerEstado()
        );

        assertEquals(
                0,
                pronostico.calcularPuntos()
        );

        assertEquals(
                0,
                usuario.getPuntos()
        );
    }
}