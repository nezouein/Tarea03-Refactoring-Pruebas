package test;

import com.sportspredictor.factory.creadorPronosticoFutbol;
import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreadorPronosticoFutbolTest {

    // CP-01
    @Test
    void CP01_crearPronosticoEventoAbierto() {

        EventoFutbol evento = new EventoFutbol(
                "evt-001",
                "Partido de prueba",
                LocalDateTime.now().plusDays(1),
                "EquipoA",
                "EquipoB"
        );

        Usuario usuario = new Usuario(
                "usr-001",
                "Juan",
                "juan@email.com",
                "1234"
        );

        creadorPronosticoFutbol creador =
                new creadorPronosticoFutbol();

        Pronostico resultado = creador.crearPronostico(
                evento,
                usuario,
                "EquipoA"
        );

        assertNotNull(resultado);

        assertEquals(
                EstadoPronostico.PENDIENTE,
                resultado.obtenerEstado()
        );
    }

    // CP-02
    @Test
    void CP02_noCrearPronosticoEventoCerrado() {

        EventoFutbol evento = new EventoFutbol(
                "evt-002",
                "Partido cerrado",
                LocalDateTime.now().plusDays(1),
                "EquipoA",
                "EquipoB"
        );

        evento.cerrarPronosticos();

        Usuario usuario = new Usuario(
                "usr-002",
                "Pedro",
                "pedro@email.com",
                "1234"
        );

        creadorPronosticoFutbol creador =
                new creadorPronosticoFutbol();

        assertThrows(
                IllegalStateException.class,
                () -> creador.crearPronostico(
                        evento,
                        usuario,
                        "EquipoA"
                )
        );
    }

    // CP-03
    @Test
    void CP03_eventoTipoIncorrecto() {

        EventoBaloncesto evento = new EventoBaloncesto(
                "evt-003",
                "Partido de baloncesto",
                LocalDateTime.now().plusDays(1),
                "EquipoA",
                "EquipoB"
        );

        Usuario usuario = new Usuario(
                "usr-003",
                "Carlos",
                "carlos@email.com",
                "1234"
        );

        creadorPronosticoFutbol creador =
                new creadorPronosticoFutbol();

        assertThrows(
                IllegalArgumentException.class,
                () -> creador.crearPronostico(
                        evento,
                        usuario,
                        "EquipoA"
                )
        );
    }
}