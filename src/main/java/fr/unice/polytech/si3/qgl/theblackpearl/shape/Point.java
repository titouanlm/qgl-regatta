package fr.unice.polytech.si3.qgl.theblackpearl.shape;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {
    private double x, y;

    public Point(@JsonProperty("x") double a, @JsonProperty("y")double b) {
        this.x = a;
        this.y = b;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point(" +
                  x +
                ", " + y +
                ')';
    }
}
