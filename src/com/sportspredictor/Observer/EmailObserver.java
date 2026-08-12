package com.sportspredictor.Observer;

import com.sportspredictor.shared.Notificacion;
import com.sportspredictor.shared.ObservadorNotificacion;

public class EmailObserver implements ObservadorNotificacion {
    private String correoDestino;

    public EmailObserver(String correoDestino) {
        this.correoDestino = correoDestino;
    }

    @Override
    public void actualizar(Notificacion notificacion) {
        enviarCorreo(notificacion);
    }

    private void enviarCorreo(Notificacion notificacion) {
        System.out.println("[Correo -> " + correoDestino + "] " + notificacion);
    }
}
