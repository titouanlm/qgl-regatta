package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovingTest.class, name = "MOVING"),
        @JsonSubTypes.Type(value = OarTest.class, name = "OAR"),
        @JsonSubTypes.Type(value = TurnTest.class, name = "TURN"),
})
public class ActionRound {
    protected int sailorId;
    protected String type;

    public int getSailorId() {
        return sailorId;
    }

    public String getType() {
        return type;
    }
}
