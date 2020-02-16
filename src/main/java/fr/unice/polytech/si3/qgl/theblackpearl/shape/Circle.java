package fr.unice.polytech.si3.qgl.theblackpearl.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("circle")
public class Circle extends Shape {
    private double radius;

    @JsonCreator
    public Circle(@JsonProperty("type") String type,@JsonProperty("radius") double radius) {
        type = "circle";
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "type=" + this.getType() +
                "radius=" + radius +
                '}';
    }
}
