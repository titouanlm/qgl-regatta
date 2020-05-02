package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Voile extends Entity {
    private boolean openned;

    @JsonCreator
    public Voile(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("openned") boolean openned) {
        super(x, y);
        type = "sail";
        this.openned = openned;
    }

    public Voile clone(){
        return new Voile(this.getX(), this.getY(), this.openned);
    }

    public boolean isOpenned() {
        return openned;
    }

    public void setOpenned(boolean isopenned) {
        this.openned = isopenned;
    }

    @Override
    public String toString() {
        return "Voile{" +
                "openned=" + openned +
                '}';
    }
}
