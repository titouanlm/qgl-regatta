package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "mode",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BattleGoal.class, name = "BATTLE"),
        @JsonSubTypes.Type(value = RegattaGoal.class, name = "REGATTA")
})
public abstract class Goal {

    protected String mode;

    private ArrayList<Checkpoint> listCheckpoints;

    @JsonCreator
    Goal(@JsonProperty("mode") String mode, @JsonProperty("checkpoint") ArrayList<Checkpoint> listCheckpoints) {
        this.mode = mode;
        this.listCheckpoints=listCheckpoints;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ArrayList<Checkpoint> getListCheckpoints() {
        return listCheckpoints;
    }



    @Override
    public String toString() {
        return "Goal{" +
                "mode='" + mode + '\'' +
                '}';
    }
}
