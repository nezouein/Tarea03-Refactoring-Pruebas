package com.sportspredictor.adapter;

public class DatosProveedor {
    private String codigoEvento;
    private String contenido; // formato propio del proveedor externo (ej. JSON crudo)

    public DatosProveedor(String codigoEvento, String contenido) {
        this.codigoEvento = codigoEvento;
        this.contenido = contenido;
    }

    public String getCodigoEvento() { return codigoEvento; }
    public String getContenido() { return contenido; }
}
