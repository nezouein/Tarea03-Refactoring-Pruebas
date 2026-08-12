package com.sportspredictor.chain;

import com.sportspredictor.shared.EstadoReporte;
import com.sportspredictor.shared.ReporteIncidencia;

public class ManejadorSoporte extends ManejadorBase{
    private static final int gravedadMaximaAtender = 2;

    private boolean puedeResolver(ReporteIncidencia reporte){
        int gravedadReporte = reporte.getGravedad();
        if(gravedadReporte > gravedadMaximaAtender){
            return false;
        }else{
            return true;
        }

    }
    private void resolver(ReporteIncidencia reporte){
        reporte.actualizarEstado(EstadoReporte.RESUELTO);
        System.out.println("Reporte: "+ reporte.getId()+ "ha sido resuelto por el equipo de soporte");
    }
    @Override
    public void manejar(ReporteIncidencia reporte){
        System.out.println("Analizando el reporte "+ reporte.getId()+" con gravedad: "+reporte.getGravedad());
        if(puedeResolver(reporte)){
            resolver(reporte);
        }else{
            System.out.println("No se puede resolver el reporte "+ reporte.getId()+ " se escalara al equipo de control de calidad");
            super.manejar(reporte);
        }

    }

}
