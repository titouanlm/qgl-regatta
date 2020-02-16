package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.Arrays;

@JsonIgnoreProperties(value = { "bateau"})
public class NextRound {
    @JsonSerialize
    private Bateau ship;
    @JsonIgnore
    private Vent wind;
    @JsonIgnore
    private VisibleEntity[] visibleEntities;

    @JsonCreator
    NextRound(@JsonProperty("ship") Bateau ship,@JsonProperty("wind") Vent wind, @JsonProperty("visibleEntities") VisibleEntity[] visibleEntities){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    public Bateau getBateau() {
        return this.ship;
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
