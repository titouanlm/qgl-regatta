package fr.unice.polytech.si3.qgl.theblackpearl.sea_elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

@JsonTypeName("stream")
public class Courant extends VisibleEntity {
    private double strength;
    @JsonCreator
    protected Courant(@JsonProperty("type") String type,@JsonProperty("position") Position position,
                      @JsonProperty("shape") Shape shape,@JsonProperty("strength") double strength) {
        super(type, position, shape);
        this.strength = strength;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

}
