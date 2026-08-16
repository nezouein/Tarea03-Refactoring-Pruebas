package com.sportspredictor.shared;

import java.time.LocalDateTime;

public class EventoTenis extends Evento {
    private final Equipos equipos;
    private int setsJugadorUno;
    private int setsJugadorDos;

    public EventoTenis(String id, String nombre, LocalDateTime fechaInicio,
                        Equipos equipos) {
        super(id, nombre, fechaInicio);
        this.equipos = equipos;
        
    }
    public Equipos getEquipos() { return equipos;}
    public String getJugadorUno() { return equipos.getNombreLocal(); }
    public String getJugadorDos() { return equipos.getNombreVisitante(); }
}
