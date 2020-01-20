package fr.unice.polytech.si3.qgl.theblackpearl.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("rectangle")
public class Rectangle extends Shape {
    private double width;
    private double height;
    private double orientation;

    @JsonCreator
    public Rectangle(@JsonProperty("type") String type,@JsonProperty("width") double width,
                     @JsonProperty("height") double height, @JsonProperty("orientation") double orientation) {
        super(type);
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

    public double getLength() {
        return height;
    }

    public void setLength(double length) {
        this.height = length;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}

