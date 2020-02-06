package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import com.fasterxml.jackson.annotation.*;
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
        double angleCalcule=-(Math.PI/2)-Math.PI/nombreRames.size();
        int i;
        for (i=-nombreRames.size()/2; ;i++){ // il faut tronquer le double angle   Calcule!=angle
            angleCalcule += Math.PI/nombreRames.size();
            if (angleCalcule>=angle-9.9E-5 && angleCalcule<=angle+9.9E-5) break;
        }
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
        if (i==0){
            i=nombreRames.size()/2;
            b=nombreRames.size()/2;
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
                        ((Rame) e).setUsed(true);
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
