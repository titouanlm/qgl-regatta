package fr.unice.polytech.si3.qgl.theblackpearl.shape;

public class Circle extends Shape {
    private double radius;

    public Circle(String type, double radius) {
        super(type);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
