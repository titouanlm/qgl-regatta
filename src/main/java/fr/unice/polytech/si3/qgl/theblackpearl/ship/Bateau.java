package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@JsonTypeName("ship")
public class Bateau {

    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private ArrayList<Entity> entities;
    private Shape shape;

    @JsonCreator
    public Bateau(@JsonProperty("type") String type, @JsonProperty("life") int life, @JsonProperty("position")  Position position,
@JsonProperty("name")  String name, @JsonProperty("deck") Deck deck, @JsonProperty("entities") ArrayList<Entity> entities, @JsonProperty("shape")  Shape shape) {
        this.type = type;
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
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

    public int[] nombreMarinsBabordTribord(double angle, int nombreMarins, ArrayList<Rame> nombreRames){
        double angleCalcule=-(Math.PI/2)-Math.PI/nombreRames.size();
        int i;
        for (i=-nombreRames.size()/2; ;i++){
            angleCalcule += Math.PI/nombreRames.size();
            if (angleCalcule>=(angle - Math.pow(5.0, -6.0))  && angleCalcule<=(angle + Math.pow(5.0, -6.0))){
                break;
            }
            if (i>nombreRames.size()/2) return null;
        }
        int nombreMarinsBabordTribord[] = new int[2];
        int b=0;
        if (i>0) {
            for (; i < (nombreRames.size() / 2); ) {
                i += 1;
                b += 1;
            }
            nombreMarinsBabordTribord[0]=b;
            nombreMarinsBabordTribord[1]=i;
        }
        else if (i<0){
            i=-i;
            for (; i < (nombreRames.size() / 2); ) {
                i += 1;
                b += 1;
            }
            nombreMarinsBabordTribord[0]=i;
            nombreMarinsBabordTribord[1]=b;
        }
        else {
            i=nombreRames.size()/2;
            b=nombreRames.size()/2;
            nombreMarinsBabordTribord[0]=i;
            nombreMarinsBabordTribord[1]=b;
        }
        return nombreMarinsBabordTribord;
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

    public List<Double> anglesPossibles() {
        List<Double> anglesRealisables = new ArrayList<>();
        int nbRames = getNbRame();
        for( int i=1 ; i<=nbRames/2 ; i++){
            anglesRealisables.add(i*Math.PI/nbRames);
            anglesRealisables.add(i*-Math.PI/nbRames);
        }
        anglesRealisables.add(0.0);
        return anglesRealisables;
    }

    public List<Double> meilleurAngleRealisable(double angleIdealVersCheckpoint){
        List<Double> meilleursAnglesRealisables = anglesPossibles();

        meilleursAnglesRealisables.sort(Comparator.comparingDouble( angle -> Math.abs(angleIdealVersCheckpoint-angle)));

        return meilleursAnglesRealisables;
    }

    public int getNbRame() {
        int nbRame =0;
        for(Entity e : getEntities()){
            if(e instanceof Rame){
                nbRame++;
            }
        }
        return nbRame;
    }

    public void initRameUsed(List<Marin> marins){
        for(Entity e : entities) {
            if(e instanceof Rame){
                for (Marin m : marins) {
                    if (m.getX() == e.getX() && m.getY() == e.getY() ) {
                        ((Rame) e).setLibre(false);
                    }
                }
            }
        }
    }

    public List<Action> allerToutDroit(List<Marin> marins) {
        int nbMarinsTribord = nbMarinRameTribord(marins);
        int nbMarinsBabord  = nbMarinRameBabord(marins);
        List<Action> actions = new ArrayList<>();

        if(nbMarinsTribord==nbMarinsBabord){
            for(Marin m : marins){
                actions.add(new OAR(m.getId()));
            }
        }
        return actions;
    }

    public int nbMarinRameTribord(List<Marin> marins){
        int nbMarinRameTribord=0;
        for(Marin m : marins){
            for(Entity e : entities){
                if(e instanceof Rame && e.getY()==this.getDeck().getWidth()-1 && m.getX() == e.getX() && m.getY() == e.getY()){
                    nbMarinRameTribord++;
                }
            }
        }
        return nbMarinRameTribord;
    }

    public int nbMarinRameBabord(List<Marin> marins){
        int nbMarinRameBabord=0;
        for(Marin m : marins){
            for(Entity e : entities){
                if(e instanceof Rame && e.getY()==0 && m.getX() == e.getX() && m.getY() == e.getY()){
                    nbMarinRameBabord++;
                }
            }
        }
        return nbMarinRameBabord;
    }

}
