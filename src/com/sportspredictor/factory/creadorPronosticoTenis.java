package com.sportspredictor.factory;

import com.sportspredictor.shared.Evento;
import com.sportspredictor.shared.EventoTenis;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class creadorPronosticoTenis extends creadorPronostico {
    @Override
    public Pronostico crearPronostico(Evento evento, Usuario usuario, Object datos) {
        EventoTenis eventoTenis = convertirDatos(evento, EventoTenis.class);
        validarEventoAbierto(eventoTenis);

        String prediccionGanador = convertirDatos(datos, String.class);
        return new pronosticoTenis(eventoTenis, usuario, prediccionGanador);
    }
}
