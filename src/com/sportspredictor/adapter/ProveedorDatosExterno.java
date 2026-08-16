package com.sportspredictor.adapter;

public class ProveedorDatosExterno {

    public DatosProveedor solicitarDatos(String eventoId) {
        String contenidoSimulado = "{\"posesion\":55,\"tirosAlArco\":8}";
        return new DatosProveedor(eventoId, contenidoSimulado);
    }
}
