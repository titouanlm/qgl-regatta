package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Goal;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

public class Game {
    private final Goal goal;
    private final int shipCount;
    private final Bateau ship;
    private final Marin[] sailors;

    @JsonCreator
    Game(@JsonProperty("goal") Goal goal, @JsonProperty("shipCount") int numberOfShips, @JsonProperty("ship") Bateau ship, @JsonProperty("sailors") Marin[] sailors){
        this.goal = goal;
        this.shipCount  = numberOfShips;
        this.ship = ship;
        this.sailors = sailors;
    }


    public Bateau getbBateau() {
        return ship;
    }
}
