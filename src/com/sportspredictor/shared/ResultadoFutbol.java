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
        try{
            int local = Integer.parseInt(partes[0].trim());
            int visitante = Integer.parseInt(partes[1].trim());
            return new ResultadoFutbol(local, visitante);
        } catch (NumberFormatException e){
            return null;
        }
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
