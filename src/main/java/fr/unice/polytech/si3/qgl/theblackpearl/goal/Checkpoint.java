package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

public class Checkpoint {
    private Position position;
    private Shape shape;

    public Checkpoint(Position position, Shape shape) {
        this.position = position;
        this.shape = shape;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
