package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Match {
    String idMatch;
    String idUser;
    String idGame;
    int points;
    int actLvl;
    String date;

    public Match(){};

    public Match(String idUser, String idGame, String date) {
        this.idMatch = RandomUtils.getId();
        this.idUser = idUser;
        this.idGame = idGame;
        this.points = 50;
        this.actLvl = 1;
        this.date = date;
    }

    //Getters Setters

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getActLvl() {
        return actLvl;
    }

    public void setActLvl(int actLvl) {
        this.actLvl = actLvl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addPoints(int p){
        this.points = this.points + p;
    }

}
