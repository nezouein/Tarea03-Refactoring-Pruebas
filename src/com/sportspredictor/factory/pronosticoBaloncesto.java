package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class pronosticoBaloncesto extends AbstractPronostico {
    private final EventoBaloncesto evento;
    private final String prediccionGanador;
    private int puntuacionTotal;

    public pronosticoBaloncesto(EventoBaloncesto evento, Usuario usuario, String prediccionGanador) {
        super(usuario);
        this.evento = evento;
        this.prediccionGanador = prediccionGanador;
        this.puntuacionTotal = 0;
    }

    @Override
    public void evaluar(String resultado) {
        String ganadorReal = resultado != null ? resultado.trim() : "";
        if (ganadorReal.equalsIgnoreCase(prediccionGanador)) {
            estado = EstadoPronostico.ACERTADO;
            puntuacionTotal = 10;
            usuario.agregarPuntos(puntuacionTotal);
        } else {
            estado = EstadoPronostico.FALLIDO;
            puntuacionTotal = 0;
        }
    }

    @Override
    public int calcularPuntos() {
        return puntuacionTotal;
    }
}
