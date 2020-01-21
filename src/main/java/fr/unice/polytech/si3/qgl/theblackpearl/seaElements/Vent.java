package fr.unice.polytech.si3.qgl.theblackpearl.seaElements;

public class Vent {
    private double orientation;
    private double strength;

    public Vent(double orientation, double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }

    public double getOrientation() {
        return orientation;
    }

    public double getStrength() {
        return strength;
    }
}
