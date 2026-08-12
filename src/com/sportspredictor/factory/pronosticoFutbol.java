package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class pronosticoFutbol implements Pronostico {
    private final EventoFutbol evento;
    private final Usuario usuario;
    private final String prediccionGanador;
    private int marcadorLocal;
    private int marcadorVisitante;
    private EstadoPronostico estado;

    public pronosticoFutbol(EventoFutbol evento, Usuario usuario, String prediccionGanador) {
        this.evento = evento;
        this.usuario = usuario;
        this.prediccionGanador = prediccionGanador;
        this.marcadorLocal = 0;
        this.marcadorVisitante = 0;
        this.estado = EstadoPronostico.PENDIENTE;
    }

    @Override
    public void evaluar(String resultado) {
        if (resultado == null || !resultado.contains("-")) {
            estado = EstadoPronostico.EN_REVISION;
            return;
        }

        String[] partes = resultado.trim().split("-");
        try {
            marcadorLocal = Integer.parseInt(partes[0].trim());
            marcadorVisitante = Integer.parseInt(partes[1].trim());
        } catch (NumberFormatException e) {
            estado = EstadoPronostico.EN_REVISION;
            return;
        }

        String ganadorReal;
        if (marcadorLocal > marcadorVisitante) {
            ganadorReal = evento.getEquipoLocal();
        } else if (marcadorLocal < marcadorVisitante) {
            ganadorReal = evento.getEquipoVisitante();
        } else {
            ganadorReal = "EMPATE";
        }

        if (ganadorReal.equalsIgnoreCase(prediccionGanador)) {
            estado = EstadoPronostico.ACERTADO;
            usuario.agregarPuntos(10);
        } else {
            estado = EstadoPronostico.FALLIDO;
        }
    }

    @Override
    public int calcularPuntos() {
        return estado == EstadoPronostico.ACERTADO ? 10 : 0;
    }

    @Override
    public EstadoPronostico obtenerEstado() {
        return estado;
    }
}
