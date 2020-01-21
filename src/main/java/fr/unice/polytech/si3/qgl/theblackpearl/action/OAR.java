package fr.unice.polytech.si3.qgl.theblackpearl.action;

import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

public class OarAction extends Action {



    public OarAction(int sailorId, String action, Rame oar) {
        super(sailorId, action);
    }

    /*public Action doAction(Marin marin, Rame oar){
        if(marin.hasContributed() == false && oar.isUsed() == false && oar.getX() == marin.getX() && oar.getY() == marin.getY()){
            marin.setContributed(true);
            oar.setUsed(true);
            return new OarAction(marin.getId(), actionType.OAR.toString(), oar);
        }
        return null;
    }*/

    public Action doAction(Marin marin, Entity e){
        Rame oar = (Rame) e;
        if(marin.hasContributed() == false && oar.isUsed() == false && oar.getX() == marin.getX() && oar.getY() == marin.getY()){
            marin.setContributed(true);
            oar.setUsed(true);
            return new OarAction(marin.getId(), actionType.OAR.toString(), oar);
        }
        return null;
    }
}
