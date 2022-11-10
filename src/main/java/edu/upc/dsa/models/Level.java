package edu.upc.dsa.models;

import java.util.List;

public class Level {
    String name;
    String fecha;

    public Level(){};

    public Level(String name, String fecha) {
        this.name = name;
        this.fecha = fecha;
    }

    //GETTERS SETTERS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
