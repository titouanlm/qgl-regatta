package fr.unice.polytech.si3.qgl.theblackpearl.referee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.theblackpearl.Calculator;
import fr.unice.polytech.si3.qgl.theblackpearl.Cockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;


public class Referee2 {
    private String initGame;
    private String nextRound;
    private Cockpit cockpit;
    private boolean finishGame;
    private int nbTour;
    private InitGame parsedInitGameReferee;
    private ActionsRound parsedActionsRound;
    private ObjectMapper objectMapperReferee;
    private double rotationShip;

    public Referee2(String initGame, String firstRound, Cockpit cockpit) {
        this.initGame = initGame;
        this.nextRound = firstRound;
        this.cockpit = cockpit;
        this.finishGame = false;
        this.nbTour = 0;
        this.objectMapperReferee = new ObjectMapper();
    }

    public void startGame() {
        this.initGame();
        this.nextRound();
    }

    public void initGame() {
        try {
            parsedInitGameReferee = objectMapperReferee.readValue(initGame, InitGame.class);
            this.cockpit.initGame(initGame);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void nextRound() {
        while (!this.finishGame && this.nbTour < 60) {
            this.rotationShip=0.0;
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
            this.nbTour++;
        }

    }

    private void getActions(String actionsRound) {
        actionsRound = "{" + "\"actions\":" + actionsRound + "}";
        try {
            this.parsedActionsRound = objectMapperReferee.readValue(actionsRound, ActionsRound.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        for (ActionRound a : this.parsedActionsRound.getActionsRound()) {
//            System.out.println(a);
//        }
    }

    private void executeActions() {
        for (ActionRound a : this.parsedActionsRound.getActionsRound()) {
            if (a instanceof MovingTest) {
                ((MovingTest) a).tryToMoveMarin(parsedInitGameReferee);
            }
        }

        for (ActionRound a : this.parsedActionsRound.getActionsRound()) {
            if (a instanceof OarTest) {
                ((OarTest) a).tryToOar(parsedInitGameReferee);
            }
            if (a instanceof TurnTest) {
                this.rotationShip+=(((TurnTest) a).tryToTurn(parsedInitGameReferee));
            }
        }
    }

    private void moveShip() {
        int nbRamesBabord = parsedInitGameReferee.getBateau().nbMarinRameBabord();
        int nbRamesTribord = parsedInitGameReferee.getBateau().nbMarinRameTribord();
        int nbRames = parsedInitGameReferee.getBateau().getNbRame();
        Calculator c =new Calculator();
        this.rotationShip+=c.calculRotationRamesTribordBabord(nbRamesBabord,nbRamesTribord, nbRames);
        double vitesseRames = c.calculVitesseRames(nbRamesBabord+nbRamesTribord,nbRames);
        int N=100;
        while(N>0){
            Position positionShipThisStep = c.calculNewPositionShip(vitesseRames, this.rotationShip ,parsedInitGameReferee.getBateau().getPosition(), 100);
            parsedInitGameReferee.getBateau().setPosition(positionShipThisStep);
            N--;
        }
        Rectangle shipShape = (Rectangle) parsedInitGameReferee.getBateau().getShape();
        shipShape.setOrientation(parsedInitGameReferee.getBateau().getPosition().getOrientation());
        System.out.println(parsedInitGameReferee.getBateau().getPosition());
    }

    private void updateNextRound() {
        StringBuilder roundJSON= new StringBuilder("{\"ship\":");
        try {
            String jsonStr = objectMapperReferee.writeValueAsString(parsedInitGameReferee.getBateau());
            roundJSON.append(jsonStr.replace(",\"voile\":null",""));
            roundJSON.append(",\"visibleEntities\":[]}");
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.nextRound=roundJSON.toString();
    }
}
