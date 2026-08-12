package com.sportspredictor.adapter;

import com.sportspredictor.shared.Estadistica;
import com.sportspredictor.shared.ServicioEstadisticas;
import com.sportspredictor.shared.Tendencia;

import java.util.ArrayList;
import java.util.List;

public class ProveedorEstadisticasAdapter implements ServicioEstadisticas {
    private ProveedorDatosExterno proveedor;

    public ProveedorEstadisticasAdapter(ProveedorDatosExterno proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public List<Estadistica> obtenerEstadisticas(String eventoId) {
        DatosProveedor datos = proveedor.solicitarDatos(eventoId);
        List<Estadistica> resultado = new ArrayList<>();
        resultado.add(convertirDatos(datos));
        return resultado;
    }

    @Override
    public List<Estadistica> obtenerHistorial(String eventoId) {
        return obtenerEstadisticas(eventoId);
    }

    @Override
    public List<Tendencia> obtenerTendencias(String eventoId) {
        List<Tendencia> tendencias = new ArrayList<>();
        tendencias.add(new Tendencia("Racha de victorias", 60.0));
        return tendencias;
    }

    private Estadistica convertirDatos(DatosProveedor datos) {
        // Traduce el formato crudo del proveedor externo al formato interno
        return new Estadistica(datos.getCodigoEvento(), "resumen", datos.getContenido(), 1.0);
    }
}
