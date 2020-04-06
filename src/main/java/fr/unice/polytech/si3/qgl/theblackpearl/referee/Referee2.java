package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.theblackpearl.Calculator;
import fr.unice.polytech.si3.qgl.theblackpearl.Cockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.AutreBateau;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Recif;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import org.w3c.dom.css.Rect;

import java.util.List;

public class Referee2 {
    private String initGame;
    private String nextRound;
    private Cockpit cockpit;
    private Calculator c;
    private int tour;
    private InitGame parsedInitGameReferee;
    private NextRound parsedNextRoundReferee;
    private ActionsRound parsedActionsRound;
    private ObjectMapper objectMapperReferee;
    private double rotationShip;
    private double speedShip;

    public Referee2(String initGame, String firstRound, Cockpit cockpit) {
        this.initGame = initGame;
        this.nextRound = firstRound;
        this.cockpit = cockpit;
        this.tour = 0;
        this.objectMapperReferee = new ObjectMapper();
        this.c = new Calculator();
    }

    public Referee2(InitGame initGame, NextRound nextRound) {
        this.parsedInitGameReferee = initGame;
        this.parsedNextRoundReferee = nextRound;
        this.objectMapperReferee = new ObjectMapper();
        this.c = new Calculator();
    }

    public void startGame(int nbTour) {
        this.initGame();
        this.nextRound(nbTour);
    }

    public boolean startRound(String actions){
        this.rotationShip=0.0;
        this.speedShip=0.0;
        for (Entity e : parsedInitGameReferee.getBateau().getEntities()) {
            e.setLibre(true);
        }
        for (Marin m : parsedInitGameReferee.getMarins()) {
            m.setCanMove(true);
            m.setLibre(true);
        }
        this.getActions(actions);
        this.executeActions();
        return this.crashTest();
    }

    public void initGame() {
        try {
            parsedInitGameReferee = objectMapperReferee.readValue(initGame, InitGame.class);
            this.cockpit.initGame(initGame);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            parsedNextRoundReferee = objectMapperReferee.readValue(nextRound, NextRound.class);
            this.cockpit.initGame(initGame);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void nextRound(int nbTour) {
        while (!this.getFinishGame() && this.tour < nbTour) {
            System.out.println(this.tour + " : " + parsedInitGameReferee.getBateau().getPosition());
            this.rotationShip=0.0;
            this.speedShip=0.0;
            for (Entity e : parsedInitGameReferee.getBateau().getEntities()) {
                e.setLibre(true);
            }
            for (Marin m : parsedInitGameReferee.getMarins()) {
                m.setCanMove(true);
                m.setLibre(true);
                //System.out.println(m);
            }
            this.getActions(this.cockpit.nextRound(nextRound));
            this.executeActions();
            this.moveShip();
            this.updateNextRound();
            this.tour++;
        }
        System.out.println("SCORE DE LA PARTIE : " + ((nbTour-this.tour)+1));
    }

    private boolean getFinishGame() {
        RegattaGoal regatta =  (RegattaGoal) parsedInitGameReferee.getGoal();
        List<Checkpoint> checkpoints = regatta.getCheckpoints();
        if(c.shapeInCollision(parsedInitGameReferee.getBateau(), checkpoints.get(0))){
            regatta.removeCheckpoint();
        }
        return regatta.getCheckpoints().isEmpty();
    }

    private void getActions(String actionsRound) {
        actionsRound = "{" + "\"actions\":" + actionsRound + "}";
        try {
            this.parsedActionsRound = objectMapperReferee.readValue(actionsRound, ActionsRound.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (ActionRound a : this.parsedActionsRound.getActionsRound()) {
            System.out.println(a);
        }
    }

    private void executeActions() {
        for (ActionRound a : this.parsedActionsRound.getActionsRound()) {
            if (a instanceof MovingReferee) {
                ((MovingReferee) a).tryToMoveMarin(parsedInitGameReferee);
            }
        }

        for (ActionRound a : this.parsedActionsRound.getActionsRound()) {
            if (a instanceof OarReferee) {
                ((OarReferee) a).tryToOar(parsedInitGameReferee);
            }
            if (a instanceof TurnReferee) {
                this.rotationShip+=(((TurnReferee) a).tryToTurn(parsedInitGameReferee));
            }
            if(a instanceof LiftSailReferee){
                ((LiftSailReferee) a).tryToLiftSail(parsedInitGameReferee);
            }
            if(a instanceof LowerSailReferee){
                ((LowerSailReferee) a).tryToLowerSail(parsedInitGameReferee);
            }
        }
    }

    private void moveShip() {
        //Calculs rames
        int nbRamesBabord = parsedInitGameReferee.getBateau().nbMarinRameBabord();
        int nbRamesTribord = parsedInitGameReferee.getBateau().nbMarinRameTribord();
        int nbRames = parsedInitGameReferee.getBateau().getNbRame();
        this.rotationShip += c.calculRotationRamesTribordBabord(nbRamesBabord,nbRamesTribord, nbRames);
        this.speedShip += c.calculVitesseRames(nbRamesBabord+nbRamesTribord,nbRames);

        //Calculs voiles
        int nbVoileOuverte = parsedInitGameReferee.getBateau().nbVoileOuverte();
        int nbVoile = parsedInitGameReferee.getBateau().nbVoile();
        if(nbVoile>0)
            this.speedShip += c.calculVitesseVent(nbVoileOuverte,nbVoile,parsedNextRoundReferee.getWind(), parsedInitGameReferee.getBateau());
        int N=0;
        int nbStep=50;
        while(N<nbStep){
            Position positionShipThisStep = c.calculNewPositionShip(this.speedShip, this.rotationShip ,parsedInitGameReferee.getBateau().getPosition(), nbStep);
            parsedInitGameReferee.getBateau().setPosition(positionShipThisStep);
            N++;
        }
        //MAJ Orientation de la forme du bateau
        Rectangle shipShape = (Rectangle) parsedInitGameReferee.getBateau().getShape();
        shipShape.setOrientationRectangle(parsedInitGameReferee.getBateau().getPosition().getOrientation());
    }

    private boolean crashTest() {
        //Calculs rames
        int nbRamesBabord = parsedInitGameReferee.getBateau().nbMarinRameBabord();
        int nbRamesTribord = parsedInitGameReferee.getBateau().nbMarinRameTribord();
        int nbRames = parsedInitGameReferee.getBateau().getNbRame();
        this.rotationShip += c.calculRotationRamesTribordBabord(nbRamesBabord,nbRamesTribord, nbRames);
        this.speedShip += c.calculVitesseRames(nbRamesBabord+nbRamesTribord,nbRames);

        //Calculs voiles
        int nbVoileOuverte = parsedInitGameReferee.getBateau().nbVoileOuverte();
        int nbVoile = parsedInitGameReferee.getBateau().nbVoile();
        if(nbVoile>0)
            this.speedShip += c.calculVitesseVent(nbVoileOuverte,nbVoile,parsedNextRoundReferee.getWind(), parsedInitGameReferee.getBateau());
        int N=0;
        int nbStep=50;
        while(N<nbStep){
            Position positionShipThisStep = c.calculNewPositionShip(this.speedShip, this.rotationShip ,parsedInitGameReferee.getBateau().getPosition(), nbStep);
            //System.out.println(positionShipThisStep);
            parsedInitGameReferee.getBateau().setPosition(positionShipThisStep);
            //parsedInitGameReferee.getBateau().setPosition(new Position(808,700,-2.0479183822456073));
            if(this.testCollision()){
                System.out.println("Position bateeau : " + parsedInitGameReferee.getBateau().getPosition());
                //return true;
            }
            N++;
        }
        //MAJ Orientation de la forme du bateau
        Rectangle shipShape = (Rectangle) parsedInitGameReferee.getBateau().getShape();
        shipShape.setOrientationRectangle(parsedInitGameReferee.getBateau().getPosition().getOrientation());
        return false;
    }

    private boolean testCollision() {
        for(VisibleEntity v : parsedNextRoundReferee.getVisibleEntities()){
            if(v instanceof Recif || v instanceof AutreBateau){
                if (c.shapescollide(parsedInitGameReferee.getBateau(), v)){
                    System.out.println(" ******************************** COLLISION ******************************** ");
                    System.out.println(v.getPosition() + " Height : " + ((Rectangle) v.getShape()).getHeight() + " Width : " + ((Rectangle) v.getShape()).getWidth());
                    return true;
                }
            }
        }
        return false;
    }

    private void updateNextRound() {
        parsedNextRoundReferee.setShip(parsedInitGameReferee.getBateau());
        try {
            this.nextRound =objectMapperReferee.writeValueAsString(parsedNextRoundReferee);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println(this.nextRound);
    }
}
