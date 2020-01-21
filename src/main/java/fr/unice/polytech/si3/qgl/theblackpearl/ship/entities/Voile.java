package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entitie;

public class Voile extends Entitie {
    private boolean openned;
    public Voile(String type, int x, int y, boolean openned) {
        super(type, x, y);
        this.openned = openned;
    }
}
