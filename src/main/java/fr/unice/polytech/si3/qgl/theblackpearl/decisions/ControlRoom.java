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

import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity.supprimerEntite;
import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Oar.setRamesUsed;

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
        tableauPositionMarinOriginale = new int[parsedInitGame.getMarins().size()][2];
    }
    public void priseEnComptePositionMarins(){
        for (int b = 0; b< parsedInitGame.getMarins().size(); b++){
            tableauPositionMarinOriginale[b][0] = parsedInitGame.getMarins().get(b).getX();
            tableauPositionMarinOriginale[b][1] = parsedInitGame.getMarins().get(b).getY();
        }
    }

    public void restaurationPositionMarins(){
        int positionnementMarins =0;
        for (Sailor sailor : parsedInitGame.getMarins()) {
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
                    for (Sailor m : parsedInitGame.getMarins()) if (!marinsOccupes.contains(m)) m.setLibre(true);
                    calculateur.setNumberSailorsToPlace(nombreMarinAplacerCopie.clone());
                    calculateur.decrementNumberSailorsToPlace(nombreTour, true, true);
                }
                if (calculateur.getNumberSailorsToPlace()[0] < 0 || calculateur.getNumberSailorsToPlace()[1] < 0) {
                    ++meilleurAngleRealisablePosition;
                    this.meilleurAngleRealisablePosition = meilleurAngleRealisablePosition;
                    calculateur.setNumberSailorsToPlace(parsedInitGame.getBateau().nombreMarinsRamesBabordTribordRames(meilleurAngleRealisable.get(meilleurAngleRealisablePosition), parsedInitGame.getBateau().getListRames()));
                } else this.continuerConfigurationRames = false;
            }
        }
    }

    private Calculator configurationCalculateur(List<Double> meilleurAngleRealisable){
        Calculator calculateur = new Calculator();
        calculateur.setNumberSailorsToPlace(parsedInitGame.getBateau().nombreMarinsRamesBabordTribordRames(meilleurAngleRealisable.get(0), parsedInitGame.getBateau().getListRames()));
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
        setRamesUsed(parsedInitGame,listeEntiteCopie);//vérifier si ca marche
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
            listeEntiteCopie = (ArrayList<Entity>) parsedInitGame.getBateau().getEntities().clone();
            for (Sailor m : parsedInitGame.getMarins()){
                if (m.isLibre()) {
                    MOVING moving = m.deplacementMarinAllerRamer(listeEntiteCopie, calculateur.getNumberSailorsToPlace()[0], calculateur.getNumberSailorsToPlace()[1], (int) ((Rectangle) parsedInitGame.getBateau().getShape()).getWidth());
                    if (moving != null && (calculateur.getNumberSailorsToPlace()[0] != 0 || calculateur.getNumberSailorsToPlace()[1] != 0)) {
                        if (moving.getYdistance() + m.getY() <= (parsedInitGame.getBateau().getDeck().getWidth()/2-1) && calculateur.getNumberSailorsToPlace()[0] > 0) { //marin placé à Tribord
                            calculateur.decrementNumberSailorsToPlace(1,true,false);
                            actionsNextRoundTemporaire.add(moving);marinPlaceGauche = true;
                            listeEntiteCopie=supprimerEntite(listeEntiteCopie, marinPlaceGauche, marinPlaceDroite, m, moving);
                            m.setX(moving.getXdistance() + m.getX());m.setY(moving.getYdistance() + m.getY());

                        } else if (moving.getYdistance() + m.getY() >= (parsedInitGame.getBateau().getDeck().getWidth()/2-1) && calculateur.getNumberSailorsToPlace()[1] > 0) { //marin placé à Babord
                            calculateur.decrementNumberSailorsToPlace(1,false,true);
                            actionsNextRoundTemporaire.add(moving);marinPlaceDroite = true;
                            listeEntiteCopie=supprimerEntite(listeEntiteCopie, marinPlaceGauche, marinPlaceDroite, m, moving);
                            m.setX(moving.getXdistance() + m.getX());m.setY(moving.getYdistance() + m.getY());

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
        for (Sailor sailor : parsedInitGame.getMarins()) {
            if (sailor.isLibre()) { //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                for (Entity e : parsedInitGame.getBateau().getEntities()) {
                    if (e instanceof Sail) {
                        if (!((Sail) e).isOpenned()) {
                            MOVING moving = sailor.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), true);
                            if (moving != null) {
                                actionsNextRound.add(moving);
                                sailor.setX(moving.getXdistance() + sailor.getX());
                                sailor.setY(moving.getYdistance() + sailor.getY());
                                ((Sail) e).setOpenned(true);
                                parsedInitGame.getBateau().setPosition(new Position(this.parsedInitGame.getBateau().getPosition().getX() + wind.getStrength() * Math.cos(wind.getOrientation()), this.parsedInitGame.getBateau().getPosition().getY() + wind.getStrength() * Math.sin(wind.getOrientation()), parsedInitGame.getBateau().getPosition().getOrientation()));
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
        for (Sailor sailor : parsedInitGame.getMarins()) {
            if (sailor.isLibre()) { //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                for (Entity e : parsedInitGame.getBateau().getEntities()) {
                    if (e instanceof Sail) {
                        if (((Sail) e).isOpenned()) {
                            MOVING moving = sailor.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), false);
                            if (moving != null) {
                                actionsNextRound.add(moving);
                                sailor.setX(moving.getXdistance() + sailor.getX());
                                sailor.setY(moving.getYdistance() + sailor.getY());
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
        double xAvecVoile=game.getBateau().getPosition().getX() + wind.getStrength()*Math.cos(wind.getOrientation());
        double yAvecVoile=game.getBateau().getPosition().getY() + wind.getStrength()*Math.sin(wind.getOrientation());
        Position positionSiRameactive = new Position(xAvecVoile,yAvecVoile, game.getBateau().getPosition().getOrientation());
        return Math.sqrt(Math.pow(2,positionSiRameactive.getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,positionSiRameactive.getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY())) <=
                Math.sqrt(Math.pow(2,game.getBateau().getPosition().getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,game.getBateau().getPosition().getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY()));
    }

    public boolean isThereARudder(){
        for (Entity e : parsedInitGame.getBateau().getEntities()) {
            if (e instanceof Rudder){
                return true;
            }
        }
        return false;
    }

    public boolean isThereASail(){
        for (Entity e : parsedInitGame.getBateau().getEntities()) {
            if (e instanceof Sail){
                return true;
            }
        }
        return false;
    }

    public Sailor configurationGouvernail(){
        for (Sailor m : parsedInitGame.getMarins()){
            if (m.isLibre()){
                MOVING moving = m.deplacementMarinGouvernail(parsedInitGame.getBateau().getEntities());
                if (moving!=null){
                    actionsNextRound.add(moving);
                    m.setX(moving.getXdistance() + m.getX());
                    m.setY(moving.getYdistance() + m.getY());
                    return m;
                }
            }
        }
        return null;
    }

}