package test;

import com.sportspredictor.shared.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    // CP-12
    @Test
    void CP12_agregarPuntos() {

        Usuario usuario = new Usuario(
                "usr-012",
                "Juan",
                "juan@email.com",
                "1234"
        );

        usuario.agregarPuntos(15);

        assertEquals(
                15,
                usuario.getPuntos()
        );
    }

    // CP-13
    @Test
    void CP13_toString() {

        Usuario usuario = new Usuario(
                "usr-013",
                "Pedro",
                "pedro@email.com",
                "1234"
        );

        usuario.agregarPuntos(5);

        String resultado = usuario.toString();

        assertTrue(resultado.contains("Pedro"));
        assertTrue(resultado.contains("5"));
    }
}