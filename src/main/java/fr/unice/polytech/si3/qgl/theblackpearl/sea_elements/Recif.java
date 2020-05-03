package fr.unice.polytech.si3.qgl.theblackpearl.sea_elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

@JsonTypeName("reef")
public class Recif extends VisibleEntity {

    @JsonCreator
    Recif(@JsonProperty("type") String type, @JsonProperty("position") Position position, @JsonProperty("shape") Shape shape){
        super(type, position,shape);
    }

    @Override
    public String toString() {
        return "Recif{ "+
                " position=" + position +
                ", shape=" + shape +
                '}';
    }
}
