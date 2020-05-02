package fr.unice.polytech.si3.qgl.theblackpearl.actions;

import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TURNTest {
    private Action turn = new TURN(2, 1.9);

    @Test
    void turnToString() {
        assertEquals( "{\"sailorId\":2,\"type\":\"TURN\",\"rotation\":1.9}", turn.toString());
    }
}