package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.Vent;

import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.action.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entitie;

import java.util.List;

public class NextRound {
    private final Bateau ship;
    @JsonIgnore
    private final Vent wind;
    @JsonIgnore
    private List<VisibleEntity> visibleEntities;
    private List<Action> actionsToDo;
    private Action newAction;

    @JsonCreator
    NextRound(@JsonProperty("ship") Bateau ship,@JsonProperty("wind") Vent wind, @JsonProperty("visibleEntities") List<VisibleEntity> visibleEntities){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    public Bateau getBateau() {
        return this.ship;
    }

    public void doActions(Game parsedGame){
        for(Entitie e : parsedGame.getbBateau().getEntities()){
            for(Marin m : parsedGame.getSailors()){
                //actionsToDo.add(newAction.doAction(m, (Entitie) e));
            }
        }
    }
}
