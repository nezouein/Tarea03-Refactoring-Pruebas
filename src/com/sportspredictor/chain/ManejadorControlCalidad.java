package com.sportspredictor.chain;

import com.sportspredictor.shared.ReporteIncidencia;

public class ManejadorControlCalidad extends ManejadorBase{
       
    private void realizarRevision(ReporteIncidencia reporte){
        System.out.println("Revision del reporte: "+ reporte.getId() + "| La descripcion es: "+ reporte.getDescripcion() + "| La evidencia la siguiente: "+ reporte.getEvidencia()+ "revisado por el equipo de control de calidad");

    }
    private void aplicarAjuste(ReporteIncidencia reporte){
        System.out.println("Aplicando ajustes de puntos o recompensas para el reporte "+reporte.getId());
    }
    @Override
    public void manejar(ReporteIncidencia reporte){
        realizarRevision(reporte);
        aplicarAjuste(reporte);
        System.out.println("Reporte "+ reporte.getId()+ " resuelto despues de ser revisado por el equipo de control de calidad");

    }

}
