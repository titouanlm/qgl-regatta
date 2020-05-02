package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

import java.util.ArrayList;


@JsonTypeName("oar")
public class Rame extends Entity{


    private boolean isUsed;

    public int getNombreMarinsPouvantDeplacerIci() {
        return nombreMarinsPouvantDeplacerIci;
    }

    public void setNombreMarinsPouvantDeplacerIci(int nombreMarinsPouvantDeplacerIci) {
        this.nombreMarinsPouvantDeplacerIci = nombreMarinsPouvantDeplacerIci;
    }

    private int nombreMarinsPouvantDeplacerIci=9999;

    @JsonCreator
    public Rame(@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(x, y);
        type = "oar";
        this.isUsed=false;
    }

    @Override
    public String toString() {
        return "Rame{" +
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
