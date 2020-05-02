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

class LowerSailRefereeTest {
    private LowerSailReferee lowerSailReferee;
    private InitGame initGame;
    private Marin m1;
    private Marin m2;
    private Marin m3;

    @BeforeEach
    void init() {
        lowerSailReferee = new LowerSailReferee(2);
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
        assertEquals( "LowerSailReferee{sailorId=2, type='LOWER_SAIL'}", lowerSailReferee.toString());
    }

    @Test
    void tryToLowerSail() {
        when(initGame.getBateau().isOnSailNotUsedOppened(m2)).thenReturn(true);
        assertTrue(m2.isLibre());
        lowerSailReferee.tryToLowerSail(initGame);
        assertFalse(m2.isLibre());

        m2.setLibre(true);
        when(initGame.getBateau().isOnSailNotUsedOppened(m2)).thenReturn(false);
        assertTrue(m2.isLibre());
        lowerSailReferee.tryToLowerSail(initGame);
        assertTrue(m2.isLibre());
    }
}