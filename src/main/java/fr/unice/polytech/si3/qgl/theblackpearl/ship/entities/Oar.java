package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

import java.util.ArrayList;


@JsonTypeName("oar")
public class Oar extends Entity{

    @JsonCreator
    public Oar(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(x, y);
        type = "oar";
    }

    @Override
    public String toString() {
        return "Oar{" +
                "x=" + this.getX() + "," +
                "y=" + this.getY() +
                "}";
    }


}
