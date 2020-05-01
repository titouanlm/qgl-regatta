package fr.unice.polytech.si3.qgl.theblackpearl.shape;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value = Polygone.class, name = "polygon"),
})
@JsonIgnoreProperties(value = { "type"})
public abstract class Shape {
    protected String type;
    Position coordonneesCentre; // utile que pour le rectangle et le cercle

    public Position getCoordonneesCentre() { return coordonneesCentre; }

    public void setCoordonneesCentre(Position coordonneesCentre) { this.coordonneesCentre = coordonneesCentre; }

    public void setOrientationCentre(double orientationCentre) { this.coordonneesCentre = new Position(coordonneesCentre.getX(),coordonneesCentre.getY(),orientationCentre); }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
