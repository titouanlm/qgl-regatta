package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

public class Deck {
    private int width;
    private int length;

    @JsonCreator
    public Deck(@JsonProperty("width") int width, @JsonProperty("length") int length) {
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isStarboard(Entity entity){ // Starboard = tribord = droite
        if(entity.getX() >= 0 && entity.getX() < length) { // on vérifie que l'entité est bien sur le bateau
            if (width % 2 == 0) {
                if (entity.getY() >= width / 2 && entity.getY() < width) return true;
            } else if (width % 2 == 1) {
                if (entity.getY() > width / 2 && entity.getY() < width) return true;

            }
        }
        return false;
    }

    public boolean isPort(Entity entity){ // Port = bâbord = gauche
        if(entity.getX() >= 0 && entity.getX() < length){ // on vérifie que l'entité est bien sur le bateau
            if(entity.getY() >= 0 && entity.getY() < width/2) return true; // on vérifie s'il est à bâbord
        }
        return false;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "width=" + width +
                ", length=" + length +
                '}';
    }
}
