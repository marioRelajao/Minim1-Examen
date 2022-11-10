package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class User {
    String id;
    String name;
    int points;
    List<Game> gamesPlayed;

    public User(){};

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.points = 50;
        this.gamesPlayed = new LinkedList<>();
    }

    //GETTERS SETTERS

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Game> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(List<Game> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int addGame(Game g){
        for(Game g1: this.gamesPlayed){
            if(g1.getId().equals(g.getId())){
                return -1; //Significa que volem iniciar un game que ja tenim
            }
        }
        this.gamesPlayed.add(g);
        return 0;
    }
}
