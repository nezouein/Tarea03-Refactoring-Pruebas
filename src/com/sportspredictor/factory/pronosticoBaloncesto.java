package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class pronosticoBaloncesto implements Pronostico {
    private final EventoBaloncesto evento;
    private final Usuario usuario;
    private final String prediccionGanador;
    private int puntuacionTotal;
    private EstadoPronostico estado;

    public pronosticoBaloncesto(EventoBaloncesto evento, Usuario usuario, String prediccionGanador) {
        this.evento = evento;
        this.usuario = usuario;
        this.prediccionGanador = prediccionGanador;
        this.puntuacionTotal = 0;
        this.estado = EstadoPronostico.PENDIENTE;
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

    @Override
    public EstadoPronostico obtenerEstado() {
        return estado;
    }
}
