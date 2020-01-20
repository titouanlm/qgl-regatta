package fr.unice.polytech.si3.qgl.theblackpearl.goal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("BATTLE")
public class BattleGoal extends Goal {
    @JsonCreator
    public BattleGoal(@JsonProperty("mode") String mode) {
        super(mode);
    }
}
