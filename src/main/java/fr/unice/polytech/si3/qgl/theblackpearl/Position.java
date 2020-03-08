package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private double x;
    private double y;
    private double orientation;

    @JsonCreator
    public Position(@JsonProperty("x") double x,@JsonProperty("y") double y, @JsonProperty("orientation")double orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
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

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }
}
