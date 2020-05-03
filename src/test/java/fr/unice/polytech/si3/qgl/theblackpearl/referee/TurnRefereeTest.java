package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TurnRefereeTest {
    private TurnReferee turnReferee;
    private InitGame initGame;
    private Marin m1;
    private Marin m2;
    private Marin m3;

    @BeforeEach
    void init() {
        turnReferee = new TurnReferee(2, 0.43);
        ArrayList<Marin> marins = new ArrayList<>();
        m1 = new Marin(1,0,0, "m1");
        m2 = new Marin(2,1,1, "m2");
        m3 = new Marin(3,2,0, "m3");
        marins.add(m1);
        marins.add(m2);
        marins.add(m3);
        initGame = new InitGame(null, 1, mock(Bateau.class), marins);
    }
    @Test
    void testToString() {
        assertEquals( "TurnTest{rotation=0.43, sailorId=2, type='TURN'}", turnReferee.toString());
    }

    @Test
    void tryToTurn() {
        when(initGame.getBateau().isOnRudderNotUsed(m2)).thenReturn(true);
        assertTrue(m2.isLibre());
        turnReferee.tryToTurn(initGame);
        assertFalse(m2.isLibre());

        m2.setLibre(true);
        when(initGame.getBateau().isOnRudderNotUsed(m2)).thenReturn(false);
        turnReferee = new TurnReferee(2, -Math.PI/4);
        assertTrue(m2.isLibre());
        turnReferee.tryToTurn(initGame);
        assertTrue(m2.isLibre());

        m2.setLibre(true);
        when(initGame.getBateau().isOnRudderNotUsed(m2)).thenReturn(true);
        turnReferee = new TurnReferee(2, Math.PI/4);
        assertTrue(m2.isLibre());
        turnReferee.tryToTurn(initGame);
        assertFalse(m2.isLibre());

        m2.setLibre(true);
        when(initGame.getBateau().isOnRudderNotUsed(m2)).thenReturn(true);
        turnReferee = new TurnReferee(2, 0.0);
        assertTrue(m2.isLibre());
        assertEquals(0.0,turnReferee.tryToTurn(initGame));
        assertFalse(m2.isLibre());

        m2.setLibre(true);
        when(initGame.getBateau().isOnRudderNotUsed(m2)).thenReturn(true);
        turnReferee = new TurnReferee(2, -Math.PI/4);
        assertTrue(m2.isLibre());
        turnReferee.tryToTurn(initGame);
        assertFalse(m2.isLibre());
    }
}