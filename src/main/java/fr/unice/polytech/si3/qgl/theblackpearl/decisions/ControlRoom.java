package fr.unice.polytech.si3.qgl.theblackpearl.decisions;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Wind;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Sail;

import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity.deleteEntity;

public class ControlRoom {

    private int[][] tableauPositionMarinOriginale;
    private Wind wind;
    private InitGame parsedInitGame;
    int meilleurAngleRealisablePosition=0;
    private boolean continuerConfigurationRames=true;
    private List<Action> actionsNextRound;
    private ArrayList<Entity> listeEntiteCopie;
    private ArrayList<Action> actionsNextRoundTemporaire;
    private List<Sailor> marinsOccupes;

    public ControlRoom(InitGame game, Wind wind, List<Action> actionsNextRound){
        this.parsedInitGame =game;
        this.wind = wind;
        this.actionsNextRound=actionsNextRound;
    }

    public void creationTableauMarins(){
        tableauPositionMarinOriginale = new int[parsedInitGame.getSailors().size()][2];
    }
    public void priseEnComptePositionMarins(){
        for (int b = 0; b< parsedInitGame.getSailors().size(); b++){
            tableauPositionMarinOriginale[b][0] = parsedInitGame.getSailors().get(b).getX();
            tableauPositionMarinOriginale[b][1] = parsedInitGame.getSailors().get(b).getY();
        }
    }

    public void restaurationPositionMarins(){
        int positionnementMarins =0;
        for (Sailor sailor : parsedInitGame.getSailors()) {
            sailor.setX(tableauPositionMarinOriginale[positionnementMarins][0]);
            sailor.setY(tableauPositionMarinOriginale[positionnementMarins][1]);
            positionnementMarins++;
        }
    }

    public void preConfigurationRamesBateau(boolean neMarchePasPourLePremiertour, int nombreTour, int[] nombreMarinAplacerCopie, List<Double> meilleurAngleRealisable, Calculator calculateur, int meilleurAngleRealisablePosition, List<Sailor> marinsOccupes) {
        if (nombreTour != 0) {
            if (this.meilleurAngleRealisablePosition < meilleurAngleRealisable.size() - 1) {
                if (!neMarchePasPourLePremiertour) {
                    restaurationPositionMarins();
                    for (Sailor m : parsedInitGame.getSailors()) if (!marinsOccupes.contains(m)) m.setAvailable(true);
                    calculateur.setNumberSailorsToPlace(nombreMarinAplacerCopie.clone());
                    calculateur.decrementNumberSailorsToPlace(nombreTour, true, true);
                }
                if (calculateur.getNumberSailorsToPlace()[0] < 0 || calculateur.getNumberSailorsToPlace()[1] < 0) {
                    ++meilleurAngleRealisablePosition;
                    this.meilleurAngleRealisablePosition = meilleurAngleRealisablePosition;
                    calculateur.setNumberSailorsToPlace(parsedInitGame.getShip().nombreMarinsRamesBabordTribordRames(meilleurAngleRealisable.get(meilleurAngleRealisablePosition), parsedInitGame.getShip().getListRames()));
                } else this.continuerConfigurationRames = false;
            }
        }
    }

    private Calculator configurationCalculateur(List<Double> meilleurAngleRealisable){
        Calculator calculateur = new Calculator();
        calculateur.setNumberSailorsToPlace(parsedInitGame.getShip().nombreMarinsRamesBabordTribordRames(meilleurAngleRealisable.get(0), parsedInitGame.getShip().getListRames()));
        calculateur.setNumberSailorsToPlaceCopy(calculateur.getNumberSailorsToPlace().clone());
        return calculateur;
    }

    private void initConfigurationRames(int meilleurAngleRealisablePosition, List<Sailor> marinsOccupes){
        this.marinsOccupes =marinsOccupes;
        this.meilleurAngleRealisablePosition=meilleurAngleRealisablePosition;
        creationTableauMarins();
        this.continuerConfigurationRames=true;
        priseEnComptePositionMarins();
        listeEntiteCopie = new ArrayList<>();
        actionsNextRoundTemporaire = new ArrayList<>();
    }

    public double configurationRames(List<Double> meilleurAngleRealisable, int meilleurAngleRealisablePosition, List<Sailor> marinsOccupes){
        initConfigurationRames(meilleurAngleRealisablePosition,marinsOccupes);
        Calculator calculateur = configurationCalculateur(meilleurAngleRealisable);
        meilleurPositionnementMarins(calculateur,meilleurAngleRealisable);
        actionsNextRound.addAll(actionsNextRoundTemporaire);
        return meilleurAngleRealisable.get(this.meilleurAngleRealisablePosition);
    }

    public void meilleurPositionnementMarins(Calculator calculateur ,List<Double> meilleurAngleRealisable){
        boolean neMarchePasPourLePremiertour = true;
        int nombreTour=0;
        do {
            boolean marinPlaceGauche=false;
            boolean marinPlaceDroite=false;
            actionsNextRoundTemporaire = new ArrayList<>();
            preConfigurationRamesBateau(neMarchePasPourLePremiertour,nombreTour,calculateur.getNumberSailorsToPlaceCopy(),meilleurAngleRealisable,calculateur,this.meilleurAngleRealisablePosition, marinsOccupes);
            listeEntiteCopie = (ArrayList<Entity>) parsedInitGame.getShip().getEntities().clone();
            for (Sailor m : parsedInitGame.getSailors()){
                if (m.isAvailable()) {
                    MOVING moving = m.deplacementMarinAllerRamer(listeEntiteCopie, calculateur.getNumberSailorsToPlace()[0], calculateur.getNumberSailorsToPlace()[1], (int) ((Rectangle) parsedInitGame.getShip().getShape()).getWidth());
                    if (moving != null && (calculateur.getNumberSailorsToPlace()[0] != 0 || calculateur.getNumberSailorsToPlace()[1] != 0)) {
                        if (moving.getYDistance() + m.getY() <= (parsedInitGame.getShip().getDeck().getWidth()/2-1) && calculateur.getNumberSailorsToPlace()[0] > 0) { //marin placé à Tribord
                            calculateur.decrementNumberSailorsToPlace(1,true,false);
                            actionsNextRoundTemporaire.add(moving);marinPlaceGauche = true;
                            listeEntiteCopie= deleteEntity(listeEntiteCopie, marinPlaceGauche, marinPlaceDroite, m, moving);
                            m.setX(moving.getXDistance() + m.getX());m.setY(moving.getYDistance() + m.getY());

                        } else if (moving.getYDistance() + m.getY() >= (parsedInitGame.getShip().getDeck().getWidth()/2-1) && calculateur.getNumberSailorsToPlace()[1] > 0) { //marin placé à Babord
                            calculateur.decrementNumberSailorsToPlace(1,false,true);
                            actionsNextRoundTemporaire.add(moving);marinPlaceDroite = true;
                            listeEntiteCopie= deleteEntity(listeEntiteCopie, marinPlaceGauche, marinPlaceDroite, m, moving);
                            m.setX(moving.getXDistance() + m.getX());m.setY(moving.getYDistance() + m.getY());

                        } else{
                            m.resetMarinPourUnNouveauTour();
                        }
                    }
                }
            }
            neMarchePasPourLePremiertour=false;nombreTour++;
        } while ((calculateur.getNumberSailorsToPlace()[0] != 0 || calculateur.getNumberSailorsToPlace()[1] != 0) && this.continuerConfigurationRames);
    }

    public Sailor hisserLaVoile(){
        for (Sailor sailor : parsedInitGame.getSailors()) {
            if (sailor.isAvailable()) {
                for (Entity e : parsedInitGame.getShip().getEntities()) {
                    if (e instanceof Sail) {
                        if (!((Sail) e).isOpenned()) {
                            MOVING moving = sailor.deplacementMarinVoile(parsedInitGame.getShip().getEntities(), true);
                            if (moving != null) {
                                actionsNextRound.add(moving);
                                sailor.setX(moving.getXDistance() + sailor.getX());
                                sailor.setY(moving.getYDistance() + sailor.getY());
                                ((Sail) e).setOpenned(true);
                                parsedInitGame.getShip().setPosition(new Position(this.parsedInitGame.getShip().getPosition().getX() + wind.getStrength() * Math.cos(wind.getOrientation()), this.parsedInitGame.getShip().getPosition().getY() + wind.getStrength() * Math.sin(wind.getOrientation()), parsedInitGame.getShip().getPosition().getOrientation()));
                                return sailor;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Sailor affaisserLaVoile(){
        for (Sailor sailor : parsedInitGame.getSailors()) {
            if (sailor.isAvailable()) {
                for (Entity e : parsedInitGame.getShip().getEntities()) {
                    if (e instanceof Sail) {
                        if (((Sail) e).isOpenned()) {
                            MOVING moving = sailor.deplacementMarinVoile(parsedInitGame.getShip().getEntities(), false);
                            if (moving != null) {
                                actionsNextRound.add(moving);
                                sailor.setX(moving.getXDistance() + sailor.getX());
                                sailor.setY(moving.getYDistance() + sailor.getY());
                                ((Sail) e).setOpenned(false);
                                return sailor;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Sailor utilisationVoile(){
        if (utilisationVoileOuiNon(parsedInitGame)) return hisserLaVoile();
        else return affaisserLaVoile();
    }

    public boolean utilisationVoileOuiNon(InitGame game){
        double xAvecVoile=game.getShip().getPosition().getX() + wind.getStrength()*Math.cos(wind.getOrientation());
        double yAvecVoile=game.getShip().getPosition().getY() + wind.getStrength()*Math.sin(wind.getOrientation());
        Position positionSiRameactive = new Position(xAvecVoile,yAvecVoile, game.getShip().getPosition().getOrientation());
        return Math.sqrt(Math.pow(2,positionSiRameactive.getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,positionSiRameactive.getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY())) <=
                Math.sqrt(Math.pow(2,game.getShip().getPosition().getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,game.getShip().getPosition().getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY()));
    }

    public boolean isThereARudder(){
        for (Entity e : parsedInitGame.getShip().getEntities()) {
            if (e instanceof Rudder){
                return true;
            }
        }
        return false;
    }

    public boolean isThereASail(){
        for (Entity e : parsedInitGame.getShip().getEntities()) {
            if (e instanceof Sail){
                return true;
            }
        }
        return false;
    }

    public Sailor configurationGouvernail(){
        for (Sailor m : parsedInitGame.getSailors()){
            if (m.isAvailable()){
                MOVING moving = m.deplacementMarinGouvernail(parsedInitGame.getShip().getEntities());
                if (moving!=null){
                    actionsNextRound.add(moving);
                    m.setX(moving.getXDistance() + m.getX());
                    m.setY(moving.getYDistance() + m.getY());
                    return m;
                }
            }
        }
        return null;
    }

}