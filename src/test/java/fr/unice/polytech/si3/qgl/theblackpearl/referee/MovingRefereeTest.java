package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovingRefereeTest {
    private MovingReferee movingReferee;
    private InitGame initGame;
    private Marin m1;
    private Marin m2;
    private Marin m3;

    @BeforeEach
    void init() {
        movingReferee = new MovingReferee(2, 2,1);
        ArrayList<Marin> marins = new ArrayList<>();
        m1 = new Marin(1,0,0, "m1");
        m2 = new Marin(2,1,1, "m2");
        m3 = new Marin(3,2,0, "m3");
        marins.add(m1);
        marins.add(m2);
        marins.add(m3);
        initGame = new InitGame(null, 1, new Bateau(null,0,null, null,mock(Deck.class),null, null), marins);
    }

    @Test
    void getXdistance() {
        assertEquals(2, movingReferee.getXdistance());
    }

    @Test
    void getYdistance() {
        assertEquals(1, movingReferee.getYdistance());
    }

    @Test
    void testToString() {
        assertEquals( "MovingTest{xdistance=2, ydistance=1, sailorId=2, type='MOVING'}", movingReferee.toString());
    }

    @Test
    void tryToMoveMarin() {
        when(initGame.getBateau().getDeck().getWidth()).thenReturn(3);
        when(initGame.getBateau().getDeck().getLength()).thenReturn(6);
        assertEquals(1,m2.getX());
        assertEquals(1,m2.getY());
        movingReferee.tryToMoveMarin(initGame);
        assertEquals(3,m2.getX());
        assertEquals(2,m2.getY());

        movingReferee = new MovingReferee(2, -22,-21);
        movingReferee.tryToMoveMarin(initGame);
        assertEquals(3,m2.getX());
        assertEquals(2,m2.getY());
    }
}