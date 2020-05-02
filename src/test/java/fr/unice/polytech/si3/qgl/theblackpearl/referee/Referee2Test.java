package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import fr.unice.polytech.si3.qgl.theblackpearl.Cockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Referee2Test {
    private Referee2 ref ;
    private InitGame initGame;
    String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Tom Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
    String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":80.0}}";
    private ArrayList<Marin> marins;
    private Marin m1;
    private Marin m2;
    private Marin m3;

    @BeforeEach
    void init() {
        marins = new ArrayList<>();
        m1 = new Marin(1,0,0, "m1");
        m2 = new Marin(2,1,1, "m2");
        m3 = new Marin(3,2,0, "m3");
        marins.add(m1);
        marins.add(m2);
        marins.add(m3);
        initGame = new InitGame(null, 1, mock(Bateau.class), marins);
    }

    @Test
    void resetSailors() {
        initGame = new InitGame(null, 1, null, marins);
        ref = new Referee2(initGame,null);
        ref.resetSailors();
        for(Marin m : marins){
            assertTrue(m.canMove());
            assertTrue(m.isLibre());
        }
    }

    @Test
    void resetEntities(){
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Rame(0,0));
        entities.add(new Rame(1,0));
        entities.add(new Rame(0,1));
        initGame = new InitGame(null, 1, new Bateau(null,0,null,null,null,entities,null), null);
        ref = new Referee2(initGame,null);
        ref.resetEntities();
        for(Entity e : entities){
            assertTrue(e.isLibre());
        }
    }

    @Test
    void initGame() {
        Referee2 ref = new Referee2(gameString, firstRound, new Cockpit());
        ref.initGame();
        assertEquals("ship", ref.getParsedInitGameReferee().getBateau().getType());
        assertEquals( 80.0,ref.getParsedNextRoundReferee().getWind().getStrength());
    }

    @Test
    void nextRound() {
    }

    @Test
    void getFinishGame() throws Exception {
        Referee2 ref = new Referee2(gameString, firstRound, new Cockpit());
        ref.initGame();
        assertFalse(ref.getFinishGame());
        assertFalse(ref.getFinishGame());
    }

    @Test
    void getActions() {
    }

    @Test
    void executeActions() {
    }

    @Test
    void moveShip() {
    }

    @Test
    void crashTest() {
    }

    @Test
    void testCollision() {
    }

    @Test
    void updateNextRound() {
    }

    @Test
    void getGoThroughCheckpoint() {
    }
}