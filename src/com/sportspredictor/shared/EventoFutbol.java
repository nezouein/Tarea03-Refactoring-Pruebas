package com.sportspredictor.shared;

import java.time.LocalDateTime;

public class EventoFutbol extends Evento {
    private final Equipos equipos;
    private int golesLocal;
    private int golesVisitante;

    public EventoFutbol(String id, String nombre, LocalDateTime fechaInicio,
                         Equipos equipos) {
        super(id, nombre, fechaInicio);
        this.equipos = equipos;
    }
    
    public Equipos getEquipos() { return equipos;}
    public String getEquipoLocal() { return equipos.getNombreLocal(); }
    public String getEquipoVisitante() { return equipos.getNombreVisitante(); }
}
