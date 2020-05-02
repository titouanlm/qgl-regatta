package fr.unice.polytech.si3.qgl.theblackpearl.sea_elements;

import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;

public class AutreBateau extends VisibleEntity {
    private int life;

    protected AutreBateau(String type, Position position, Shape shape,int life) {
        super(type, position, shape);
        this.life = life;
    }

    public int getLife() {
        return life;
    }
}
