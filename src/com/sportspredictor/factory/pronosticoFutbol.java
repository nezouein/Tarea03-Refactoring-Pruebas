package com.sportspredictor.factory;

import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.ResultadoFutbol;
import com.sportspredictor.shared.Usuario;

public class pronosticoFutbol extends AbstractPronostico{

    // Refactor "Replace Magic Number with Symbolic Constant":
    // antes el 10 aparecía sin nombre dentro de evaluar().
    private static final int PUNTOS_ACIERTO = 10;

    private final EventoFutbol evento;
    private final String prediccionGanador;

    public pronosticoFutbol(EventoFutbol evento, Usuario usuario, String prediccionGanador) {
        super(usuario);
        this.evento = evento;
        this.prediccionGanador = prediccionGanador;
    }

    @Override
    public void evaluar(String resultado) {
        ResultadoFutbol resultadoFutbol = ResultadoFutbol.parse(resultado);
        if (resultadoFutbol == null) {
            registrarEnRevision();
            return;
        }

        String ganadorReal = resultadoFutbol.determinarGanador(evento.getEquipos());

        if (ganadorReal.equalsIgnoreCase(prediccionGanador)) {
            registrarAcierto(PUNTOS_ACIERTO);
        } else {
            registrarFallo();
        }
    }

    // calcularPuntos() ya no se sobreescribe: la implementación de
    // AbstractPronostico (basada en el puntaje pasado a registrarAcierto)
    // es ahora la única fuente de verdad para las tres implementaciones
    // de Pronostico (refactor "Unify Interfaces").
}
