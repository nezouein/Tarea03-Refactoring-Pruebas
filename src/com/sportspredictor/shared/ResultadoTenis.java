/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sportspredictor.shared;

/**
 *
 * @author Administrator
 */
public class ResultadoTenis {
    private final String ganador;
    private final int sets;
    
    private ResultadoTenis(String ganador, int sets){
        this.ganador= ganador;
        this.sets = sets;
    }
    
    public static ResultadoTenis parse(String datos){
        if(datos!=null && datos.contains(",")){
            String[] partes = datos.split(",");
            String ganadorParseado = partes[0].trim();
            int setsParseados = partes.length > 1 ? parseSets(partes[1].trim()): 0;
            return new ResultadoTenis(ganadorParseado,setsParseados);
        }
        String ganadorParseado = datos != null ? datos.trim(): "";
        return new ResultadoTenis(ganadorParseado, 0);
    }
    private static int parseSets(String texto){
        try{
            return Integer.parseInt(texto);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public String getGanador(){return ganador;}
    public int getSets(){return sets;}
    
}
