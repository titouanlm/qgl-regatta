package fr.unice.polytech.si3.qgl.theblackpearl;

public class Marin {
    private int x;
    private int y;
    private int id;
    private String name;

    public Marin( int x, int y, int id, String name) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
