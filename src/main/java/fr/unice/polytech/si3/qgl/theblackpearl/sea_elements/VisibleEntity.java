package fr.unice.polytech.si3.qgl.theblackpearl.sea_elements;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutreBateau.class, name = "ship"),
        @JsonSubTypes.Type(value = Recif.class, name = "reef"),
        @JsonSubTypes.Type(value = Courant.class, name = "stream")
})

// either our ship, our enemies' ship, a reef or the stream of the sea

public abstract class VisibleEntity {
    protected String type;
    protected Position position;
    protected Shape shape;

    @JsonCreator
    VisibleEntity(@JsonProperty("type") String type, @JsonProperty("position") Position position, @JsonProperty("shape") Shape shape){
        this.type = type;
        this.position = position;
        this.shape = shape;
    }

    public Shape getShape(){
        return shape;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
