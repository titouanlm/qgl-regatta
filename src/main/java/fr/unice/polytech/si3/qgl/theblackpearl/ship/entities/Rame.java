package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

import java.util.ArrayList;

@JsonTypeName("oar")
public class Rame extends Entity{

    private boolean isUsed;

    @JsonCreator
    public Rame(@JsonProperty("type") String type,@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(type, x, y);
        this.isUsed=false;
    }

    @Override
    public String toString() {
        return "Rame{" +
                "type=" + this.getType() + "," +
                "x=" + this.getX() + "," +
                "y=" + this.getY() +
                "}";
    }

    public static void setRamesUsed(InitGame game, ArrayList<Entity> listeEntiteCopie){
        ArrayList<Entity> listeEntiteUtilisees = ((ArrayList<Entity>) game.getBateau().getEntities().clone());
        listeEntiteUtilisees.removeAll(listeEntiteCopie); //rames utilisées
        ArrayList<Entity> listeEntite = game.getBateau().getEntities();
        for (Entity e : listeEntite){
            for (Entity e2 : listeEntiteUtilisees){
                if (e==e2){
                    ((Rame) e).setUsed(true);
                }
            }
        }
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isUsed() {
        return this.isUsed;
    }
}
