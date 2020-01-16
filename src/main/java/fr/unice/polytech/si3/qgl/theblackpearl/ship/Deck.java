package fr.unice.polytech.si3.qgl.theblackpearl.ship;

public class Deck {
    private int width;
    private int length;

    public Deck(int width, int length) {
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
