package fr.unice.polytech.si3.qgl.theblackpearl.action;

import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

public abstract class Action {

    private int sailorId;
    private String action;

    public Action(){}

    public Action(int sailorId, String action) {
        this.sailorId = sailorId;
        this.action = action;
    }

    public abstract Action doAction(Marin m, Entity e);

}
