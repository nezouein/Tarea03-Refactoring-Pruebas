/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sportspredictor.shared;

/**
 *
 * @author Administrator
 */
public class Equipos {
    private final String nombreLocal;
    private final String nombreVisitante;
    
    public Equipos(String nombreLocal, String nombreVisitante){
        this.nombreLocal = nombreLocal;
        this.nombreVisitante = nombreVisitante;
    }
    public String getNombreLocal(){return nombreLocal;}
    public String getNombreVisitante(){return nombreVisitante;}
    
    @Override
    public String toString(){return nombreLocal + " vs " + nombreVisitante;}
    
}
