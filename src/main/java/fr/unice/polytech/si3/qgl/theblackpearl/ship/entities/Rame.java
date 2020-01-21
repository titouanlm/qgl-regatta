package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;

@JsonTypeName("oar")
public class Rame extends Entitie{

    @JsonCreator
    public Rame(@JsonProperty("type") String type,@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(type, x, y);
    }

    public void doAction(Marin marin){
        if(!marin.hasContributed() && !this.isUsed()) {
            this.setUsed(true);
        }
        else{
            System.out.println("La rame n'a pas été utilisée");
        }
    }
}
