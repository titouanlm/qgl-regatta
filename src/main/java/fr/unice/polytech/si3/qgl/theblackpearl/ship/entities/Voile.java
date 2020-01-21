package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entitie;

public class Voile extends Entitie implements Actions{
    public Voile(String type, int x, int y) {
        super(type, x, y);
    }

    public void doAction(Marin marin){}
}
