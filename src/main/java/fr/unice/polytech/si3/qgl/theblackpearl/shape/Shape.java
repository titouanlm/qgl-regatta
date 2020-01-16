package fr.unice.polytech.si3.qgl.theblackpearl.shape;

public abstract class Shape {
    private String type;

    Shape(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
