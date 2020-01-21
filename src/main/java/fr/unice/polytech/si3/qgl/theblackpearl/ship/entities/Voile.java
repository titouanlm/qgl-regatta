package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

public class Voile extends Entity{
    private boolean openned;
    public Voile(String type, int x, int y, boolean openned) {
        super(type, x, y);
        this.openned=openned;
    }

}
