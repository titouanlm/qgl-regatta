package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import com.fasterxml.jackson.annotation.*;

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
}
