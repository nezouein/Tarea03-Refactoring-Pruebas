package com.sportspredictor.factory;

import com.sportspredictor.shared.Evento;
import com.sportspredictor.shared.EventoFutbol;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class creadorPronosticoFutbol extends creadorPronostico{
    @Override
    public Pronostico crearPronostico(Evento evento, Usuario usuario, Object datos) {
        EventoFutbol eventoFutbol = convertirDatos(evento, EventoFutbol.class);
        validarEventoAbierto(eventoFutbol);

        String prediccionGanador = convertirDatos(datos, String.class);
        return new pronosticoFutbol(eventoFutbol, usuario, prediccionGanador);
    }
}
