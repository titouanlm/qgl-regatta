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
        shape1 = getShape(object1, shape1);
        shape2 = getShape(object2, shape2);
        if (shape1 == null || shape2 == null) return false;
        if (shape1 instanceof Circle && shape2 instanceof Circle){ return collisionCercles((Circle) shape1,(Circle) shape2);}
        List<Vecteur> myList = vecteurATester(shape1,shape2);
        for (Vecteur vecteur : myList) {
            if (!objectsInCollision(shape1, shape2, vecteur)) return false;
        }
        return true;
    }

    private Shape getShape(Object object2, Shape shape2) {
        if (object2 instanceof AutreBateau ||object2 instanceof Recif || object2 instanceof Courant) { shape2 = ((VisibleEntity) object2).getShape(); if (((VisibleEntity) object2).getShape() instanceof Rectangle || ((VisibleEntity) object2).getShape() instanceof Circle) shape2.setCoordonneesCentre(((VisibleEntity) object2).getPosition());}
        if (object2 instanceof Checkpoint){ shape2 = ((Checkpoint) object2).getShape(); if (((Checkpoint) object2).getShape() instanceof Rectangle || ((Checkpoint) object2).getShape() instanceof Circle) shape2.setCoordonneesCentre(((Checkpoint) object2).getPosition());}
        if (object2 instanceof Bateau){ shape2 = ((Bateau) object2).getShape(); if (((Bateau) object2).getShape() instanceof Rectangle || ((Bateau) object2).getShape() instanceof Circle) shape2.setCoordonneesCentre(((Bateau) object2).getPosition());}
        return shape2;
    }

    public boolean objectsInCollision(Shape shape, Shape shape2, Vecteur vecteur) {
        if ((shape instanceof Polygone && shape2 instanceof Polygone) || (shape instanceof Polygone && shape2 instanceof Rectangle) || (shape instanceof Rectangle && shape2 instanceof Polygone))
            return shadowInCollisionPolygoneOrRectangle(shape,shape2, vecteur);
        if ((shape instanceof Circle && shape2 instanceof Rectangle) || (shape instanceof Rectangle && shape2 instanceof Circle))
            return shadowInCollisionRectangleCercle(shape,shape2, vecteur);
        if ((shape instanceof Polygone && shape2 instanceof Circle) || (shape instanceof Circle && shape2 instanceof Polygone))
            return shadowInCollisionPolygoneCercle(shape,shape2, vecteur);
        return false;
    }

    public boolean collisionCercles(Circle a,Circle b) {
        double distanceCBCC = calculDistanceEntreDeuxPoints(a.getCoordonneesCentre(),b.getCoordonneesCentre());
        return distanceCBCC <= (a.getRadius()+b.getRadius());
    }

    public boolean shadowInCollisionRectangleCercle(Shape shape, Shape shape2, Vecteur vecteur) {
        return false;
    }

    public boolean shadowInCollisionPolygoneCercle(Shape shape, Shape shape2, Vecteur vecteur) {
        return false;
    }

    public boolean shadowInCollisionPolygoneOrRectangle(Shape shape, Shape shape2, Vecteur vecteur) {
        List<Point> Liste1 = new ArrayList<>();
        List<Point> Liste2 = new ArrayList<>();
        if (shape instanceof Polygone) creationPointsProjetesPolygone((Polygone) shape, Liste1, vecteur);
        else creationPointsProjetesRectangle((Rectangle) shape, Liste1, vecteur);
        if (shape2 instanceof Polygone) creationPointsProjetesPolygone((Polygone) shape2, Liste2, vecteur);
        else creationPointsProjetesRectangle((Rectangle) shape2, Liste2, vecteur);
        double a = 9999999, b = -9999999, c = 9999999, d = -9999999;
        for (Point point : Liste1) {
            if ((point.getY() + point.getX()) <= a) a = point.getY() + point.getX();
            if ((point.getY() + point.getX()) >= b) a = point.getY() + point.getX();
        }
        for (Point point : Liste2) {
            if ((point.getY() + point.getX()) <= c) c = point.getY() + point.getX();
            if ((point.getY() + point.getX()) >= d) d = point.getY() + point.getX();
        }
        for (Point point : Liste1) {
            if ((point.getY() + point.getX()) >= c && (point.getY() + point.getX()) <= d) return true;
        }
        for (Point point : Liste2) {
            if ((point.getY() + point.getX()) >= a && (point.getY() + point.getX()) <= b) return true;
        }
        return false;
    }

    private void creationPointsProjetesPolygone(Polygone shape2, List<Point> liste, Vecteur vecteur) {
        double xb = 1;
        double yb = 1;
        for (int i = 0; i< shape2.getVertices().size(); i++){
            double xa = shape2.getVertices().get(i).getX();
            double ya = shape2.getVertices().get(i).getY();
            calculCoordonneePointProjete(liste, vecteur, xb, yb, xa, ya);
        }
    }

    private void creationPointsProjetesRectangle(Rectangle shape2, List<Point> liste, Vecteur vecteur){ // Pas sur de la fonction
        double xb = 1;
        double yb = 1;
        Position basGauche = shape2.getCoordonneesCentre(); basGauche.setX(basGauche.getX()-shape2.getWidth());basGauche.setX(basGauche.getX()-shape2.getHeight());
        Position basDroit = shape2.getCoordonneesCentre(); basDroit.setX(basGauche.getX()+shape2.getWidth());basDroit.setX(basGauche.getX()-shape2.getHeight());
        Position hautGauche = shape2.getCoordonneesCentre(); hautGauche.setX(basGauche.getX()-shape2.getWidth());hautGauche.setX(basGauche.getX()+shape2.getHeight());
        Position hautDroit = shape2.getCoordonneesCentre(); hautDroit.setX(basGauche.getX()+shape2.getWidth());hautDroit.setX(basGauche.getX()+shape2.getHeight());

        basGauche.setX(basGauche.getX()+Math.cos(shape2.getOrientation()*-shape2.getWidth()/2)-Math.sin(shape2.getOrientation()*-shape2.getHeight()/2)); // ici peut etre confusion avec height et width
        basGauche.setY(basGauche.getY()+Math.sin(shape2.getOrientation()*-shape2.getWidth()/2)+Math.cos(shape2.getOrientation()*-shape2.getHeight()/2));

        basDroit.setX(basDroit.getX()+Math.cos(shape2.getOrientation()*+shape2.getWidth()/2)-Math.sin(shape2.getOrientation()*-shape2.getHeight()/2));
        basDroit.setY(basDroit.getY()+Math.sin(shape2.getOrientation()*+shape2.getWidth()/2)+Math.cos(shape2.getOrientation()*-shape2.getHeight()/2));

        hautGauche.setX(hautGauche.getX()+Math.cos(shape2.getOrientation()*-shape2.getWidth()/2)-Math.sin(shape2.getOrientation()*+shape2.getHeight()/2));
        hautGauche.setY(hautGauche.getY()+Math.sin(shape2.getOrientation()*-shape2.getWidth()/2)+Math.cos(shape2.getOrientation()*+shape2.getHeight()/2));

        hautDroit.setX(hautDroit.getX()+Math.cos(shape2.getOrientation()*+shape2.getWidth()/2)-Math.sin(shape2.getOrientation()*+shape2.getHeight()/2));
        hautDroit.setY(hautDroit.getY()+Math.sin(shape2.getOrientation()*+shape2.getWidth()/2)+Math.cos(shape2.getOrientation()*+shape2.getHeight()/2));

        List<Position> coinsRectangle = new ArrayList<>(); coinsRectangle.add(basGauche);coinsRectangle.add(basDroit);coinsRectangle.add(hautGauche);coinsRectangle.add(hautDroit);
        for (Position point : coinsRectangle){
            double xa = point.getX();
            double ya = point.getY();
            calculCoordonneePointProjete(liste, vecteur, xb, yb, xa, ya);
        }

    }


    private void calculCoordonneePointProjete(List<Point> liste, Vecteur vecteur, double xb, double yb, double xa, double ya) {
        double v = ( (xa - xb) * vecteur.getX() + (ya - yb) * vecteur.getY() ) / ( Math.pow(vecteur.getX(),2) + Math.pow(vecteur.getY(),2) );
        liste.add(new Point(xb + v * vecteur.getX(),yb + v * vecteur.getY()));
    }

    public List<Vecteur> vecteurATester(Shape shape1, Shape shape2){
        List<Vecteur> myList = new ArrayList<>();
        myList = vecteurDirecteurPourProjectionAxe(shape1,myList);
        myList = vecteurDirecteurPourProjectionAxe(shape2,myList);
        return myList;
    }


    public List<Vecteur> vecteurDirecteurPourProjectionAxe(Shape shape, List<Vecteur> myList){
        if (shape instanceof Rectangle) {
            Vecteur vecteur = new Vecteur(Math.cos(((Rectangle) shape).getOrientation()),Math.sin(((Rectangle) shape).getOrientation()));
            Vecteur vecteur2 = new Vecteur(Math.cos(((Rectangle) shape).getOrientation()+Math.PI/2),Math.sin(((Rectangle) shape).getOrientation()+Math.PI/2));
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
        }
        return myList;
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
