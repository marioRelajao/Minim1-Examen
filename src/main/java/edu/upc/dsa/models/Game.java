package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;
import edu.upc.dsa.models.Level;

public class Game {
    String id;
    String descr;
    List<Level> levels; //Llista de nivells

    public Game(){};

    public Game(String id, String descr, int nLvls) {
        this.id = id;
        this.descr = descr;
        this.levels = fillListLevels (nLvls);
    }

    private List<Level> fillListLevels(int nLvls) {
        List<Level> llistaRetornar = new ArrayList<>();
        for(int i = 0; i<nLvls;i++){
            Level l = new Level("Level"+i+1,"dd-mm-aa");
            llistaRetornar.add(l);
        }
        return llistaRetornar;
    }

    //GETTERS SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
}
