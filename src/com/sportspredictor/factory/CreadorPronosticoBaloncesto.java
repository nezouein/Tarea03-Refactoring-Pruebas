package com.sportspredictor.factory;

import com.sportspredictor.shared.Evento;
import com.sportspredictor.shared.EventoBaloncesto;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public class CreadorPronosticoBaloncesto extends creadorPronostico {

    @Override
    public Pronostico crearPronostico(Evento evento, Usuario usuario, Object datos) {
        EventoBaloncesto eventoBaloncesto = convertirDatos(evento, EventoBaloncesto.class);
        validarEventoAbierto(eventoBaloncesto);

        String prediccionGanador = convertirDatos(datos, String.class);
        return new pronosticoBaloncesto(eventoBaloncesto, usuario, prediccionGanador);
    }
}
