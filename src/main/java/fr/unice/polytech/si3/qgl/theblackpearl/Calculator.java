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

    public boolean shapeInCollision(Bateau bateau, Checkpoint checkpoint) throws Exception { // Peut y avoir un probleme du à l'orientation du rectangle et du bateau
        return shapescollide(bateau, checkpoint);
    }

    public boolean shapescollide(Object object1, Object object2) throws Exception {
        Shape shape1 =  getShape(object1); // BON
        Shape shape2  = getShape(object2); // BON
        if (shape1 == null || shape2 == null) return false;
        if (shape1 instanceof Circle && shape2 instanceof Circle){ return collisionCercles((Circle) shape1,(Circle) shape2);}
        List<Vecteur> myList = vecteurATester(shape1,shape2); // BON
        for (Vecteur vecteur : myList) { // BON
            if (!objectsInCollision(shape1, shape2, vecteur)) return false;
        }
        return true;
    }

    private Shape getShape(Object object2) {
        Shape shape2 = null;
        if (object2 instanceof AutreBateau ||object2 instanceof Recif || object2 instanceof Courant){
            shape2 = ((VisibleEntity) object2).getShape();
            if (shape2 instanceof Circle)
                shape2.setCoordonneesCentre(((VisibleEntity) object2).getPosition());
            else if (shape2 instanceof Rectangle){
                shape2.setCoordonneesCentre(((VisibleEntity) object2).getPosition());
                shape2.setOrientationCentre(((VisibleEntity) object2).getPosition().getOrientation());
            }
        }
        else if (object2 instanceof Checkpoint){
            shape2 = ((Checkpoint) object2).getShape();
            if (shape2 instanceof Circle)
                shape2.setCoordonneesCentre(((Checkpoint) object2).getPosition());
            else if (shape2 instanceof Rectangle){
                shape2.setCoordonneesCentre(((Checkpoint) object2).getPosition());
                shape2.setOrientationCentre(((Checkpoint) object2).getPosition().getOrientation());
            }
        }
        else if (object2 instanceof Bateau){
            shape2 = ((Bateau) object2).getShape();
            if (shape2 instanceof Rectangle){
                shape2.setCoordonneesCentre(((Bateau) object2).getPosition());
                shape2.setOrientationCentre(((Bateau) object2).getPosition().getOrientation());
            }
        }
        return shape2;
    }

    public boolean objectsInCollision(Shape shape, Shape shape2, Vecteur vecteur) {
        if ((shape instanceof Polygone && shape2 instanceof Rectangle) || (shape instanceof Rectangle && shape2 instanceof Polygone))
            return shadowInCollisionPolygoneOrRectangle(shape,shape2, vecteur);
        if ((shape instanceof Circle && shape2 instanceof Rectangle) || (shape instanceof Rectangle && shape2 instanceof Circle))
            return shadowInCollisionRectangleCercle(shape,shape2, vecteur);
        if ((shape instanceof Polygone && shape2 instanceof Circle) || (shape instanceof Circle && shape2 instanceof Polygone))
            return shadowInCollisionPolygoneCercle(shape,shape2, vecteur);
        if ((shape) instanceof Rectangle && shape2 instanceof Rectangle)
            return shadowInCollisionRectangleRectangle(shape,shape2,vecteur);
        if ((shape instanceof Polygone && shape2 instanceof Polygone))
            return shadowInCollisionPolygonePolygone(shape,shape2,vecteur);
        return false;
    }

    public boolean collisionCercles(Circle a,Circle b) {
        double distanceCBCC = calculDistanceEntreDeuxPoints(a.getCoordonneesCentre(),b.getCoordonneesCentre());
        return distanceCBCC <= (a.getRadius()+b.getRadius());
    }

    public boolean collisionSegments(List<Point> Liste1, List<Point> Liste2){ // peut etre faux
        double a = 9999999, b = -9999999, c = 9999999, d = -9999999;
        for (Point point : Liste1) {
            if ((point.getY() + point.getX()) <= a) a = (point.getY() + point.getX());
            if ((point.getY() + point.getX()) >= b) b = (point.getY() + point.getX());
        }
        for (Point point : Liste2) {
            if ((point.getY() + point.getX()) <= c) c = (point.getY() + point.getX());
            if ((point.getY() + point.getX()) >= d) d = (point.getY() + point.getX());
        }
        for (Point point : Liste1) {
            if ((point.getY() + point.getX()) >= c && (point.getY() + point.getX()) <= d) return true;
        }
        for (Point point : Liste2) {
            if ((point.getY() + point.getX()) >= a && (point.getY() + point.getX()) <= b) return true;
        }
        return false;
    }

    public boolean shadowInCollisionPolygonePolygone(Shape shape, Shape shape2, Vecteur vecteur){
        List<Point> Liste1 = new ArrayList<>();
        List<Point> Liste2 = new ArrayList<>();
        creationPointsProjetesPolygone((Polygone)shape, Liste1, vecteur);
        creationPointsProjetesPolygone((Polygone)shape2, Liste2, vecteur);
        return collisionSegments(Liste1,Liste2);
    }

    public boolean shadowInCollisionRectangleRectangle(Shape shape, Shape shape2, Vecteur vecteur){
        List<Point> Liste1 = new ArrayList<>();
        List<Point> Liste2 = new ArrayList<>();
        creationPointsProjetesRectangle((Rectangle)shape, Liste1, vecteur);
        creationPointsProjetesRectangle((Rectangle)shape2, Liste2, vecteur);
        return collisionSegments(Liste1,Liste2);
    }

    public boolean shadowInCollisionRectangleCercle(Shape shape, Shape shape2, Vecteur vecteur) {
        List<Point> Liste1 = new ArrayList<>();
        List<Point> Liste2 = new ArrayList<>();
        if (shape instanceof Rectangle) {
            creationPointsProjetesRectangle((Rectangle)shape, Liste1, vecteur);
            creationPointsProjetesCercle((Circle)shape2, Liste2, vecteur);
        }
        else {
            creationPointsProjetesRectangle((Rectangle) shape2, Liste2, vecteur);
            creationPointsProjetesCercle((Circle)shape, Liste1, vecteur);
        }
        return collisionSegments(Liste1,Liste2);
    }

    public boolean shadowInCollisionPolygoneCercle(Shape shape, Shape shape2, Vecteur vecteur) {
        List<Point> Liste1 = new ArrayList<>();
        List<Point> Liste2 = new ArrayList<>();
        if (shape instanceof Polygone) {
            creationPointsProjetesPolygone((Polygone)shape, Liste1, vecteur);
            creationPointsProjetesCercle((Circle)shape2, Liste2, vecteur);
        }
        else {
            creationPointsProjetesPolygone((Polygone) shape2, Liste2, vecteur);
            creationPointsProjetesCercle((Circle)shape,Liste1, vecteur);
        }
        return collisionSegments(Liste1,Liste2);
    }

    public boolean shadowInCollisionPolygoneOrRectangle(Shape shape, Shape shape2, Vecteur vecteur) {
        List<Point> Liste1 = new ArrayList<>();
        List<Point> Liste2 = new ArrayList<>();
        if (shape instanceof Polygone){
            creationPointsProjetesPolygone((Polygone) shape, Liste1, vecteur);
            creationPointsProjetesRectangle((Rectangle) shape2, Liste2, vecteur);}
        else {
            creationPointsProjetesPolygone((Polygone) shape2, Liste2, vecteur);
            creationPointsProjetesRectangle((Rectangle) shape, Liste1, vecteur);}
        return collisionSegments(Liste1,Liste2);
    }

    public void creationPointsProjetesCercle(Circle shape, List<Point> liste, Vecteur vecteur){
        calculCoordonneePointProjeteCercle(liste, vecteur,1,1,shape.getCoordonneesCentre().getX() , shape.getCoordonneesCentre().getY(), shape);
    }

    private void creationPointsProjetesPolygone(Polygone shape2, List<Point> liste, Vecteur vecteur) { // PAS ENCORE GÉRÉ LA ROTATION DES POLYGONES
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
        Point basGauche = pointBasGauche(shape2);
        Point basDroit = pointBasDroit(shape2);
        Point hautGauche = pointHautGauche(shape2);
        Point hautDroit = pointHautDroit(shape2);

        List<Point> coinsRectangle = new ArrayList<>(); coinsRectangle.add(basGauche);coinsRectangle.add(basDroit);coinsRectangle.add(hautGauche);coinsRectangle.add(hautDroit);
        for (Point point : coinsRectangle){
            double xa = point.getX();
            double ya = point.getY();
            calculCoordonneePointProjete(liste, vecteur, xb, yb, xa, ya);
        }

    }

    private Point pointBasGauche(Rectangle shape2){ // MODIFIÉ MAIS PAS SUR
        double xModifie = Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getHeight()/2-Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getWidth()/2;
        double yModifie = Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getHeight()/2+Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getWidth()/2;
        return new Point(xModifie+shape2.getCoordonneesCentre().getX(),yModifie+shape2.getCoordonneesCentre().getY());
    }

    private Point pointBasDroit(Rectangle shape2){ // ici peut etre confusion avec height et width
        double xModifie = Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getHeight()/2-Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getWidth()/2;
        double yModifie = Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getHeight()/2+Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getWidth()/2;
        return new Point(xModifie+shape2.getCoordonneesCentre().getX(),yModifie+shape2.getCoordonneesCentre().getY());
    }

    private Point pointHautGauche(Rectangle shape2){ // ici peut etre confusion avec height et width
        double xModifie = Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getHeight()/2-Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getWidth()/2;
        double yModifie = Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*-shape2.getHeight()/2+Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getWidth()/2;
        return new Point(xModifie+shape2.getCoordonneesCentre().getX(),yModifie+shape2.getCoordonneesCentre().getY());
    }

    private Point pointHautDroit(Rectangle shape2){ // ici peut etre confusion avec height et width
        double xModifie = Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getHeight()/2-Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getWidth()/2;
        double yModifie = Math.sin(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getHeight()/2+Math.cos(shape2.getOrientationRectangle()+shape2.getCoordonneesCentre().getOrientation())*+shape2.getWidth()/2;
        return new Point(xModifie+shape2.getCoordonneesCentre().getX(),yModifie+shape2.getCoordonneesCentre().getY());
    }

    private void calculCoordonneePointProjete(List<Point> liste, Vecteur vecteur, double xb, double yb, double xa, double ya) {
        double v = ( (xa - xb) * vecteur.getX() + (ya - yb) * vecteur.getY() ) / ( Math.pow(vecteur.getX(),2) + Math.pow(vecteur.getY(),2) );
        liste.add(new Point(xb + v * vecteur.getX(),yb + v * vecteur.getY()));
    }

    private void calculCoordonneePointProjeteCercle(List<Point> liste, Vecteur vecteur, double xb, double yb, double xa, double ya, Circle shape) { // PEUT ETRE ICI
        double v = ( (xa - xb) * vecteur.getX() + (ya - yb) * vecteur.getY() ) / ( Math.pow(vecteur.getX(),2) + Math.pow(vecteur.getY(),2) );
        liste.add(new Point(xb + v * vecteur.getX()+shape.getRadius(),yb + v * vecteur.getY()+(shape.getRadius()*vecteur.getY()/vecteur.getX())));
        liste.add(new Point(xb + v * vecteur.getX()-shape.getRadius(),yb + v * vecteur.getY()+(-shape.getRadius()*vecteur.getY()/vecteur.getX())));
    }

    public List<Vecteur> vecteurATester(Shape shape1, Shape shape2) throws Exception {
        List<Vecteur> myList = new ArrayList<>();
        if (shape1 instanceof Circle) {
            myList.add(vecteurDirecteurProjetectionAxeCercle((Circle) shape1, shape2));
            myList=vecteurDirecteurPourProjectionAxe(shape2,myList);
        }
        else if (shape2 instanceof Circle) {
            myList.add(vecteurDirecteurProjetectionAxeCercle((Circle) shape2, shape1));
            myList=vecteurDirecteurPourProjectionAxe(shape1,myList);
        }
        else {
            myList=vecteurDirecteurPourProjectionAxe(shape1,myList);
            myList=vecteurDirecteurPourProjectionAxe(shape2,myList);
        }
        return myList;
    }

    public Vecteur vecteurDirecteurProjetectionAxeCercle(Circle shape, Shape shape2) throws Exception { // points et axe
        Point point = null; // c'est pas très intelligent ici mais bon
        double d = 99999.999;
        List<Point> liste = new ArrayList<>();
        if (shape2 instanceof Rectangle){
            Point basGauche = pointBasGauche((Rectangle) shape2);liste.add(basGauche);
            Point basDroit = pointBasDroit((Rectangle) shape2);liste.add(basDroit);
            Point hautGauche = pointHautGauche((Rectangle) shape2);liste.add(hautGauche);
            Point hautDroit = pointHautDroit((Rectangle) shape2);liste.add(hautDroit);
            for (Point pointRectangle : liste){
                if (Math.sqrt(Math.pow(pointRectangle.getX()-shape.getCoordonneesCentre().getX(),2)+Math.pow(pointRectangle.getY()-shape.getCoordonneesCentre().getY(),2)) < d){
                    point=pointRectangle;
                    d = Math.sqrt(Math.pow(pointRectangle.getX()-shape.getCoordonneesCentre().getX(),2)+Math.pow(pointRectangle.getY()-shape.getCoordonneesCentre().getY(),2));
                }
            }
            Vecteur vecteur = vecteurUnitairePourCerclePoint(shape, point);
            return vecteur;
        }
        else {
            for (Point pointPolygone : ((Polygone) shape2).getVertices()){
                if (Math.sqrt(Math.pow(pointPolygone.getX()-shape.getCoordonneesCentre().getX(),2)+Math.pow(pointPolygone.getY()-shape.getCoordonneesCentre().getY(),2)) < d){
                    point=pointPolygone;
                    d = Math.sqrt(Math.pow(pointPolygone.getX()-shape.getCoordonneesCentre().getX(),2)+Math.pow(pointPolygone.getY()-shape.getCoordonneesCentre().getY(),2));
                }
            }
            Vecteur vecteur = vecteurUnitairePourCerclePoint(shape, point);
            return vecteur;
        }
    }

    private Vecteur vecteurUnitairePourCerclePoint(Circle shape, Point point) throws Exception {
        if (point!=null){
            Vecteur vecteur = new Vecteur((shape.getCoordonneesCentre().getX()-point.getX()),shape.getCoordonneesCentre().getY()-point.getY());
            double normeVecteur = Math.sqrt(Math.pow(vecteur.getX(),2)+Math.pow(vecteur.getY(),2));
            if (normeVecteur !=0) return new Vecteur(vecteur.getX()/normeVecteur,vecteur.getY()/normeVecteur);
            else throw new Exception("Can't divide by zero");
        }
        else throw new Exception("Point is null");
    }

    public Vecteur vecteurUnitaireVecteur(Vecteur vecteur) throws Exception {
        double normeVecteur = Math.sqrt(Math.pow(vecteur.getX(),2)+Math.pow(vecteur.getY(),2));
        if (normeVecteur != 0)
        return new Vecteur(vecteur.getX()/normeVecteur,vecteur.getY()/normeVecteur);
        else throw new Exception("Can't divide by zero");
    }

    public List<Vecteur> vecteurDirecteurPourProjectionAxe(Shape shape, List<Vecteur> myList) throws Exception { // MODIFIER MAIS PAS SUR
        if (shape instanceof Rectangle) {
            Vecteur vecteur = new Vecteur(Math.cos(((Rectangle) shape).getOrientationRectangle()+shape.getCoordonneesCentre().getOrientation()),Math.sin(((Rectangle) shape).getOrientationRectangle()+shape.getCoordonneesCentre().getOrientation()));
            vecteur=vecteurUnitaireVecteur(vecteur);
            Vecteur vecteur2 = new Vecteur(Math.cos(((Rectangle) shape).getOrientationRectangle()+Math.PI/2+shape.getCoordonneesCentre().getOrientation()),Math.sin(((Rectangle) shape).getOrientationRectangle()+Math.PI/2+shape.getCoordonneesCentre().getOrientation()));
            vecteur2=vecteurUnitaireVecteur(vecteur2);
            myList.add(vecteur);
            myList.add(vecteur2);
        }
        if (shape instanceof Polygone) {
            for (int i = 0; i < ((Polygone) shape).getVertices().size(); i++) {
                if (i != (((Polygone) shape).getVertices().size()-1) ){
                    Vecteur vecteur = new Vecteur(((Polygone) shape).getVertices().get(i + 1).getX() - ((Polygone) shape).getVertices().get(i).getX(),((Polygone) shape).getVertices().get(i + 1).getY() - ((Polygone) shape).getVertices().get(i).getY());
                    vecteur=vecteurUnitaireVecteur(vecteur);
                    myList.add(vecteur);
                }
                else{
                    Vecteur vecteur = new Vecteur(((Polygone) shape).getVertices().get(0).getX() - ((Polygone) shape).getVertices().get(i).getX(),((Polygone) shape).getVertices().get(0).getY() - ((Polygone) shape).getVertices().get(i).getY());
                    vecteur=vecteurUnitaireVecteur(vecteur);
                    myList.add(vecteur);
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
        if (nbVoile != 0)
            return ((double)nbVoileOuverte/nbVoile)*wind.getStrength()*Math.cos(Math.abs(wind.getOrientation()-bateau.getPosition().getOrientation()));
        else return 0;
    }
}
