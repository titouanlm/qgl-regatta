package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vigie extends Entity{
    @JsonCreator
    public Vigie(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(x, y);
    }

}
