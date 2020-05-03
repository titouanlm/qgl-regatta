package fr.unice.polytech.si3.qgl.theblackpearl.shape;

public class VecteurPolaire {

    private double longeur;
    private double angle;

    public VecteurPolaire(double longeur,double angle){
        this.longeur=longeur;
        this.angle=angle;
    }

    public double getLongeur() {
        return longeur;
    }

    public void setLongeur(double longeur) {
        this.longeur = longeur;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "VecteurPolaire{" +
                "longeur=" + longeur +
                ", angle=" + angle +
                '}';
    }
}
