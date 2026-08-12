package com.sportspredictor.Observer;

import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.ObservadorNotificacion;

import java.util.ArrayList;
import java.util.List;
public class GestorNotificaciones {
    private List<ObservadorNotificacion> observadores = new ArrayList<>();

    public void suscribir(ObservadorNotificacion observador) {
        observadores.add(observador);
    }

    public void desuscribir(ObservadorNotificacion observador) {
        observadores.remove(observador);
    }

    public void notificar(Notificacion notificacion) {
        for (ObservadorNotificacion observador : observadores) {
            observador.actualizar(notificacion);
        }
    }
}
