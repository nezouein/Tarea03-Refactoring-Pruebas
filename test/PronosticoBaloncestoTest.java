
import com.sportspredictor.factory.pronosticoBaloncesto;
import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.Usuario;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PronosticoBaloncestoTest {

    // CP-07
    @Test
    void CP07_resultadoCorrecto() {

        EventoBaloncesto evento = new EventoBaloncesto(
                "evt-007",
                "Partido de baloncesto",
                LocalDateTime.now().plusDays(1),
                "EquipoA",
                "EquipoB"
        );

        Usuario usuario = new Usuario(
                "usr-007",
                "Juan",
                "juan@email.com",
                "1234"
        );

        pronosticoBaloncesto pronostico =
                new pronosticoBaloncesto(
                        evento,
                        usuario,
                        "EquipoA"
                );

        pronostico.evaluar("EquipoA");

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

    // CP-08
    @Test
    void CP08_resultadoNulo() {

        EventoBaloncesto evento = new EventoBaloncesto(
                "evt-008",
                "Partido de baloncesto",
                LocalDateTime.now().plusDays(1),
                "EquipoA",
                "EquipoB"
        );

        Usuario usuario = new Usuario(
                "usr-008",
                "Pedro",
                "pedro@email.com",
                "1234"
        );

        pronosticoBaloncesto pronostico =
                new pronosticoBaloncesto(
                        evento,
                        usuario,
                        "EquipoA"
                );

        pronostico.evaluar(null);

        assertEquals(
                EstadoPronostico.FALLIDO,
                pronostico.obtenerEstado()
        );

        assertEquals(
                0,
                pronostico.calcularPuntos()
        );
    }
}