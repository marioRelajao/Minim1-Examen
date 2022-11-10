package edu.upc.dsa;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;


import java.util.*;

public class GameEngineImpl implements GameEngine{
    private static GameEngine instance;

    protected Map<String, User> users;
    protected List<Game> games;

    final static Logger logger = Logger.getLogger(GameEngine.class);

    GameEngineImpl(){
        this.users = new HashMap<>(); //Clave = id, valor es el usuario como tal
        this.games = new ArrayList<>();
    }

    public static GameEngine getInstance(){
        if(instance==null) instance = new GameEngineImpl();
        return instance;
    }



    @Override
    public User addUserGame(String idUser, String idGame) {
        logger.info("Trying to add User " + idUser + " to game " + idGame);
        if(getUserbyId(idUser)!=null && getGamebyId(idGame)!=null){ //Si es != de null implica que he encontrado un user/game
            User u = getUserbyId(idUser);
            Game g = getGamebyId(idGame);
            int res = u.addGame(g);
            if(res==-1){
                logger.info("Can't add game, user already in");
                return null;
            }
            logger.info("Added "+ idGame + "to user" +idUser);
        }
        logger.info("Either the game or the user doesn't exist");
        return null;
    }

    @Override
    public User getUserbyId(String idUser) {
        logger.info("Looking for User:" + idUser);
        for(Map.Entry<String,User> entry : this.users.entrySet()){
            if(entry.getValue().getId().equals(idUser)){
                logger.info("Found user: " + entry.getValue().getName());
                return entry.getValue();
            }
        }
        logger.info("Not found :c");
        return null;
    }

    public Game getGamebyId(String idGame) {
        logger.info("Looking for Game" + idGame);
        for(Game g: this.games){
            if(g.getId().equals(idGame)){ //Si el game existe en mi lista de juegos
                logger.info("Game " + idGame + "exists");
                return g;
            }
        }
        logger.info("Not found :c");
        return null;
    }

    @Override
    public Game startGame(Game game, String idUser) {
        return null;
    }

    @Override
    public String levelUser(String idUser) {
        return null;
    }

    @Override
    public int actualPoints(String idUser) {
        return 0;
    }

    @Override
    public String nextLevel(String idUser, int points, String date) {
        return null;
    }
}
