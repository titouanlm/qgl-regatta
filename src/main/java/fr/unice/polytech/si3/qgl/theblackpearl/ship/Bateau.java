package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonTypeName("ship")
public class Bateau {

    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private ArrayList<Entity> entities;
    private Shape shape;
    public double orientationBateau;

    @JsonCreator
    public Bateau(@JsonProperty("type") String type, @JsonProperty("life") int life, @JsonProperty("position")  Position position,
                  @JsonProperty("name")  String name, @JsonProperty("deck") Deck deck, @JsonProperty("entities") ArrayList<Entity> entities,
                  @JsonProperty("shape")  Shape shape, @JsonProperty("orientation")  double orientation) {

        this.type = type;
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
        this.orientationBateau=orientation;
    }

    public String getType() {
        return type;
    }

    public void setType(String aType) {
        type = aType;
    }

    public int getLife() {
        return life;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Rame> getListRames(){
        ArrayList<Rame> listRames = new ArrayList<>();
        for (Entity c : getEntities()){
            if (c instanceof Rame){
                listRames.add((Rame) c);
            }
        }
        return listRames;
    }

    public double[] anglesPossibles(int nombreMarins) { // certains angles ne peuvent pas être réalisé s'il n'y a pas assez de marins
        // voir si les marins peuvent se rendre jusqu'a la rame

        double angle[] = new double[nombreMarins == 1 ? 2 : (getListRames().size() <= nombreMarins ? getListRames().size() + 1 : nombreMarins + 1)];
        if (nombreMarins == 1) {
            angle[0] = (Math.PI/2) * (double) (1 / getListRames().size()) * (double) (2);
            angle[1] = - angle[0] ;
        }
        else {
            for (int i = 0; i < getListRames().size()+1; i++) { // je mets tout les angles possibles dans un tableau
                angle[i] = (Math.PI / 2) - (Math.PI / getListRames().size())*i ;
            }
        }
        return angle;
    }

    // à faire
    public int[] nombreMarinsBabordTribord(double angle, int nombreMarins, ArrayList<Rame> nombreRames){
        double angleCalcule=0;
        int i;
        for (i=-nombreRames.size()/2;angleCalcule!=angle;i++){
            angleCalcule = (Math.PI/nombreRames.size())*i;
        }
        i--;
        int nombreMarinsBabordTribord[] = new int[2];
        int b=0;
        if (i>0) {
            for (; i < (nombreRames.size() / 2); i++) {
                i += 1;
                b += 1;
            }
            nombreMarinsBabordTribord[0]=b;
            nombreMarinsBabordTribord[1]=i;

            return nombreMarinsBabordTribord;
        }
        if (i<0){
            i=-i;
            for (; i < (nombreRames.size() / 2); i++) {
                i += 1;
                b += 1;
            }
            nombreMarinsBabordTribord[0]=i;
            nombreMarinsBabordTribord[1]=b;

            return nombreMarinsBabordTribord;
        }
        return null;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Bateau{" +
                "type='" + type + '\'' +
                ", life=" + life +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", deck=" + deck +
                ", entities=" + Arrays.toString(entities.toArray()) +
                ", shape=" + shape +
                '}';
    }
}
