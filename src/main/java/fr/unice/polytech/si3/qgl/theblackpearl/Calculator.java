package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.*;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.*;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.ArrayList;
import java.util.List;


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

    public boolean shapeInCollision(Position bateauPosition, Checkpoint checkpoint) {
        double distanceCBCC = calculDistanceEntreDeuxPoints(bateauPosition, checkpoint.getPosition());
        if(checkpoint.getShape() instanceof Circle){
            Circle circle = (Circle) checkpoint.getShape();
            return distanceCBCC <= circle.getRadius();
        }else{
            Rectangle rectangle = (Rectangle) checkpoint.getShape();
            return distanceCBCC <= rectangle.getWidth()/2;
            // return shapescollide(bateau, checkpoint);
        }
    }

    public boolean shapescollide(Object object1, Object object2){
        Shape shape1 = null;
        Shape shape2 = null;
        if (object1 instanceof AutreBateau || object1 instanceof Recif || object1 instanceof Courant) shape1 = ((VisibleEntity) object1).getShape();
        if (object1 instanceof Checkpoint) shape1 = ((Checkpoint) object1).getShape();
        if (object1 instanceof Bateau) shape1 = ((Bateau) object1).getShape();
        if (object2 instanceof AutreBateau ||object2 instanceof Recif || object2 instanceof Courant) shape2 = ((VisibleEntity) object2).getShape();
        if (object2 instanceof Checkpoint) shape2 = ((Checkpoint) object2).getShape();
        if (object2 instanceof Bateau) shape1 = ((Bateau) object2).getShape();
        if (shape1 == null || shape2 == null) return false;

        List<Vecteur> myList = anglesATester(shape1,shape2);
        for (Vecteur vecteur : myList) {
            if (!shadowsInCollision(shape1, shape2, vecteur)) return false;
        }
        return true;
    }

    public boolean shadowsInCollision(Shape shape, Shape shape2, Vecteur vecteur){ // A FAIRE
        if (shape instanceof Polygone && shape2 instanceof Polygone){
            List<Point> Liste1 = new ArrayList<>();
            List<Point> Liste2 = new ArrayList<>();
            creationPointsProjetes((Polygone) shape, Liste1,vecteur);
            creationPointsProjetes((Polygone) shape2, Liste2,vecteur);
            for (int i=0; i<((Polygone) shape).getVertices().size();i++){
                for (int b=0;b<((Polygone) shape2).getVertices().size();b++) { // INDICE : regarder si un point est dans un segment délimité par
                                                                                 // les deux points les plus hauts et bad
                }
            }
        }

        return false;

        /*
        double distance2 = (Math.pow(Liste1.get(i).getX() - Liste2.get(b).getX(), 2)) + (Math.pow(Liste1.get(i).getY() - Liste2.get(b).getY(), 2));
                    if (distance1 >= distance2) return true;
         */
    }

    private void creationPointsProjetes(Polygone shape2, List<Point> liste, Vecteur vecteur) { // A MODIFIER
        double xb = 1;
        double yb = 1;
        for (int i = 0; i< shape2.getVertices().size(); i++){
            double xa = shape2.getVertices().get(i).getX();
            double ya = shape2.getVertices().get(i).getY();

            double v = ( (xa - xb) * vecteur.getX() + (ya - yb) * vecteur.getY() ) / ( Math.pow(vecteur.getX(),2) + Math.pow(vecteur.getY(),2) );
            liste.add(new Point(xb + v * vecteur.getX(),yb + v * vecteur.getY()));
        }
    }

    public List<Vecteur> anglesATester(Shape shape1, Shape shape2){
        List<Vecteur> myList = new ArrayList<>();
        myList = vecteurDirecteurPourProjectionAxe(shape1,myList);
        myList = vecteurDirecteurPourProjectionAxe(shape2,myList);
        return myList;
    }

    public List<Vecteur> vecteurDirecteurPourProjectionAxe(Shape shape, List<Vecteur> myList){ // A FAIRE
        /*if (shape instanceof Rectangle) {
            Vecteur vecteur = new Vecteur(,);
            Vecteur vecteur2 = new Vecteur(,);
            myList.add(vecteur);
            myList.add(vecteur2);
        }
        if (shape instanceof Polygone) {
            for (int i = 0; i < ((Polygone) shape).getVertices().size(); i++) {
                if (i != (((Polygone) shape).getVertices().size()-1) ){
                    Vecteur vecteur = new Vecteur(((Polygone) shape).getVertices().get(i + 1).getX() - ((Polygone) shape).getVertices().get(i).getX(),((Polygone) shape).getVertices().get(i + 1).getY() - ((Polygone) shape).getVertices().get(i).getY());
                    myList.add(vecteur);
                }
                else{
                    Vecteur vecteur = new Vecteur(((Polygone) shape).getVertices().get(0).getX() - ((Polygone) shape).getVertices().get(i).getX(),((Polygone) shape).getVertices().get(0).getY() - ((Polygone) shape).getVertices().get(i).getY());myList.add(vecteur);
                }
            }
>>>>>>> Stashed changes
        }
        return myList;*/
        return null;
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
        double newX = (int)(Math.cos(shipPosition.getOrientation())*speed * 100)/100.;
        double newY = (int)(Math.sin(shipPosition.getOrientation())*speed * 100)/100.;
        return new Position(shipPosition.getX()+newX,shipPosition.getY()+newY, shipPosition.getOrientation()+rotation );
    }

    public double calculVitesseVent(int nbVoileOuverte, int nbVoile, Vent wind, Bateau bateau) {
        return ((double)nbVoileOuverte/nbVoile)*wind.getStrength()*Math.cos(Math.abs(wind.getOrientation()-bateau.getPosition().getOrientation()));
    }
}
