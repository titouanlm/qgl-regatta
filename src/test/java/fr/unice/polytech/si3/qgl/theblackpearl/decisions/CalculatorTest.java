package fr.unice.polytech.si3.qgl.theblackpearl.decisions;

<<<<<<< HEAD:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/CalculatorTest.java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
=======
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Calculator;
>>>>>>> master:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/decisions/CalculatorTest.java
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calcul = new Calculator();
    private Position posBateau;
    private Position posCheckpoint;
    private InitGame parsedInitGame;
    private NextRound parsedNextRound;
    private Checkpoint checkpoint;
    private Shape shape;

    @BeforeEach
    public void init() {
        posBateau = new Position(947, 1000,0);
        posCheckpoint = new Position(1000, 1000,0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            parsedInitGame = objectMapper.readValue(gameString, InitGame.class);
            parsedNextRound = objectMapper.readValue(firstRound, NextRound.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

<<<<<<< HEAD:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/CalculatorTest.java
=======
//    @Test
//    void pointIsInsideCheckpoint() {
//        checkpoint = new Checkpoint(posCheckpoint, new Circle(50));
//        assertFalse(calcul.shapeInCollision(posBateau, checkpoint));
//        posBateau = new Position(950, 1000,0);
//        assertTrue(calcul.shapeInCollision(posBateau, checkpoint));
//        posBateau = new Position(1000, 1000,0);
//        assertTrue(calcul.shapeInCollision(posBateau, checkpoint));
//        posBateau = new Position(958.3, 973.8,0);
//        assertTrue(calcul.shapeInCollision(posBateau, checkpoint));
//    }



>>>>>>> master:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/decisions/CalculatorTest.java
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

    @Test
    void calculNewPositionShip(){
        posBateau = new Position(0, 0,0);
<<<<<<< HEAD:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/CalculatorTest.java
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        assertEquals(10.0, posBateau.getX());
        assertEquals(0.0, posBateau.getY());
        assertEquals(0.1, posBateau.getOrientation());
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        assertEquals(19.950041652780257, posBateau.getX());
        assertEquals(0.9983341664682815, posBateau.getY());
        assertEquals(0.2, posBateau.getOrientation());
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
=======
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        assertEquals(10.0, posBateau.getX());
        assertEquals(0.0, posBateau.getY());
        assertEquals(0.1, posBateau.getOrientation());
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
        assertEquals(19.950041652780257, posBateau.getX());
        assertEquals(0.9983341664682815, posBateau.getY());
        assertEquals(0.2, posBateau.getOrientation());
        posBateau = calcul.calculNewPositionShip(100, 1, posBateau, 10);
>>>>>>> master:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/decisions/CalculatorTest.java
        assertEquals(29.750707431192673, posBateau.getX());
        assertEquals(2.9850274744188936, posBateau.getY());
        assertEquals(0.30000000000000004, posBateau.getOrientation());
    }
<<<<<<< HEAD:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/CalculatorTest.java

    @Test
    public void bateauDansCheckpointnTest() throws Exception {
        parsedNextRound.getBateau().setPosition(new Position(10,5,9));
        assertTrue(calcul.bateauDansCheckpoint(parsedNextRound.getBateau(),new Checkpoint(new Position(48,32,0),new Circle(50))));
        parsedNextRound.getBateau().setPosition(new Position(30,25,9));
        assertTrue(calcul.bateauDansCheckpoint(parsedNextRound.getBateau(),new Checkpoint(new Position(48,32,0),new Circle(50))));
        parsedNextRound.getBateau().setPosition(new Position(50,8,9));
        assertTrue(calcul.bateauDansCheckpoint(parsedNextRound.getBateau(),new Checkpoint(new Position(48,32,0),new Circle(50))));
        parsedNextRound.getBateau().setPosition(new Position(15,56,9));
        assertTrue(calcul.bateauDansCheckpoint(parsedNextRound.getBateau(),new Checkpoint(new Position(48,32,0),new Circle(50))));
        parsedNextRound.getBateau().setPosition(new Position(7,20,9));
        assertTrue(calcul.bateauDansCheckpoint(parsedNextRound.getBateau(),new Checkpoint(new Position(48,32,0),new Circle(50))));
        parsedNextRound.getBateau().setPosition(new Position(20,30,9));
        assertTrue(calcul.bateauDansCheckpoint(parsedNextRound.getBateau(),new Checkpoint(new Position(48,32,0),new Circle(50))));
    }

    @Test
    public void shapesCollideTest() throws Exception {
        parsedNextRound.getBateau().setPosition(new Position(2012.9130229585658,4116.056423252195,1.5794560650965999));
        assertTrue(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));
        parsedNextRound.getBateau().setPosition(new Position(2128.58646,3892.33699,1.5794560650965999));
        assertTrue(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));
        parsedNextRound.getBateau().setPosition(new Position(2120.23487,3838.0516,1.5794560650965999));
        assertTrue(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));
        parsedNextRound.getBateau().setPosition(new Position(2003.31248,4261.89523,1.5794560650965999));
        assertTrue(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));
        parsedNextRound.getBateau().setPosition(new Position(2241.3330,4401.78451,1.5794560650965999));
        assertTrue(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));
        parsedNextRound.getBateau().setPosition(new Position(2270.5636,4165.85185,1.5794560650965999));
        assertTrue(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));
        parsedNextRound.getBateau().setPosition(new Position(2262.2120,43969.58928,1.5794560650965999));
        assertFalse(calcul.shapesCollide(parsedNextRound.getBateau(),parsedNextRound.getVisibleEntities().get(6)));

        // FAIRE AU MOINS ENCORE 50 LIGNES
    }

    @Test
    public void pointBasGaucheTriangleTest(){

    }

    @Test
    public void pointBasDroitTriangleTest(){

    }

    @Test
    public void pointHautGaucheTriangleTest(){

    }

    @Test
    public void pointHautDroitTriangleTest(){

    }

    @Test
    public void convertionVecteurCartesienPolaireTest(){

    }

    @Test
    public void convertionVecteurPolaireCartesien(){

    }

    @Test
    public void calculRotationRamesTribordBabordTest(){

    }

    @Test
    public void calculNewPositionShipTest(){

    }

    @Test
    public void calculVitesseVentTest(){

    }

    @Test
    public void calculInfluenceOfStreamTest(){

    }

=======
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

>>>>>>> master:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/decisions/CalculatorTest.java
    @Test
    void testPoints(){
        Rectangle s = new Rectangle(2, 10, 0);
        s.setCoordonneesCentre(new Position(0,0,0));
<<<<<<< HEAD:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/CalculatorTest.java
        Point BG = calcul.pointBasGaucheTriangle(s);
        Point BD = calcul.pointBasDroitTriangle(s);
        Point HD = calcul.pointHautDroitTriangle(s);
        Point HG = calcul.pointHautGaucheTriangle(s);
=======
        Point BG = calcul.pointBasGauche(s);
        Point BD = calcul.pointBasDroit(s);
        Point HD = calcul.pointHautDroit(s);
        Point HG = calcul.pointHautGauche(s);
>>>>>>> master:src/test/java/fr/unice/polytech/si3/qgl/theblackpearl/decisions/CalculatorTest.java
        assertEquals(-5 ,BG.getX());
        assertEquals(-1 ,BG.getY());
        assertEquals(5 ,BD.getX());
        assertEquals(-1 ,BD.getY());
        assertEquals(5 ,HD.getX());
        assertEquals(1 ,HD.getY());
        assertEquals(-5 ,HG.getX());
        assertEquals(1,HG.getY());
    }

}