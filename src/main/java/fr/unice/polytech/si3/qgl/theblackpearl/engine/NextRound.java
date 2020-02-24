package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.Arrays;

@JsonIgnoreProperties(value = { "bateau"})
public class NextRound {
    @JsonSerialize
    private Bateau ship;
    @JsonSerialize
    private VisibleEntity[] visibleEntities;
    @JsonSerialize
    private Vent wind;

    @JsonCreator
    NextRound(@JsonProperty("ship") Bateau ship, @JsonProperty("visibleEntities") VisibleEntity[] visibleEntities, @JsonProperty("wind") Vent wind){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    public Bateau getBateau() {
        return this.ship;
    }

    public Vent getWind() { return this.wind; }

    public void setShip(Bateau ship) {
        this.ship = ship;
    }

    public void setVisibleEntities(VisibleEntity[] visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    @Override
    public String toString() {
        return "NextRound{" +
                "ship=" + ship +
                ", wind=" + wind +
                ", visibleEntities=" + Arrays.toString(visibleEntities) +
                '}';
    }
}
