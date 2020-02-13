package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("oar")
public class Rame extends Entity{

    private boolean isUsed;

    @JsonCreator
    public Rame(@JsonProperty("type") String type,@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(type, x, y);
        this.isUsed=false;
    }

    @Override
    public String toString() {
        return "Rame{" +
                "type=" + this.getType() + "," +
                "x=" + this.getX() + "," +
                "y=" + this.getY() +
                "}";
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isUsed() {
        return this.isUsed;
    }
}
