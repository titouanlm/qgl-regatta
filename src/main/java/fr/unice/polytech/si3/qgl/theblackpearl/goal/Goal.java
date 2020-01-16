package fr.unice.polytech.si3.qgl.theblackpearl.goal;

public abstract class Goal {
    private String mode;

    Goal(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
