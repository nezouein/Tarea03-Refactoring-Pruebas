package com.sportspredictor.adapter;

/**
 * Simula una API externa de estadísticas deportivas (Adaptee).
 * En un caso real, aquí iría la llamada HTTP al proveedor.
 */
public class ProveedorDatosExterno {
    private String url;

    public ProveedorDatosExterno(String url) {
        this.url = url;
    }

    public DatosProveedor solicitarDatos(String eventoId) {
        String contenidoSimulado = "{\"posesion\":55,\"tirosAlArco\":8}";
        return new DatosProveedor(eventoId, contenidoSimulado);
    }
}
