package edu.upc.dsa;

import org.apache.log4j.Logger;
import org.junit.Before;

public class GameEngineTest {
    final static Logger logger = Logger.getLogger(GameEngine.class);
    GameEngine gm;

    @Before
    public void setUp(){
        gm = new GameEngineImpl();
    }

}
