package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = false
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Gouvernail.class, name = "rudder"),
        @JsonSubTypes.Type(value = Rame.class, name = "oar"),
        @JsonSubTypes.Type(value = Vigie.class, name = "watch"),
        @JsonSubTypes.Type(value = Voile.class, name = "sail"),
})
@JsonIgnoreProperties(value = { "isUsed" , "used", "type", "angleRealise"})
public abstract class Entity {
    protected String type;
    private int x;
    private int y;
    protected boolean libre = true;

    @JsonCreator
    public Entity(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public static ArrayList<Entity> supprimerEntite(ArrayList<Entity> listeEntite, boolean marinPlaceGauche, boolean marinPlaceDroite, Marin m, MOVING moving){
        if (listeEntite!=null) {
            for (int b = 0; b < listeEntite.size(); b++) { // supprimer la rame utilisée pour cette configuration
                if (listeEntite.get(b) instanceof Rame && (marinPlaceGauche || marinPlaceDroite)) {
                    if ((listeEntite.get(b).getY() - m.getY()) == moving.getYdistance() && (listeEntite.get(b).getX() - m.getX()) == moving.getXdistance()) {
                        // TODO mettre l'attribut libre de Rame sur false
                        listeEntite.remove(b);
                        break;
                    }
                }
            }
            return listeEntite;
        }
        else return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
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

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x : " + this.x +", y : " + this.y;
    }
}
