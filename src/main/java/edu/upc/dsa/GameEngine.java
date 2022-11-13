package edu.upc.dsa;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.User;

import java.util.List;

public interface GameEngine {
    //Operaciones que se piden en la parte1
    public Game createGame(String id, String descr, int numLvl);
    public Game startGame(String idGame, String idUser);
    public int getActualLvl(String idUser);
    public String getActualPoints(String idUser);
    public User nextLvl(String idUser, int points, String date);
    public User endMatch(String idUser);
    public List<User> sortUsers(Game g);
    public List<Match> matchUser(String idUser);
    public List<Match> userActivity(String idUser, String idGame);

    int sizeGames();

    int sizeUsers();

    public User addUser(String idUser);
}
