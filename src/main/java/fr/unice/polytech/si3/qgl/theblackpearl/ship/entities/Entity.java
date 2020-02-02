package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.BattleGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Gouvernail.class, name = "rudder"),
        @JsonSubTypes.Type(value = Rame.class, name = "oar"),
        @JsonSubTypes.Type(value = Vigie.class, name = "watch"),
        @JsonSubTypes.Type(value = Voile.class, name = "sail"),
})

public abstract class Entity {
    private String type;
    private int x;
    private int y;
    private boolean libre = true;

    @JsonCreator
    public Entity(@JsonProperty("type") String type, @JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.type = type;
        this.x = x;
        this.y = y;
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
