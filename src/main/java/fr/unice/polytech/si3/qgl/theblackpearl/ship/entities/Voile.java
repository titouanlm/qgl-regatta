package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Voile extends Entity{
    private boolean openned;

    @JsonCreator
    public Voile(@JsonProperty("type") String type,@JsonProperty("x") int x,@JsonProperty("y") int y,@JsonProperty("openned") boolean openned) {
        super(type, x, y);
        this.openned=openned;
    }

    public boolean isOpenned() {
        return openned;
    }
}
