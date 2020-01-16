package fr.unice.polytech.si3.qgl.theblackpearl.shape;

public class Rectangle extends Shape {
    private double width;
    private double length;
    private double orientation;

    public Rectangle(String type, double width, double length, double orientation) {
        super(type);
        this.width = width;
        this.length = length;
        this.orientation = orientation;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}

