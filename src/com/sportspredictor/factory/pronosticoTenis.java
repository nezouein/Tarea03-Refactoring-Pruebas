package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoTenis;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ResultadoTenis;
import com.sportspredictor.shared.Usuario;

public class pronosticoTenis extends AbstractPronostico {
    private final EventoTenis evento;
    private final String ganadorPronosticado;
    private final int setsPronosticados;
    private int setsResultado;

    public pronosticoTenis(EventoTenis evento, Usuario usuario, String datos) {
        super(usuario);
        this.evento = evento;
        ResultadoTenis prediccion = ResultadoTenis.parse(datos);
        this.ganadorPronosticado = prediccion.getGanador();
        this.setsPronosticados = prediccion.getSets();
        this.setsResultado = 0;
    }

    @Override
    public void evaluar(String resultado) {
        if (resultado == null || !resultado.contains(",")) {
            registrarEnRevision();
            return;
        }

       ResultadoTenis resultadoTenis = ResultadoTenis.parse(resultado);
       String ganadorReal = resultadoTenis.getGanador();
       setsResultado = resultadoTenis.getSets();

        if (ganadorReal.equalsIgnoreCase(ganadorPronosticado)) {
            registrarAcierto(5 + (setsResultado == setsPronosticados ? 5 : 0));
        } else {
            registrarFallo();
        }
    }

    @Override
    public int calcularPuntos() {
        if (estado == EstadoPronostico.ACERTADO) {
            return 5 + (setsResultado == setsPronosticados ? 5 : 0);
        }
        return 0;
    }
}
