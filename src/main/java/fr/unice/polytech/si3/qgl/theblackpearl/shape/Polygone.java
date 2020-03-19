package fr.unice.polytech.si3.qgl.theblackpearl.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Arrays;


@JsonTypeName("polygon")
public class Polygone extends Shape {
    private double orientation;
    private ArrayList<Point> vertices;


    @JsonCreator
    public Polygone(@JsonProperty("orientation") double orientation, @JsonProperty("vertices") ArrayList<Point> vertices) {
        this.type = "polygon";
        this.orientation = orientation;
        this.vertices = vertices;
    }

    public double getOrientation() {
        return orientation;
    }

    public ArrayList<Point> getVertices(){ return vertices; }


    @Override
    public String toString() {
        return "Polygone{" +
                "orientation=" + orientation +
                ", vertices=" + Arrays.toString(vertices.toArray()) +
                '}';
    }

}

