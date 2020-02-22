package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Gouvernail;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Voile;

import java.util.List;

public class Marin {
    private int id;
    private int x;
    private int y;
    private String name;
    private boolean libre;
    private String actionAFaire="";
    private boolean canMove;

    @JsonCreator
    public Marin(@JsonProperty("id") int id,@JsonProperty("x") int x,@JsonProperty("y") int y,@JsonProperty("name") String name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.libre=true;
        this.canMove=true;
    }

    public void resetMarinPourUnNouveauTour() {
        this.actionAFaire="";
        this.libre=true;
    }
    public String getActionAFaire(){
        return actionAFaire;
    }

    public MOVING deplacementMarinGouvernail(List<Entity> Entities){
        int deplacementMarin;
        for (Entity entity : Entities) {
            if (entity instanceof Gouvernail) {
                deplacementMarin = (Math.abs(entity.getX() - this.getX()) + Math.abs(entity.getY() - this.getY()));
                if (deplacementMarin < 6 && this.libre) {
                    actionAFaire="tournerGouvernail";
                    this.libre=false;
                    return new MOVING(getId(), "MOVING", entity.getX() - this.getX(), entity.getY() - this.getY());
                }
            }
        }
        return null;
    }

    public MOVING deplacementMarinVoile(List<Entity> Entities, boolean leverLaVoile){
        int deplacementMarin=0;
        for (Entity entity : Entities) {
            if (entity instanceof Voile) {
                deplacementMarin = (Math.abs(entity.getX() - this.getX()) + Math.abs(entity.getY() - this.getY()));
                if (deplacementMarin < 6 && this.libre) {
                    if (leverLaVoile) actionAFaire="HisserVoile";
                    else actionAFaire="BaisserLaVoile";
                    this.libre=false;
                    return new MOVING(getId(), "MOVING", entity.getX() - this.getX(), entity.getY() - this.getY());
                }
            }
        }
        return null;
    }

    public MOVING deplacementMarinAllerRamer(List<Entity> Entities, int nombreDeMarinsManquantsAGauche, int nombreDeMarinsManquantsADroite, int largeurBateau){
        int entiteRecoitMarin=-1;
        int deplacementMarin;
        int deplacementPlusCourt=6;
        for (int i=0;i<Entities.size();i++) {
            if (Entities.get(i) instanceof Rame) {
                deplacementMarin = (Math.abs(Entities.get(i).getX() - this.getX()) + Math.abs(Entities.get(i).getY() - this.getY()));
                if (nombreDeMarinsManquantsAGauche > 0 && Entities.get(i).getY() == 0) { // la gauche du bateau est à y = 0
                    if (deplacementMarin < 6 && this.libre && deplacementMarin < deplacementPlusCourt) {
                        entiteRecoitMarin = i;
                        deplacementPlusCourt = deplacementMarin;
                    }
                } else if (nombreDeMarinsManquantsADroite > 0 && Entities.get(i).getY() == largeurBateau - 1 /*ici normalement */) {
                    if (deplacementMarin < 6 && this.libre && deplacementMarin < deplacementPlusCourt) {
                        entiteRecoitMarin = i;
                        deplacementPlusCourt = deplacementMarin;
                    }
                }
            }
        }
        if (entiteRecoitMarin!=-1) {
            this.libre=false;
            this.actionAFaire="Ramer";
            return new MOVING(getId(),"MOVING",Entities.get(entiteRecoitMarin).getX() - this.getX(),Entities.get(entiteRecoitMarin).getY() - this.getY());

        }
        else
            return null;
    }


    public boolean isLibre(){
        return libre;
    }

    public void setLibre(boolean libre){
        this.libre=libre;
    }

    public int getId() {
        return id;
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


    @Override
    public String toString() {
        return "Marin{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void moveSailor(int xdistance, int ydistance) {
        this.setX(this.x+xdistance);
        this.setY(this.y+ydistance);
        this.canMove=false;
    }

    public void setCanMove(boolean b) {
        this.canMove=b;
    }

    public boolean canMove() {
        return canMove;
    }

}
