package edu.upc.dsa.models;

public class Game {
    String idGame;
    String desc;
    int numLvl;

    public Game (){};

    public Game(String idGame, String desc, int numLvl) {
        this.idGame = idGame;
        this.desc = desc;
        this.numLvl = numLvl;
    }

    //Getters y setters

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNumLvl() {
        return numLvl;
    }

    public void setNumLvl(int numLvl) {
        this.numLvl = numLvl;
    }
}
