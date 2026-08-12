package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.ResultadoFutbol;
import com.sportspredictor.shared.Usuario;

public class pronosticoFutbol extends AbstractPronostico{
    private final EventoFutbol evento;
    private final String prediccionGanador;
    private int marcadorLocal;
    private int marcadorVisitante;

    public pronosticoFutbol(EventoFutbol evento, Usuario usuario, String prediccionGanador) {
        super(usuario);
        this.evento = evento;
        this.prediccionGanador = prediccionGanador;
        this.marcadorLocal = 0;
        this.marcadorVisitante = 0;
    }

    @Override
    public void evaluar(String resultado) {
        ResultadoFutbol resultadoFutbol = ResultadoFutbol.parse(resultado);
        if (resultadoFutbol == null) {
            registrarEnRevision();
            return;
        }
        marcadorLocal = resultadoFutbol.getMarcadorLocal();
        marcadorVisitante = resultadoFutbol.getMarcadorVisitante();
        
        String ganadorReal = resultadoFutbol.determinarGanador(evento.getEquipos());

        if (ganadorReal.equalsIgnoreCase(prediccionGanador)) {
            registrarAcierto(10);
        } else {
            registrarFallo();
        }
    }

    @Override
    public int calcularPuntos() {
        return estado == EstadoPronostico.ACERTADO ? 10 : 0;
    }

}
