package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Circle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

import java.util.Arrays;
import java.util.List;

@JsonTypeName("REGATTA")
public class RegattaGoal extends Goal {
    private List<Checkpoint> checkpoints;

    @JsonCreator
    public RegattaGoal(@JsonProperty("mode") String mode,@JsonProperty("checkpoints") List<Checkpoint> checkpoints) {
        super(mode);
        this.checkpoints = checkpoints;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void removeCheckpoint() {
        this.checkpoints.remove(0);
    }

    @Override
    public String toString() {
        return "RegattaGoal{" +
                "checkpoints=" + checkpoints +
                '}';
    }

    public boolean shipIsInsideCheckpoint(Bateau bateau) {
        if(checkpoints.get(0)!=null){
            double x = bateau.getPosition().getX()-checkpoints.get(0).getPosition().getX();
            double y = bateau.getPosition().getY()-checkpoints.get(0).getPosition().getY();
            double distanceCBCC = Math.sqrt((x*x)+(y*y));
            if(checkpoints.get(0).getShape() instanceof Circle){
                Circle circle = (Circle) checkpoints.get(0).getShape();
                return distanceCBCC <= circle.getRadius();
            }else{
                Rectangle rectangle = (Rectangle) checkpoints.get(0).getShape();
                return false;
            }
        }
        return false;
    }
}
