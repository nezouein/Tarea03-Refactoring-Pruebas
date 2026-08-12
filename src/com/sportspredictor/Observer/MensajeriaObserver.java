package com.sportspredictor.Observer;

import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.ObservadorNotificacion;

public class MensajeriaObserver implements ObservadorNotificacion {
    private String numeroDestino;

    public MensajeriaObserver(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    @Override
    public void actualizar(Notificacion notificacion) {
        enviarMensaje(notificacion);
    }

    private void enviarMensaje(Notificacion notificacion) {
        System.out.println("[Mensajería -> " + numeroDestino + "] " + notificacion);
    }
}
