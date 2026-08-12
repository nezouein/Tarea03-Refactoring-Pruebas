package com.sportspredictor.Observer;

import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.ObservadorNotificacion;

public class PushObserver implements ObservadorNotificacion {
    private String tokenDispositivo;

    public PushObserver(String tokenDispositivo) {
        this.tokenDispositivo = tokenDispositivo;
    }

    @Override
    public void actualizar(Notificacion notificacion) {
        enviarPush(notificacion);
    }

    private void enviarPush(Notificacion notificacion) {
        System.out.println("[Push -> " + tokenDispositivo + "] " + notificacion);
    }
}
