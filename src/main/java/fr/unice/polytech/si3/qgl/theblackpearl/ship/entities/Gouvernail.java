package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Gouvernail extends Entity{

    @JsonCreator
    public Gouvernail(@JsonProperty("type")String type,@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(type, x, y);
    }


}
