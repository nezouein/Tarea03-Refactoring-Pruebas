package com.sportspredictor.factory;

import com.sportspredictor.shared.EventoTenis;
import com.sportspredictor.shared.ResultadoTenis;
import com.sportspredictor.shared.Usuario;

public class pronosticoTenis extends AbstractPronostico {

    // Refactor "Replace Magic Number with Symbolic Constant".
    private static final int PUNTOS_BASE_GANADOR = 5;
    private static final int PUNTOS_BONO_SETS_EXACTOS = 5;

    private final EventoTenis evento;
    private final String ganadorPronosticado;
    private final int setsPronosticados;

    public pronosticoTenis(EventoTenis evento, Usuario usuario, String datos) {
        super(usuario);
        this.evento = evento;
        ResultadoTenis prediccion = ResultadoTenis.parse(datos);
        this.ganadorPronosticado = prediccion.getGanador();
        this.setsPronosticados = prediccion.getSets();
    }

    @Override
    public void evaluar(String resultado) {
        if (resultado == null || !resultado.contains(",")) {
            registrarEnRevision();
            return;
        }

        ResultadoTenis resultadoTenis = ResultadoTenis.parse(resultado);
        String ganadorReal = resultadoTenis.getGanador();
        int setsResultado = resultadoTenis.getSets();

        if (ganadorReal.equalsIgnoreCase(ganadorPronosticado)) {
            boolean setsExactos = setsResultado == setsPronosticados;
            registrarAcierto(PUNTOS_BASE_GANADOR + (setsExactos ? PUNTOS_BONO_SETS_EXACTOS : 0));
        } else {
            registrarFallo();
        }
    }

    // calcularPuntos() ya no se sobreescribe (refactor "Unify Interfaces");
    // setsResultado pasó de campo a variable local, ya que solo se usaba
    // dentro de evaluar() una vez que se retiró el cálculo duplicado.
}
