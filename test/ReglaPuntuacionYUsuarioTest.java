package test;

import com.sportspredictor.shared.ReglaPuntuacion;
import com.sportspredictor.shared.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReglaPuntuacionYUsuarioTest {

    @Test
    void cp27_reglaPuntuacion_validar_trueCuandoPuntosSonPositivos() {
        ReglaPuntuacion regla = new ReglaPuntuacion("r-01", "general", 5);

        assertTrue(regla.validar());
        assertEquals(5, regla.getPuntosPorAcierto());
    }

    @Test
    void cp28_reglaPuntuacion_validar_falseCuandoPuntosSonCero() {
        ReglaPuntuacion regla = new ReglaPuntuacion("r-02", "general", 0);

        assertFalse(regla.validar());
        assertEquals(0, regla.getPuntosPorAcierto());
    }

    @Test
    void cp29_usuarioSistema_iniciarSesion_devuelveTrue() {
        Usuario usuario = new Usuario("u-010", "Luis", "luis@example.com", "pass");

        assertTrue(usuario.iniciarSesion());
        assertEquals("u-010", usuario.getId());
    }

    @Test
    void cp30_usuarioSistema_cerrarSesion_noLanzaExcepcion() {
        Usuario usuario = new Usuario("u-011", "Marta", "marta@example.com", "pass");

        assertDoesNotThrow(usuario::cerrarSesion);
        assertEquals("Marta", usuario.getNombre());
    }
}
