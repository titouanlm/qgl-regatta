package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

import java.util.List;

public class Marin {
    private int id;
    private int x;
    private int y;
    private String name;
    private boolean libre = true;

    @JsonCreator
    public Marin(@JsonProperty("id") int id,@JsonProperty("x") int x,@JsonProperty("y") int y,@JsonProperty("name") String name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Entity planificationMarinAllerRamer(List<Entity> Entities, int nombreDeMarinsManquantsAGauche, int nombreDeMarinsManquantsADroite, int largeurBateau){
        int entiteRecoitMarin=-1;
        int deplacementMarin=0;
        int deplacementPlusCourt=6;
        for (int i=0;i<Entities.size();i++) {
            if (Entities.get(i) instanceof Rame) { //Pour faire ramer les marins
                deplacementMarin = (Math.abs(Entities.get(i).getX() - this.getX()) + Math.abs(Entities.get(i).getY() - this.getY()));
                if (nombreDeMarinsManquantsAGauche > 0 && Entities.get(i).getY() == 0) { // la gauche du bateau est à y = 0
                    if (deplacementMarin < 6 && this.libre && deplacementMarin < deplacementPlusCourt) {
                        entiteRecoitMarin = i;
                        deplacementPlusCourt = deplacementMarin;
                    }
                }
                else if (nombreDeMarinsManquantsADroite> 0 && Entities.get(i).getY() == largeurBateau) {
                    if (deplacementMarin < 6 && this.libre && deplacementMarin < deplacementPlusCourt) {
                        entiteRecoitMarin = i;
                        deplacementPlusCourt = deplacementMarin;
                    }
                }
            }
        }
        if (entiteRecoitMarin!=-1) {
            this.libre=false;
            this.setX(Entities.get(entiteRecoitMarin).getX());
            this.setY(Entities.get(entiteRecoitMarin).getY());
            return Entities.get(entiteRecoitMarin);}
        else
            return null;
    }


    public boolean getLibre(){
        return libre;
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

    @Override
    public String toString() {
        return "Marin{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}