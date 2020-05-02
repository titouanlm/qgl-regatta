package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

@JsonTypeName("MOVING")
public class MovingReferee extends ActionRound {
    private int xdistance;
    private int ydistance;

    @JsonCreator
    public MovingReferee(@JsonProperty("sailorId") int sailorId, @JsonProperty("xdistance") int xdistance, @JsonProperty("ydistance") int ydistance) {
        this.type="MOVING";
        this.sailorId = sailorId;
        this.xdistance=xdistance;
        this.ydistance=ydistance;
    }

    public int getXdistance() {
        return xdistance;
    }

    public int getYdistance() {
        return ydistance;
    }

    @Override
    public String toString() {
        return "MovingTest{" +
                "xdistance=" + xdistance +
                ", ydistance=" + ydistance +
                ", sailorId=" + sailorId +
                ", type='" + type + '\'' +
                '}';
    }

    public void tryToMoveMarin(InitGame parsedInitGameReferee) {
        for(Marin m : parsedInitGameReferee.getMarins()){
            if(m.getId()==this.getSailorId()){
                int x = this.getXdistance()+m.getX();
                int y = this.getYdistance()+m.getY();
                int largeur=parsedInitGameReferee.getBateau().getDeck().getWidth();
                int longueur=parsedInitGameReferee.getBateau().getDeck().getLength();
                if(5>=(Math.abs(this.getXdistance())+Math.abs(this.getYdistance())) && 0<=y && y<=largeur-1 && 0<=x && x<=longueur-1 && m.canMove()){
                    m.moveSailor(this.getXdistance(), this.getYdistance());
                }else{
                    System.out.println("ERREUR : Marin " + m.getId() + "ne peut pas se déplacer");
                    System.out.println("MOVINGX : " + this.getXdistance() + " \nMOVINGY : " + this.getYdistance());
                    System.out.println("XBEFORE : " + m.getX() + " \nYBEFORE : " + m.getY());
                    System.out.println("X : " + x + " \nY : " + y);

                }
                break;
            }
        }

    }
}
