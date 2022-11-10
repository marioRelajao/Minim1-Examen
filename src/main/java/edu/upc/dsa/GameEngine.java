package edu.upc.dsa;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;

public interface GameEngine {


    User addUserGame(String idUser, String idGame);

    User getUserbyId(String idUser);

    public Game startGame (Game game, String idUser); //Començar un game amb el seu id i afegir l'user amb id
    public String levelUser (String idUser); //Get level d'un user dins d'un game
    public int actualPoints (String idUser); //Check points d'un user
    public String nextLevel (String idUser, int points, String date); //donat un user, sumar points i afegir data
}
