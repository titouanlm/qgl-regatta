package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import fr.unice.polytech.si3.qgl.theblackpearl.Marin;

public class Voile extends Entity implements Actions{
    public Voile(String type, int x, int y) {
        super(type, x, y);
    }

    public void doAction(Marin marin){}
}
