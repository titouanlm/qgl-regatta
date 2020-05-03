package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Sailor;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = false
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rudder.class, name = "rudder"),
        @JsonSubTypes.Type(value = Oar.class, name = "oar"),
        @JsonSubTypes.Type(value = Watch.class, name = "watch"),
        @JsonSubTypes.Type(value = Sail.class, name = "sail"),
})
@JsonIgnoreProperties(value = { "isUsed" , "used", "type", "angleRealise"})
public abstract class Entity {
    protected String type;
    private int x;
    private int y;
    @JsonIgnore
    private boolean libre;
    @JsonCreator
    public Entity(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
        this.libre=true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLibre(){
        return libre;
    }

    public void setLibre(boolean libre){
        this.libre=libre;
    }

    @Override
    public String toString() {
        return "x : " + this.x +", y : " + this.y;
    }

    public static ArrayList<Entity> supprimerEntite(ArrayList<Entity> listeEntite, boolean marinPlaceGauche, boolean marinPlaceDroite, Sailor m, MOVING moving){
        if (listeEntite!=null) {
            for (int b = 0; b < listeEntite.size(); b++) { // supprimer la rame utilisée pour cette configuration
                if (listeEntite.get(b) instanceof Oar && (marinPlaceGauche || marinPlaceDroite)) {
                    if ((listeEntite.get(b).getY() - m.getY()) == moving.getYdistance() && (listeEntite.get(b).getX() - m.getX()) == moving.getXdistance()) {
                        listeEntite.remove(b);
                        break;
                    }
                }
            }
            return listeEntite;
        }
        else return null;
    }
}
