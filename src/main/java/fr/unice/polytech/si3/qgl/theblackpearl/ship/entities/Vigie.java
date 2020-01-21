package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import fr.unice.polytech.si3.qgl.theblackpearl.Marin;

public class Vigie extends Entity implements Actions{
    public Vigie(String type, int x, int y) {
        super(type, x, y);
    }

    public void doAction(Marin marin){}
}
