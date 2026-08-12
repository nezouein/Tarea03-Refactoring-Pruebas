package com.sportspredictor.factory;

import com.sportspredictor.shared.Evento;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

public abstract class creadorPronostico {
    public abstract Pronostico crearPronostico(Evento evento, Usuario usuario, Object datos);

    protected <T> T convertirDatos(Object datos, Class<T> tipoEsperado) {
        if (!tipoEsperado.isInstance(datos)) {
            throw new IllegalArgumentException("Se esperaban datos de tipo " + tipoEsperado.getSimpleName());
        }
        return tipoEsperado.cast(datos);
    }

    protected void validarEventoAbierto(Evento evento) {
        if (!evento.estaAbierto()) {
            throw new IllegalStateException("No se puede crear el pronóstico porque el evento ya inició");
        }
    }
}
