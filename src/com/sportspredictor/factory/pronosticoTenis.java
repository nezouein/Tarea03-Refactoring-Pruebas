package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.EventoTenis;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class pronosticoTenis implements Pronostico {
    private final EventoTenis evento;
    private final Usuario usuario;
    private final String ganadorPronosticado;
    private final int setsPronosticados;
    private int setsResultado;
    private EstadoPronostico estado;

    public pronosticoTenis(EventoTenis evento, Usuario usuario, String datos) {
        this.evento = evento;
        this.usuario = usuario;

        if (datos != null && datos.contains(",")) {
            String[] partes = datos.split(",");
            this.ganadorPronosticado = partes[0].trim();
            this.setsPronosticados = partes.length > 1 ? parseSets(partes[1].trim()) : 0;
        } else {
            this.ganadorPronosticado = datos != null ? datos.trim() : "";
            this.setsPronosticados = 0;
        }

        this.setsResultado = 0;
        this.estado = EstadoPronostico.PENDIENTE;
    }

    private int parseSets(String texto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void evaluar(String resultado) {
        if (resultado == null || !resultado.contains(",")) {
            estado = EstadoPronostico.EN_REVISION;
            return;
        }

        String[] partes = resultado.trim().split(",");
        String ganadorReal = partes[0].trim();
        setsResultado = partes.length > 1 ? parseSets(partes[1].trim()) : 0;

        if (ganadorReal.equalsIgnoreCase(ganadorPronosticado)) {
            estado = EstadoPronostico.ACERTADO;
            usuario.agregarPuntos(5 + (setsResultado == setsPronosticados ? 5 : 0));
        } else {
            estado = EstadoPronostico.FALLIDO;
        }
    }

    @Override
    public int calcularPuntos() {
        if (estado == EstadoPronostico.ACERTADO) {
            return 5 + (setsResultado == setsPronosticados ? 5 : 0);
        }
        return 0;
    }

    @Override
    public EstadoPronostico obtenerEstado() {
        return estado;
    }
}
