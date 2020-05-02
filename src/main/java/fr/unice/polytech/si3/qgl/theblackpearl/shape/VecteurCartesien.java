package fr.unice.polytech.si3.qgl.theblackpearl.shape;

public class VecteurCartesien {

    private double x,y;

    public VecteurCartesien(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "VecteurCartesien{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
