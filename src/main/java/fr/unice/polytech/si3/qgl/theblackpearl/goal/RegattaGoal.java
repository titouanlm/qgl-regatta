package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import com.fasterxml.jackson.annotation.*;

import java.util.Arrays;

@JsonTypeName("REGATTA")
public class RegattaGoal extends Goal {
    private Checkpoint[] checkpoints;

    @JsonCreator
    public RegattaGoal(@JsonProperty("mode") String mode,@JsonProperty("checkpoints") Checkpoint[] checkpoints) {
        super(mode);
        this.checkpoints = checkpoints;
    }

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Checkpoint[] checkpoints) {
        this.checkpoints = checkpoints;
    }

    @Override
    public String toString() {
        return "RegattaGoal{" +
                "checkpoints=" + Arrays.toString(checkpoints) +
                '}';
    }
}
