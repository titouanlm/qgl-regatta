package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Circle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;


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

    public boolean pointIsInsideCheckpoint(Position point, Checkpoint checkpoint) {
        double distanceCBCC = calculDistanceEntreDeuxPoints(point, checkpoint.getPosition());
        if(checkpoint.getShape() instanceof Circle){
            Circle circle = (Circle) checkpoint.getShape();
            return distanceCBCC <= circle.getRadius();
        }else{
            Rectangle rectangle = (Rectangle) checkpoint.getShape();
            return distanceCBCC <= rectangle.getWidth()/2;
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
