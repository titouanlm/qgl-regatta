package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Goal;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.ArrayList;


public class InitGame {
    private Goal goal;
    private int shipCount;
    private Bateau ship;
    private ArrayList<Marin> sailors;

    @JsonCreator
    public InitGame(@JsonProperty("goal") Goal goal, @JsonProperty("shipCount") int shipCount, @JsonProperty("ship") Bateau ship, @JsonProperty("sailors") ArrayList<Marin> sailors){
        this.goal = goal;
        this.shipCount  = shipCount;
        this.ship = ship;
        this.sailors = sailors;
    }

    public Bateau getBateau() {
        return this.ship;
    }

    public Goal getGoal(){
        return this.goal;
    }

    public void setBateau(Bateau ship) {
        this.ship = ship;
    }

    public ArrayList<Marin> getMarins() {
        return sailors;
    }

    public void setMarins(ArrayList<Marin> sailors) {
        this.sailors = sailors;
    }

    @Override
    public String toString() {
        return "Game{" +
                "goal=" + goal +
                ", shipCount=" + shipCount +
                ", ship=" + ship +
                ", sailors=" + sailors +
                '}';
    }


    public InitGame clone(){
        Goal cloneGoal;
        if(goal instanceof RegattaGoal){
            cloneGoal = ((RegattaGoal) goal).clone();
        }else{
            cloneGoal = goal;
        }
        Bateau cloneBateau = ship.clone();
        ArrayList<Marin> cloneSailors = new ArrayList<>();
        for(Marin m : sailors){
            cloneSailors.add(m.clone());
        }
        return new InitGame(cloneGoal, shipCount, cloneBateau, cloneSailors);
    }

}
