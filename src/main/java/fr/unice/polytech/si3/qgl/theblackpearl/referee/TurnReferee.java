package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Sailor;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

public class TurnReferee extends ActionRound {
    private double rotation;

    public TurnReferee(@JsonProperty("sailorId") int sailorId, @JsonProperty("rotation") double rotation) {
        this.type = "TURN";
        this.sailorId=sailorId;
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "TurnTest{" +
                "rotation=" + rotation +
                ", sailorId=" + sailorId +
                ", type='" + type + '\'' +
                '}';
    }

    public double tryToTurn(InitGame parsedInitGameReferee) {
        for(Sailor m : parsedInitGameReferee.getMarins()){
            if(m.getId()==this.getSailorId()){
                if(m.isLibre() && parsedInitGameReferee.getBateau().isOnRudderNotUsed(m) && this.rotation<=Math.PI/4 && this.rotation>=-Math.PI/4){
                    //System.out.println(m.getId()+" peut faire tourner le bateau de " + this.rotation);
                    m.setLibre(false);
                    return this.rotation;
                }else{
                    System.out.println("ERREUR : " + m.getId() + " Impossible de tourner.");
                }
                break;
            }
        }
        return 0.0;
    }
}
