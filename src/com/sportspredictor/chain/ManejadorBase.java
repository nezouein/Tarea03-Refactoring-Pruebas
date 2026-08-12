package com.sportspredictor.chain;

import com.sportspredictor.shared.EstadoReporte;
import com.sportspredictor.shared.ManejadorIncidente;
import com.sportspredictor.shared.ReporteIncidencia;

public abstract class ManejadorBase implements ManejadorIncidente{
    protected ManejadorIncidente siguiente;

    @Override
    public ManejadorIncidente establecerSiguiente(ManejadorIncidente siguiente){
            this.siguiente = siguiente; 
            return siguiente;       
    }

    @Override 
    public void manejar(ReporteIncidencia reporte ){
        if(siguiente != null){
            reporte.actualizarEstado(EstadoReporte.ESCALADO);
            siguiente.manejar(reporte);
        }else{
            System.out.println("Reporte: "+ reporte.getId()+ "sin resolver ya que no hay mas equipos para escalar");
        }
    }
}
