package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

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

        double angle[] = new double[nombreMarins == 1 ? 2 : (getListRames().size() <= nombreMarins ? getListRames().size() * 2 + 1 : nombreMarins * 2 + 1)];
        if (nombreMarins == 1) {
            angle[0] = (Math.PI/2) * (double) (1 / getListRames().size()) * (double) (1/2);
            angle[1] = - angle[0] ;
        }
        else {
            angle[getListRames().size() / 2] = 0.0;
            for (int i = 0; i < getListRames().size() / 2; i++) { // je met tout les angles possibles dans un tableau
                angle[i] = (Math.PI / 2) * (double) ((i + 1) / getListRames().size()) * (double) (1 / 2);
                angle[i + getListRames().size() / 2 + 1] = -(Math.PI / 2) * (double) ((i + 1) / getListRames().size()) * (double) (1 / 2);
            }
        }
        return angle;
    }

    // à faire
    public int[] nombreMarinsBabordTribord(ArrayList<Checkpoint> listCheckpoints, int nombreMarins){  // pour un angle donné donne une configuration de marin possible
                                                                                                        // si la configuraiton est impossible return null
                                                                                                        // plusieurs facons de réaliser l'angle -> influt sur la vitesse
                                                                                                        // voir ce cas dans oe cockpit
        if (listCheckpoints.size() == 0 )  return new int[]{0, 0};  //la course est fini plus de checkpoints à faire
        double checkpointX = listCheckpoints.get(0).getPosition().getX();
        double checkpointY = listCheckpoints.get(0).getPosition().getY();
        int nombreSousEtapes = 50;
        //int vitesseBateau = 165 * getListRames().size()/getListRames().size(); // vitesse change
        //double tableauDistance[][] = new double[anglesPossibles().length][2];
        double positionApresTour[][] = new double[anglesPossibles(nombreMarins).length][2]; // jusqu'au checkpoint
        for (int i=0;i<anglesPossibles(nombreMarins).length;i++){
            for (int n=1; n<=50;n++){
                positionApresTour[i][0] = ;
                positionApresTour[i][1] = ;
            }
        }
        //faire une fonction dans la classe position qui prend un tableau et retourne l'indice du tableau ou la distance est la plus courte
        // jusqu'au checkpoint
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
