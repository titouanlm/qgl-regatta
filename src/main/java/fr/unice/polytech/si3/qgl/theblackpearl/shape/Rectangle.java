package fr.unice.polytech.si3.qgl.theblackpearl.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("rectangle")
public class Rectangle extends Shape {
    private double width;
    private double height;
    private double orientation;
    //private double coordonneesCentre;

    @JsonCreator
    public Rectangle(@JsonProperty("width") double width,
                     @JsonProperty("height") double height, @JsonProperty("orientation") double orientation) {
        this.type = "rectangle";
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "type=" + this.getType() +
                ",width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                '}';
    }
}

