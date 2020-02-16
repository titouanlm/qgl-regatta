package fr.unice.polytech.si3.qgl.theblackpearl.ship.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Gouvernail extends Entity{

    @JsonCreator
    public Gouvernail(@JsonProperty("x") int x,@JsonProperty("y") int y) {
        super(x, y);
        type = "rudder";
    }
    private double angleRealise;

    public double getAngleRealise() {
        return angleRealise;
    }

    public void setAngleRealise(double angleRealise) {
        this.angleRealise = angleRealise;
    }

    public double angleGouvernail(double angleAFaire) {
        if (angleAFaire == 0) return 0.0;
        else if ((angleAFaire == Math.PI/2) || (angleAFaire == Math.PI)) return 45 * Math.PI / 180;
        else if (angleAFaire == -Math.PI/2) return -45 * Math.PI / 180;
        else if (angleAFaire > 0 && angleAFaire > 45 * Math.PI / 180) return 45 * Math.PI / 180;
        else if (angleAFaire > 0) return angleAFaire % (45 * Math.PI / 180);
        else if (angleAFaire < 0 && angleAFaire < -45 * Math.PI / 180) return -45 * Math.PI / 180;
        else return -angleAFaire % (45 * Math.PI / 180);
    }




}
