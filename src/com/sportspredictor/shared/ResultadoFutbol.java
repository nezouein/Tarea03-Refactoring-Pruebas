/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sportspredictor.shared;

/**
 *
 * @author Administrator
 */
public class ResultadoFutbol {
    private final int marcadorLocal;
    private final int marcadorVisitante;
    
    private ResultadoFutbol(int marcadorLocal, int marcadorVisitante){
        this.marcadorLocal=marcadorLocal;
        this.marcadorVisitante= marcadorVisitante;
    }
    
    public static ResultadoFutbol parse(String resultado){
        if(resultado == null || !resultado.contains("-")){
            return null;
        }
        String[] partes = resultado.trim().split("-");
        // Refactor "Extract Class" (Duplicate Code): el parseo seguro de
        // enteros ahora vive en ParseoNumericoUtil, compartido con
        // ResultadoTenis.
        Integer local = ParseoNumericoUtil.parseEnteroSeguro(partes[0]);
        Integer visitante = ParseoNumericoUtil.parseEnteroSeguro(partes[1]);
        if (local == null || visitante == null) {
            return null;
        }
        return new ResultadoFutbol(local, visitante);
    }
    public String determinarGanador(Equipos equipos){
        if(marcadorLocal>marcadorVisitante){
            return equipos.getNombreLocal();
        }else if(marcadorLocal< marcadorVisitante){
            return equipos.getNombreVisitante();
        }
        return "Empate";
    }
    public int getMarcadorLocal(){return marcadorLocal;}
    public int getMarcadorVisitante(){return marcadorVisitante;}
}
