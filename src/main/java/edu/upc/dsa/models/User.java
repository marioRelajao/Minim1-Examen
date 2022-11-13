package edu.upc.dsa.models;

import java.util.HashMap;

public class User {
    String idUser;
    HashMap<String,Match> history; //Historial de las partidas, clave sera el id de la partida, valor la partida
    Boolean playing;

    public User(){};

    public User(String idUser) {
        this.idUser = idUser;
        this.history = new HashMap<>();
    }

    //Getters setters

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public HashMap<String, Match> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, Match> history) {
        this.history = history;
    }

    public Boolean getPlaying() {
        return playing;
    }

    public void setPlaying(Boolean playing) {
        this.playing = playing;
    }

    public void addMatch(Match match) {
        this.history.put(match.idMatch, match);
        this.playing=true;
    }
}
