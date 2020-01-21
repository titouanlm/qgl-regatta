package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entitie;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

import java.util.List;

@JsonTypeName("ship")
public class Bateau {

    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private List<Entitie> entities;
    private Shape shape;

    @JsonCreator
    public Bateau(@JsonProperty("type") String type, @JsonProperty("life") int life, @JsonProperty("position")  Position position,
                  @JsonProperty("name")  String name, @JsonProperty("deck") Deck deck, @JsonProperty("entities") List<Entitie> entities,
                  @JsonProperty("shape")  Shape shape) {

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

    public void setType(String aType) {
        type = aType;
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

    @Override
    public String toString() {
        return "Bateau{" +
                "type='" + type + '\'' +
                ", life=" + life +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", deck=" + deck +
                ", entities=" + entities +
                ", shape=" + shape +
                '}';
    }
}
