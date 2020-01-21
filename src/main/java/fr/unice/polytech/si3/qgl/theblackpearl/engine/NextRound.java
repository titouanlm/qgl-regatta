package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.Vent;

import fr.unice.polytech.si3.qgl.theblackpearl.action.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.action.actionType;
import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.action.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

import java.util.ArrayList;
import java.util.List;

public class NextRound {
    private final Bateau ship;
    @JsonIgnore
    private final Vent wind;
    @JsonIgnore
    private List<VisibleEntity> visibleEntities;
    private List<Action> actionsToDo = new ArrayList<>();
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
        for(Entity e : parsedGame.getbBateau().getEntities()){
            for(Marin m : parsedGame.getSailors()){
                if(e instanceof Rame){
                    Rame oar = (Rame) e;
                    OAR action = new OAR(m.getId(), actionType.OAR.toString(), oar);
                    actionsToDo.add(action.doAction(m, oar));
                }
            }
        }
    }
}
