package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Deck {
    private int width;
    private int length;

    @JsonCreator
    public Deck(@JsonProperty("width") int width, @JsonProperty("length") int length) {
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
