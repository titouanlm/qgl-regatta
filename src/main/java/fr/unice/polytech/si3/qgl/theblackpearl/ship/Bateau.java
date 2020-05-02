package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Gouvernail;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Voile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(value = {"gouvernail","listRames", "nbRame"})
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

    public Position getPosition() {
        return position;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Bateau clone(){
        ArrayList<Entity> cloneEntities = new ArrayList<>();
        for(Entity e : entities){
            if(e instanceof Voile){
                cloneEntities.add(((Voile) e).clone());
            }
            cloneEntities.add(e);
        }
        return new Bateau(this.type, this.life , this.position.clone(), this.name, this.deck, cloneEntities, this.shape);
    }

    public Gouvernail getGouvernail(){
        for (Entity e : this.getEntities()) if (e instanceof Gouvernail) return ((Gouvernail) e);
        return null;
    }

    public List<Rame> getListRames(){
        List<Rame> listRames = new ArrayList<>();
        for (Entity c : getEntities()){
            if (c instanceof Rame){
                listRames.add((Rame) c);
            }
        }
        return listRames;
    }

    public int[] nombreMarinsRamesBabordTribordRames(double angle, List<Rame> nombreRames){
        double angleCalcule=-(Math.PI/2)-Math.PI/nombreRames.size();
        int i;
        for (i=-nombreRames.size()/2; ;i++){
            angleCalcule += Math.PI/nombreRames.size();
            if (angleCalcule>=(angle - Math.pow(5.0, -6.0))  && angleCalcule<=(angle + Math.pow(5.0, -6.0))){
                break;
            }
            if (i>nombreRames.size()/2) return null;
        }
        int[] nombreMarinsBabordTribord = new int[2];
        int b=0;
        if (i>0) {
            while ( i < (nombreRames.size() / 2) ) {
                i += 1;
                b += 1;
            }
            nombreMarinsBabordTribord[0]=b;
            nombreMarinsBabordTribord[1]=i;
        }
        else if (i<0){
            i=-i;
            while ( i < (nombreRames.size() / 2) ) {
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

    public List<Double> anglesPossiblesAvecRames() {
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
        List<Double> meilleursAnglesRealisables = anglesPossiblesAvecRames();

        meilleursAnglesRealisables.sort(Comparator.comparingDouble( angle -> Math.abs(angleIdealVersCheckpoint-angle)));

        return meilleursAnglesRealisables;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    //////////////////////////Referee
    public boolean isOnOarNotUsed(Marin m) {
        for(Entity e: entities){
            if(e instanceof Rame && e.isLibre()){
                if(e.getX()==m.getX() && e.getY()==m.getY()){
                    e.setLibre(false);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOnRudderNotUsed(Marin m) {
        for(Entity e: entities){
            if(e instanceof Gouvernail && e.isLibre()){
                if(e.getX()==m.getX() && e.getY()==m.getY()){
                    e.setLibre(false);
                    return true;
                }
            }
        }
        return false;
    }

    public int nbMarinRameTribord(){
        int nbMarinRameTribord=0;
        for(Entity e : entities){
            if(e instanceof Rame && e.getY()==this.getDeck().getWidth()-1 && !e.isLibre()){
                nbMarinRameTribord++;
            }
        }
        return nbMarinRameTribord;
    }

    public int nbMarinRameBabord(){
        int nbMarinRameBabord=0;
        for(Entity e : entities){
            if(e instanceof Rame && e.getY()==0  && !e.isLibre()){
                nbMarinRameBabord++;
            }
        }
        return nbMarinRameBabord;
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

    public int nbVoile() {
        int nbVoile = 0;
        for(Entity e : getEntities()){
            if(e instanceof Voile){
                nbVoile++;
            }
        }
        return nbVoile;
    }


    public int nbVoileOuverte() {
        int nbVoileOuverte = 0;
        for(Entity e : getEntities()){
            if(e instanceof Voile){
                if(((Voile) e).isOpenned()){
                    nbVoileOuverte++;
                }
            }
        }
        return nbVoileOuverte;
    }

    public boolean isOnSailNotUsedNotOppened(Marin m) {
        for(Entity e: entities){
            if(e instanceof Voile && e.isLibre()){
                if(e.getX()==m.getX() && e.getY()==m.getY() && !((Voile) e).isOpenned()){
                    e.setLibre(false);
                    ((Voile) e).setOpenned(true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOnSailNotUsedOppened(Marin m) {
        for(Entity e: entities){
            if(e instanceof Voile && e.isLibre()){
                if(e.getX()==m.getX() && e.getY()==m.getY() && ((Voile) e).isOpenned()){
                    e.setLibre(false);
                    ((Voile) e).setOpenned(false);
                    return true;
                }
            }
        }
        return false;
    }
}
