package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Circle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Point;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calcul = new Calculator();
    private Position posBateau;
    private Position posCheckpoint;
    private Checkpoint checkpoint;
    private Shape shape;

    @BeforeEach
    public void init() {
        posBateau = new Position(947, 1000,0);
        posCheckpoint = new Position(1000, 1000,0);
    }

    /*@Test
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
        assertFalse(calcul.shapeInCollision(posBateau, checkpoint));
        posBateau = new Position(950, 1000,0);
        assertTrue(calcul.shapeInCollision(posBateau, checkpoint));
        posBateau = new Position(1000, 1000,0);
        assertTrue(calcul.shapeInCollision(posBateau, checkpoint));
        posBateau = new Position(958.3, 973.8,0);
        assertTrue(calcul.shapeInCollision(posBateau, checkpoint));
    }


    @Test
    void calculRotationRamesTribordBabord() {
        assertEquals(-2*Math.PI/6, calcul.calculRotationRamesTribordBabord(3,1,6));
        assertEquals(Math.PI/2, calcul.calculRotationRamesTribordBabord(0,3,6));
        assertEquals(-Math.PI/2, calcul.calculRotationRamesTribordBabord(18,0,36));
        assertEquals(Math.PI/4, calcul.calculRotationRamesTribordBabord(1,2,4));
        assertEquals(0.0, calcul.calculRotationRamesTribordBabord(2,2,4));
        assertEquals(Math.PI/4, calcul.calculRotationRamesTribordBabord(1,2,4));
    }

    @Test
    void calculAnglesPossiblesEnFonctionDesRames() {

    }

    /*@Test
    void calculNewPositionShip(){
        posBateau = new Position(0, 0,0);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        System.out.println(posBateau);
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
    }*/
        /*
    @Test
    void calculateCoordinatesOfRectangleVertices(){
        shape = new Rectangle(50,100, 0);
        checkpoint = new Checkpoint(posCheckpoint,shape);
        System.out.println(Arrays.toString(calcul.calculateCoordinatesOfRectangleVertices(checkpoint).toArray()));
        // rotation de pi/4 de la position du checkpoint
        posCheckpoint  =  new Position(500, -500, Math.PI/4);
        checkpoint = new Checkpoint(posCheckpoint,shape);
        System.out.println(Arrays.toString(calcul.calculateCoordinatesOfRectangleVertices(checkpoint).toArray()));
        // rotation globale superieure à pi/2
        shape = new Rectangle(50, 100, Math.PI/2);
        checkpoint = new Checkpoint(posCheckpoint,shape);
        System.out.println(Arrays.toString(calcul.calculateCoordinatesOfRectangleVertices(checkpoint).toArray()));
        // rotation de la position du checkpoint superieure à pi/2
        posCheckpoint  =  new Position(-1000, 0, (2*Math.PI)/3);
        checkpoint = new Checkpoint(posCheckpoint,shape);
        System.out.println(Arrays.toString(calcul.calculateCoordinatesOfRectangleVertices(checkpoint).toArray()));
        // rotations de la forme du checkpoint et de  sa position supérieure chacune à pi/2
        posCheckpoint  =  new Position(-1000, 0, (2*Math.PI)/3);
        shape = new Rectangle(50, 100, Math.PI);
        checkpoint = new Checkpoint(posCheckpoint,shape);
        System.out.println(Arrays.toString(calcul.calculateCoordinatesOfRectangleVertices(checkpoint).toArray()));

    }*/

    @Test
    void testPointGauche(){
        Rectangle s = new Rectangle(2, 10, 0.78);
        s.setCoordonneesCentre(new Position(0,0,0.78));
        Point BG = calcul.pointBasGauche(s);
        Point BD = calcul.pointBasDroit(s);
        Point HD = calcul.pointHautDroit(s);
        Point HG = calcul.pointHautGauche(s);
        System.out.println(BG);
        System.out.println(BD);
        System.out.println(HD);
        System.out.println(HG);
    }

}