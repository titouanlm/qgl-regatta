package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Goal;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

import java.util.List;

import java.util.Arrays;

public class InitGame {
    private Goal goal;
    private int shipCount;
    private Bateau ship;
    private Marin[] sailors;

    @JsonCreator
    public InitGame(@JsonProperty("goal") Goal goal, @JsonProperty("shipCount") int shipCount, @JsonProperty("ship") Bateau ship, @JsonProperty("sailors") Marin[] sailors){
        this.goal = goal;
        this.shipCount  = shipCount;
        this.ship = ship;
        this.sailors = sailors;
    }

    public Bateau getBateau() {
        return this.ship;
    }

    public void setBateau(Bateau ship) {
        this.ship = ship;
    }

    public Marin[] getMarins() {
        return sailors;
    }

    @Override
    public String toString() {
        return "Game{" +
                "goal=" + goal +
                ", shipCount=" + shipCount +
                ", ship=" + ship +
                ", sailors=" + Arrays.toString(sailors) +
                '}';
    }

    public Marin[] getSailors() {
        return sailors;
    }

}