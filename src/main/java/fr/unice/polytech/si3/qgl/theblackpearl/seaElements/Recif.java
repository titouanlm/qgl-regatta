package fr.unice.polytech.si3.qgl.theblackpearl.seaElements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

@JsonTypeName("reef")
public class Recif extends SeaElement {
    @JsonCreator
    Recif(@JsonProperty("type") String type, @JsonProperty("position") Position position, @JsonProperty("shape") Shape shape){
        super(type,position,shape);
    }
}
