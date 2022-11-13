package edu.upc.dsa;

import edu.upc.dsa.models.Game;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {
    final static Logger logger = Logger.getLogger(GameEngine.class);
    GameEngine gm;

    @Before
    public void setUp(){
        gm = new GameEngineImpl();
        gm.createGame("Outer Wilds","I hope you won’t mind if I think of you as a friend",4);
        gm.createGame("God Of War Ragnarok","BOY",4);
        gm.addUser("Yelepo");
        gm.addUser("Churro Mototruco");
        gm.startGame("Outer Wilds", "Yelepo");
    }

    @After
    public void tearDown(){
        this.gm = null;
        logger.info("End of the test");
    }

    @Test
    public void addGame(){
        logger.info("Testing adding games");
        Assert.assertEquals(2,this.gm.sizeGames());
        logger.info("Add a third game");
        gm.createGame("Risk of Rain 2","Cuidao que llueve segundo",5);
        Assert.assertEquals(3,this.gm.sizeGames());
        logger.info("END OF ADD GAMES TEST");
    }

    @Test
    public void startGame(){
        logger.info("Test of setting up games");
        Game g = gm.startGame("sapotruco","Yelepo");
        Assert.assertEquals(null,g); //Pq no va illO?
        g = gm.startGame("Outer Wilds", "Yelepo");
        logger.info("Now we start a game that exists witha player that exists");
        Assert.assertEquals("Outer Wilds",g.getIdGame());
        Assert.assertEquals("Yelepo",gm.getMatchUser("Yelepo").getIdUser());

    }

}
