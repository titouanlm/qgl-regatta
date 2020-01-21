package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;

import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.List;

public class NextRound {
    private final Bateau ship;
    @JsonIgnore
    private final Vent wind;
    @JsonIgnore
    private List<VisibleEntity> visibleEntities;

    @JsonCreator
    NextRound(@JsonProperty("ship") Bateau ship,@JsonProperty("wind") Vent wind, @JsonProperty("visibleEntities") List<VisibleEntity> visibleEntities){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    public Bateau getbBateau() {
        return this.ship;
    }
}
