package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Circle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Point;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collection;


public class Calculator {

    private int[] nombreMarinAplacer;
    private int[] nombreMarinAplacerCopie;

    public int[] getNombreMarinAplacer() {
        return nombreMarinAplacer;
    }

    public void setNombreMarinAplacer(int[] nombreMarinAplacer) {
        this.nombreMarinAplacer = nombreMarinAplacer;
    }

    public int[] getNombreMarinAplacerCopie() {
        return nombreMarinAplacerCopie;
    }

    public void setNombreMarinAplacerCopie(int[] nombreMarinAplacerCopie) {
        this.nombreMarinAplacerCopie = nombreMarinAplacerCopie;
    }

    public void decrementationNombreMarinPlacer(int nombreADecrementer, boolean gauche, boolean droite){
        if (gauche) {
            nombreMarinAplacer[0] -= nombreADecrementer;
        }
        if (droite){
            nombreMarinAplacer[1]-=nombreADecrementer;
        }
    }

    public double calculDistanceEntreDeuxPoints(Position pos1, Position pos2){
        double x = pos1.getX()-pos2.getX();
        double y = pos1.getY()-pos2.getY();
        return Math.sqrt((x*x)+(y*y));
    }

    public double calculAngleIdeal(Position pos1, Position pos2){
        return Math.atan2(pos2.getY()-pos1.getY(),pos2.getX()-pos1.getX()) - pos1.getOrientation();
    }

    public double calculateAreaOf1Triangle(Point sommet1, Point sommet2, Point sommet3){
        double area = (sommet1.getX() * (sommet2.getY() - sommet3.getY()) + sommet2.getX() * (sommet3.getY() - sommet1.getY()) + sommet3.getX() * (sommet1.getY() - sommet2.getY())) / 2.0;
        return Math.abs(area);
    }

    public double calculateAreaOf4Triangles(ArrayList<Point>){

    }

    public ArrayList<Point> calculateCoordinatesOfRectangleVertices(Checkpoint checkpoint){
        ArrayList<Point> sommetsRectangle = new ArrayList<>();
        Point centreRectangle = new Point(checkpoint.getPosition().getX(), checkpoint.getPosition().getY());
        Rectangle rectangle = (Rectangle) checkpoint.getShape();
        Point sommet1 = new Point(centreRectangle.getX()  -  ((rectangle.getHeight()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                - (rectangle.getWidth()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation())
                , centreRectangle.getY()  -  ((rectangle.getHeight()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                + (rectangle.getWidth()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation())  );

        Point sommet2 = new Point(centreRectangle.getX()  +  ((rectangle.getHeight()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                - (rectangle.getWidth()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation())
                , centreRectangle.getY()  +  ((rectangle.getHeight()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                + (rectangle.getWidth()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()));

        Point sommet3 = new Point(centreRectangle.getX()  +  ((rectangle.getHeight()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                + (rectangle.getWidth()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation())
                , centreRectangle.getY()  +  ((rectangle.getHeight()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                - (rectangle.getWidth()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()));

        Point sommet4 = new Point(centreRectangle.getX()  -  ((rectangle.getHeight()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                + (rectangle.getWidth()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation())
                , centreRectangle.getY()  -  ((rectangle.getHeight()/2.0)*Math.sin(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()))
                - (rectangle.getWidth()/2.0)*Math.cos(checkpoint.getPosition().getOrientation()+rectangle.getOrientation()));

        sommetsRectangle.add(sommet1);
        sommetsRectangle.add(sommet2);
        sommetsRectangle.add(sommet3);
        sommetsRectangle.add(sommet4);
        return sommetsRectangle;
    }

    public boolean pointIsInsideRectangle(Position centreBateau, Checkpoint checkpoint){
        ArrayList<Point> sommetsRectangle = calculateCoordinatesOfRectangleVertices(checkpoint);
        double areaOf4Triangles = calculateAreaOf4Triangles();
        return false;
    }

    public boolean pointIsInsideCheckpoint(Position centreBateau, Checkpoint checkpoint) {
        double distanceCBCC = calculDistanceEntreDeuxPoints(centreBateau, checkpoint.getPosition());
        Point pointCentreBateau = new Point(centreBateau.getX(),centreBateau.getY());
        if(checkpoint.getShape() instanceof Circle){
            Circle circle = (Circle) checkpoint.getShape();
            return distanceCBCC <= circle.getRadius();
        }else {
            Rectangle rectangle = (Rectangle) checkpoint.getShape();
            return distanceCBCC <= rectangle.getWidth()/2;
            //return pointIsInsideRectangle(point, checkpoint)
        }
    }

    public double calculRotationRamesTribordBabord(int nbRameBabord, int nbRameTribord, int nbRames){
        if(nbRameTribord>nbRameBabord){
            return ((nbRameTribord-nbRameBabord)*Math.PI)/nbRames;
        }else if(nbRameTribord<nbRameBabord){
            return -((nbRameBabord-nbRameTribord)*Math.PI)/nbRames;
        }else{
            return 0.0;
        }
    }

    public double calculVitesseRames(int nbRamesActives, int nbRames) {
        return (165*(double)nbRamesActives)/nbRames;
    }

    public Position calculNewPositionShip(double shipSpeed , double rotationSpeed, Position shipPosition, int nbSteps){
        double speed = shipSpeed/nbSteps;
        double rotation = rotationSpeed/nbSteps;
        double newX = Math.cos(shipPosition.getOrientation())*speed;
        double newY = Math.sin(shipPosition.getOrientation())*speed;
        return new Position(shipPosition.getX()+newX,shipPosition.getY()+newY, shipPosition.getOrientation()+rotation );
    }

    public double calculVitesseVent(int nbVoileOuverte, int nbVoile, Vent wind, Bateau bateau) {
        return ((double)nbVoileOuverte/nbVoile)*wind.getStrength()*Math.cos(Math.abs(wind.getOrientation()-bateau.getPosition().getOrientation()));
    }
}
