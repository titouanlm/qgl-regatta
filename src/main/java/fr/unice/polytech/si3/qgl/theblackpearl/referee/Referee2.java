package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Calculator;
import fr.unice.polytech.si3.qgl.theblackpearl.Cockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.AutreBateau;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Courant;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Recif;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

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
    private boolean goThroughCheckpoint;

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
        this.goThroughCheckpoint = false;
        this.c = new Calculator();
    }

    public void startGame(int nbTour) throws Exception {
        this.initGame();
        this.nextRound(nbTour);
    }

    public InitGame getParsedInitGameReferee() {
        return parsedInitGameReferee;
    }

    public NextRound getParsedNextRoundReferee() {
        return parsedNextRoundReferee;
    }

    public boolean startRound(String actions) throws Exception {
        this.rotationShip=0.;
        this.speedShip=0.0;
        this.resetEntities();
        this.resetSailors();
        this.getActions(actions);
        this.executeActions();
        return this.moveShip();
    }

    public void resetEntities() {
        for (Entity e : parsedInitGameReferee.getBateau().getEntities()) {
            e.setLibre(true);
        }
    }

    public void resetSailors() {
        for (Marin m : parsedInitGameReferee.getMarins()) {
            m.setCanMove(true);
            m.setLibre(true);
        }
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

    public void nextRound(int nbTour) throws Exception {
        while (!this.getFinishGame() && this.tour < nbTour) {
            System.out.println(this.tour + " : " + parsedInitGameReferee.getBateau().getPosition());
            this.rotationShip=0.;
            this.speedShip=0.0;
            this.resetEntities();
            this.resetSailors();
            this.getActions(this.cockpit.nextRound(nextRound));
            this.executeActions();
            this.moveShip();
            this.updateNextRound();
            this.tour++;
        }
        System.out.println("SCORE DE LA PARTIE : " + ((nbTour-this.tour)+1));
    }

    public boolean getFinishGame() throws Exception {
        RegattaGoal regatta =  (RegattaGoal) parsedInitGameReferee.getGoal();
        List<Checkpoint> checkpoints = regatta.getCheckpoints();
        if(c.shapeInCollision(parsedInitGameReferee.getBateau(), checkpoints.get(0))){
            regatta.removeCheckpoint();
        }
        return regatta.getCheckpoints().isEmpty();
    }

    public void getActions(String actionsRound) {
        actionsRound = "{" + "\"actions\":" + actionsRound + "}";
        try {
            this.parsedActionsRound = objectMapperReferee.readValue(actionsRound, ActionsRound.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void executeActions() {
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

    public boolean moveShip() throws Exception {
        //Calculs rames
        int nbRamesBabord = parsedInitGameReferee.getBateau().nbMarinRameBabord();
        int nbRamesTribord = parsedInitGameReferee.getBateau().nbMarinRameTribord();
        int nbRames = parsedInitGameReferee.getBateau().getNbRame();
        this.rotationShip += c.calculRotationRamesTribordBabord(nbRamesBabord,nbRamesTribord, nbRames);

        //Calculs voiles
        int nbVoileOuverte = parsedInitGameReferee.getBateau().nbVoileOuverte();
        int nbVoile = parsedInitGameReferee.getBateau().nbVoile();

        int N=0;
        int nbStep=100;

        while(N<nbStep){
            //TEST COURANT
            for(VisibleEntity v : parsedNextRoundReferee.getVisibleEntities()){
                if(v instanceof Courant){
                    if(c.shapesCollide(parsedInitGameReferee.getBateau(), v)){
                        Position positionAfterStream = c.calculInfluenceOfStream(parsedInitGameReferee.getBateau().getPosition(), (Courant) v, nbStep);
                        parsedInitGameReferee.getBateau().setPosition(positionAfterStream);
                        //System.out.println("COURANT" + N + " : " + parsedInitGameReferee.getBateau().getPosition());
                    }
                }
            }

            this.speedShip=0.;
            this.speedShip += c.calculVitesseRames(nbRamesBabord+nbRamesTribord,nbRames);
            if(nbVoile>0)
                this.speedShip += c.calculVitesseVent(nbVoileOuverte,nbVoile,parsedNextRoundReferee.getWind(), parsedInitGameReferee.getBateau());

            Position positionShipThisStep = c.calculNewPositionShip(this.speedShip, this.rotationShip ,parsedInitGameReferee.getBateau().getPosition(), nbStep);
            parsedInitGameReferee.getBateau().setPosition(positionShipThisStep);

            if(this.cockpit==null){
                RegattaGoal regatta = (RegattaGoal) parsedInitGameReferee.getGoal();
                if(c.shapeInCollision(parsedInitGameReferee.getBateau(), regatta.getCheckpoints().get(0))){
                    this.goThroughCheckpoint = true;
                }
                if(this.testCollision()){
                    return true;
                }
            }
            N++;
        }
        return false;
    }

    public boolean testCollision() throws Exception {
        for(VisibleEntity v : parsedNextRoundReferee.getVisibleEntities()){
            if(v instanceof Recif || v instanceof AutreBateau){
                if (c.shapesCollide(parsedInitGameReferee.getBateau(), v)){
                    System.out.println(" ******************************** COLLISION ******************************** ");
                    return true;
                }
            }
        }
        return false;
    }

    public void updateNextRound() {
        parsedNextRoundReferee.setShip(parsedInitGameReferee.getBateau());
        try {
            this.nextRound =objectMapperReferee.writeValueAsString(parsedNextRoundReferee);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public boolean getGoThroughCheckpoint() {
        return this.goThroughCheckpoint;
    }
}
