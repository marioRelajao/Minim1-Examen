package edu.upc.dsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

public class GameEngineImpl implements GameEngine {
    private static GameEngine instance;

    protected Map<String, User> users;
    private List<Game> games;

    final static Logger logger = Logger.getLogger(GameEngine.class);

    GameEngineImpl(){
        this.users = new HashMap<>(); //Clave=id, valor es el user como tal
        this.games = new ArrayList<>();
    }

    public static GameEngine getInstance(){
        if(instance==null) instance = new GameEngineImpl();
        return instance;
    }


    @Override
    public Game createGame(String idGame, String descr, int numLvl) {
        Game g = getGame(idGame);
        if(g==null){ //=null es que no lo hemos encontrado, asi que se puede crear
            g = new Game(idGame,descr,numLvl);
            games.add(g);
            logger.info("Game " + idGame + "added");
            return g;
        }
        logger.info("Game with the same id already created");
        return null;
    }


    @Override
    public Game startGame(String idGame, String idUser) {
        Game g = getGame(idGame);
        User u = getUser(idUser);
        if(g==null || u==null){ //En este caso, o no existe el player o la partida
            logger.info("Either game/player does not exist");
            return null;
        }
        if(u.getPlaying()==true){
            logger.info("Player already in a game");
            return null;
        }
        Match match = new Match(idUser,idGame, String.valueOf(java.time.LocalDate.now())); //Trobat a internet lo de la date
        logger.info("Adding match to user "+ u.getIdUser());
        this.users.get(idUser).addMatch(match);
        return g;
    }

    private User getUser(String idUser) {
        logger.info("Looking for user with id " + idUser);
        if(this.users.get(idUser)==null){
            logger.info("Player not found");
            return null;
        }
        return this.users.get(idUser);
    }

    @Override
    public int getActualLvl(String idUser) { //retornamos el lvl o -1 si cualquier otra cosa
        Match m = getMatchUser(idUser);
        User u = getUser(idUser);
        if(m!=null || u!=null){ //Si existe el game y el user
            logger.info("User "+u.getIdUser()+" is at lvl "+m.getActLvl());
            int res = m.getActLvl();
            return m.getActLvl();
        }

        return -1;
    }

    private Match getMatchUser(String idUser) {
        User u = getUser(idUser);
        logger.info("Looking for the last match of user "+idUser);
        if(u.getPlaying()==true){ //Asumimos que el ultimo match añadido es el que esta jugando
             Match m = u.getHistory().get(u.getHistory().size()-1);
             logger.info("");
             return m;
        }
        logger.info("Couldn't find a match");
        return null;
    }

    @Override
    public String getActualPoints(String idUser) {
        Match m = getMatchUser(idUser);
        if(m!=null){
            logger.info("User has "+ m.getPoints());
            return m.getPoints().toString();
        }
        return null;
    }

    @Override
    public User nextLvl(String idUser, int points, String date) {
        Match m = getMatchUser(idUser);
        if(m!=null){
            logger.info("User is at level" + m.getActLvl() + " of a total "+getGame(m.getIdGame()).getNumLvl() );
            if(m.getActLvl()==getGame(m.getIdGame()).getNumLvl()){
                logger.info("User is at the final level");
                this.users.get(idUser).getHistory().get(m.getIdGame()).addPoints(100);
                this.users.get(idUser).setPlaying(false);
                logger.info("Set user playing to false");
                return getUser(idUser);
            }
            Match newMatch = m;
            newMatch.setActLvl(m.getActLvl()+1);
            newMatch.setPoints(points);
            newMatch.setDate(String.valueOf(java.time.LocalDate.now()));
            getUser(idUser).addMatch(newMatch);
            logger.info("User "+idUser+" advanced to next level");
            return getUser(idUser);
        }
        return null;
    }

    @Override
    public User endMatch(String idUser) {
        Match m = getMatchUser(idUser);
        if(m!=null){ //Si hay match
            this.users.get(idUser).setPlaying(false);
            logger.info("User " + idUser +" ended actual matcg");
            return this.users.get(idUser);
        }
        return null;
    }

    @Override
    public List<User> sortUsers(Game g) {
        Game g1 = getGame(g.getIdGame());
        List<Match> matches = new ArrayList<>();
        for(Map.Entry<String,User> entry: this.users.entrySet()){
            Match m = getMatch(g.getIdGame(), entry.getKey()); //El idUser en este caso corresponde al valor de Hash
            if(m!=null){
                matches.add(m);
            }
        }
        if(matches.size()!=0){
            matches.sort((Match m1, Match m2) -> Integer.compare(m2.getPoints(), m1.getPoints()));
            List<User> sorted = new ArrayList<>();
            for (Match match : matches){
                sorted.add(this.users.get(match.getIdUser()));
            }
            return sorted;
        }
        return null;
    }

    private Match getMatch(String idGame, String idUser) {
        List<Match> l = getMatches(idGame,idUser);
        if(l.size()!=0){
            return l.get(l.size()-1);
        }
        return null;
    }

    private List<Match> getMatches(String idGame, String idUser) {
        logger.info("Trying to get a list of all matches from a game-user");
        Game g = getGame(idGame);
        if(g==null){
            logger.info("Game does not exist");
            return null;
        }
        User u = this.users.get(idUser);
        if(u==null){
            logger.info("User does not exist");
        }
        HashMap<String,Match> matches = u.getHistory();
        List<Match> matchPlayed = new ArrayList<>();
        for (Map.Entry<String,Match> entry: matches.entrySet()){
            if(entry.getValue().getIdGame().equals(idGame)){ //Si esa entrada del hashmap corresponde al idGame
                matchPlayed.add(entry.getValue());
                logger.info("Added a match to de list, total "+ matchPlayed.size());
            }
        }
        return matchPlayed;
    }

    @Override
    public List<Match> matchUser(String idUser) {
        return null;
    }

    @Override
    public List<Match> userActivity(String idUser, String idGame) {
        return null;
    }

    @Override
    public int sizeGames() {
        return this.games.size();
    }

    @Override
    public int sizeUsers() {
        return this.users.size();
    }

    @Override
    public User addUser(String idUser) {
        if(getUser(idUser)==null){
            logger.info("User does not exists, adding");
            return getUser(idUser);
        }
        return null;
    }

    private Game getGame(String idGame) {
        logger.info("Looking for game with id " + idGame);
        for(Game g: this.games){
            if(g.getIdGame().equals(idGame)){ //Si el game existe en mi lista de juegos
                logger.info("Game found! " + g.getDesc());
                return g;
            }
        }
        logger.info("Game not found");
        return null;
    }
}
