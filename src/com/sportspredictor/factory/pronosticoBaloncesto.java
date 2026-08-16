package com.sportspredictor.factory;

import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.Usuario;

public class pronosticoBaloncesto extends AbstractPronostico {

    // Refactor "Replace Magic Number with Symbolic Constant".
    private static final int PUNTOS_ACIERTO = 10;

    private final EventoBaloncesto evento;
    private final String prediccionGanador;

    public pronosticoBaloncesto(EventoBaloncesto evento, Usuario usuario, String prediccionGanador) {
        super(usuario);
        this.evento = evento;
        this.prediccionGanador = prediccionGanador;
    }

    @Override
    public void evaluar(String resultado) {
        String ganadorReal = resultado != null ? resultado.trim() : "";
        if (ganadorReal.equalsIgnoreCase(prediccionGanador)) {
            registrarAcierto(PUNTOS_ACIERTO);
        } else {
            registrarFallo();
        }
    }
}
