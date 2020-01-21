package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Goal;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.List;

public class Game {
    private final Goal goal;
    private final int shipCount;
    private final Bateau ship;


    private final List<Marin> sailors;

    @JsonCreator
    Game(@JsonProperty("goal") Goal goal, @JsonProperty("shipCount") int numberOfShips, @JsonProperty("ship") Bateau ship, @JsonProperty("sailors") List<Marin> sailors){
        this.goal = goal;
        this.shipCount  = numberOfShips;
        this.ship = ship;
        this.sailors = sailors;
    }


    public Bateau getbBateau() {
        return this.ship;
    }

    public List<Marin> getSailors() {
        return sailors;
    }


}
