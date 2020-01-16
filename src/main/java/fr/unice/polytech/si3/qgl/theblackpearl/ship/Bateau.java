package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entitie;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

import java.util.List;

public class Bateau {
    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private List<Entitie> entities;
    private Shape shape;

    public Bateau(String type, int life, Position position, String name, Deck deck, List<Entitie> entities, Shape shape) {
        this.type = type;
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
    }

    public String getType() {
        return type;
    }

    public int getLife() {
        return life;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Entitie> getEntities() {
        return entities;
    }

    public Shape getShape() {
        return shape;
    }
}
