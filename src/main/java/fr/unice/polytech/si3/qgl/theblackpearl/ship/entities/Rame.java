package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("oar")
public class Rame extends Entitie {

    @JsonCreator
    public Rame(@JsonProperty("type") String type,@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(type, x, y);
    }

    @Override
    public String toString() {
        return "Rame{" +
                "x=" + this.getX() +

                "}";
    }
}
