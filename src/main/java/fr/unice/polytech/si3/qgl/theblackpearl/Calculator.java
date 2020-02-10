package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Circle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;

import java.util.List;

public class Calculator {

    public double calculDistanceEntreDeuxPoints(Position pos1, Position pos2){
        double x = pos1.getX()-pos2.getX();
        double y = pos1.getY()-pos2.getY();
        return Math.sqrt((x*x)+(y*y));
    }

    public double calculAngleIdeal(Position pos1, Position pos2){
        return Math.atan2(pos2.getY()-pos1.getY(),pos2.getX()-pos1.getX()) - pos1.getOrientation();
    }

    public boolean pointIsInsideCheckpoint(Position point, Checkpoint checkpoint) {
        double distanceCBCC = calculDistanceEntreDeuxPoints(point, checkpoint.getPosition());
        if(checkpoint.getShape() instanceof Circle){
            Circle circle = (Circle) checkpoint.getShape();
            return distanceCBCC <= circle.getRadius();
        }else{
            Rectangle rectangle = (Rectangle) checkpoint.getShape();
            return false;
        }
    }

    public List<Double> calculAnglesPossiblesEnFonctionDesRames(int nbRamesTribord, int nbRamesBabord){
        return  null;
    }

}
