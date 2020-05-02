package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.referee.LowerSailReferee;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Deck;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InitGameTest {
    private InitGame initGame;
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
    void getBateau() {
    }

    @Test
    void getGoal() {
    }

    @Test
    void setBateau() {
    }

    @Test
    void getMarins() {
    }

    @Test
    void setMarins() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testClone() {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Rame(0,0));
        entities.add(new Rame(1,0));
        Position pos = new Position(0, 0, 0);
        Deck deck = new Deck(2, 3);
        Shape shape = new Rectangle( 2, 3, 0 );
        Bateau bateau = new Bateau("ship", 100, pos, "Les copaings d'abord!", deck, entities, shape);
        initGame = new InitGame(null, 1, bateau, marins);
        assertEquals(initGame.toString(), initGame.clone().toString());
    }

    @Test
    void getSailorById() {
        initGame = new InitGame(null, 1, mock(Bateau.class), marins);
        assertEquals(m2,initGame.getSailorById(2));
        assertEquals(m3,initGame.getSailorById(3));
        assertNull(initGame.getSailorById(5));
    }
}