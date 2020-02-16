package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calcul = new Calculator();
    private Position posBateau;
    private Position posCheckpoint;
    private Checkpoint checkpoint;

    @BeforeEach
    public void init() {
        posBateau = new Position(947, 1000,0);
        posCheckpoint = new Position(1000, 1000,0);
    }

    @Test
    void calculDistanceEntreDeuxPoints() {
        assertEquals(calcul.calculDistanceEntreDeuxPoints(posBateau, posCheckpoint), 53.0);
        posBateau = new Position(853, 202,0);
        posCheckpoint = new Position(32, -627,0);
        assertEquals(calcul.calculDistanceEntreDeuxPoints(posBateau, posCheckpoint), 1166.7399024632696);
        posBateau = new Position(0, 0,0);
        posCheckpoint = new Position(0, 0,0);
        assertEquals(calcul.calculDistanceEntreDeuxPoints(posBateau, posCheckpoint), 0);
        posBateau = new Position(2, -1,0);
        posCheckpoint = new Position(-4 , -1,0);
        assertEquals(calcul.calculDistanceEntreDeuxPoints(posBateau, posCheckpoint), 6.0);
    }

    @Test
    void calculAngleIdeal() {
        assertEquals(calcul.calculAngleIdeal(posBateau, posCheckpoint), 0.0);
        posBateau = new Position(0, 0,0);
        posCheckpoint = new Position(1000, 1000,0);
        assertEquals(calcul.calculAngleIdeal(posBateau, posCheckpoint), Math.PI/4);
        posCheckpoint = new Position(1000, -1000,0);
        assertEquals(calcul.calculAngleIdeal(posBateau, posCheckpoint), -Math.PI/4);
        posCheckpoint = new Position(-1000, -1000,0);
        assertEquals(calcul.calculAngleIdeal(posBateau, posCheckpoint), -3*Math.PI/4);

        posBateau = new Position(10, 10,Math.PI/4);
        posCheckpoint = new Position(-11, -11,0);
        assertEquals(calcul.calculAngleIdeal(posBateau, posCheckpoint), -Math.PI);
        posBateau = new Position(853, 202,0.9834);
        posCheckpoint = new Position(32, -627,0);
        //assertEquals(calcul.calculAngleIdeal(posBateau, posCheckpoint), 0);

    }

    @Test
    void pointIsInsideCheckpoint() {
        checkpoint = new Checkpoint(posCheckpoint, new Circle(50));
        assertFalse(calcul.pointIsInsideCheckpoint(posBateau, checkpoint));
        posBateau = new Position(950, 1000,0);
        assertTrue(calcul.pointIsInsideCheckpoint(posBateau, checkpoint));
        posBateau = new Position(1000, 1000,0);
        assertTrue(calcul.pointIsInsideCheckpoint(posBateau, checkpoint));
        posBateau = new Position(958.3, 973.8,0);
        assertTrue(calcul.pointIsInsideCheckpoint(posBateau, checkpoint));
    }

    @Test
    void calculAnglesPossiblesEnFonctionDesRames() {

    }


}