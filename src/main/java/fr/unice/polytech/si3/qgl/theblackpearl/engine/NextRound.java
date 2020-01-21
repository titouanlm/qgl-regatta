package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

public class NextRound {
    private final Bateau ship;
    @JsonIgnore
    private final Vent wind;
    @JsonIgnore
    private SeaElement visibleEntities;

    @JsonCreator
    NextRound(@JsonProperty("ship") Bateau ship, @JsonProperty("wind") Vent wind,
              @JsonProperty("visibleEntities") SeaElement visibleEntities){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    public Bateau getbBateau() {
        return this.ship;
    }
}
