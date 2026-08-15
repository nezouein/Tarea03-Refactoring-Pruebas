/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sportspredictor.factory;

import com.sportspredictor.shared.EstadoPronostico;
import com.sportspredictor.shared.Pronostico;
import com.sportspredictor.shared.Usuario;

/**
 *
 * @author Administrator
 */
public abstract class AbstractPronostico implements Pronostico{
    protected final Usuario usuario;
    protected EstadoPronostico estado;
    private int puntosObtenidos;

    protected AbstractPronostico(Usuario usuario){
        this.usuario = usuario;
        this.estado = EstadoPronostico.PENDIENTE;
        this.puntosObtenidos = 0;
    }
    
    protected void registrarAcierto(int puntos){
        estado = EstadoPronostico.ACERTADO;
        puntosObtenidos = puntos;
        usuario.agregarPuntos(puntos);
    }
    
    protected void registrarFallo(){
        estado = EstadoPronostico.FALLIDO;
        puntosObtenidos = 0;
    }
    
    protected void registrarEnRevision(){
        estado = EstadoPronostico.EN_REVISION;
        puntosObtenidos = 0;
    }
    
    @Override
    public EstadoPronostico obtenerEstado(){
        return estado;
    }

    @Override
    public int calcularPuntos(){
        return puntosObtenidos;
    }

}
