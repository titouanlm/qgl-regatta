package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import java.util.List;

public class RegattaGoal extends Goal {
    private List<Checkpoint> checkpoints;

    public RegattaGoal(String mode, List<Checkpoint> checkpoints) {
        super(mode);
        this.checkpoints = checkpoints;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }
}
