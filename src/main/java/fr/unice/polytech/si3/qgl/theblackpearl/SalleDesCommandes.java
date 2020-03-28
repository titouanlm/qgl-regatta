package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Voile;


import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity.supprimerEntite;
import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame.setRamesUsed;

public class SalleDesCommandes {

    private int[][] tableauPositionPotentielleMarins;
    private Vent vent;
    private InitGame parsedInitGame;
    int meilleurAngleRealisablePosition=0;
    private boolean continuerConfigurationRames=true;
    private List<Action> actionsNextRound;
    private ArrayList<Entity> listeEntiteCopie;
    private ArrayList<Action> actionsNextRoundTemporaire;
    private List<Marin> marinsOccupes;

    public SalleDesCommandes(InitGame game, Vent vent, List<Action> actionsNextRound){
        this.parsedInitGame =game;
        this.vent=vent;
        this.actionsNextRound=actionsNextRound;
    }

    public int[][] getTableauPositionPotentielleMarins() {
        return tableauPositionPotentielleMarins;
    }

    public void creationTableauMarins(){
        tableauPositionPotentielleMarins = new int[parsedInitGame.getMarins().size()][2];
    }
    public void priseEnComptePositionMarins(){
        for (int b = 0; b< parsedInitGame.getMarins().size(); b++){
            tableauPositionPotentielleMarins[b][0] = parsedInitGame.getMarins().get(b).getX();
            tableauPositionPotentielleMarins[b][1] = parsedInitGame.getMarins().get(b).getY();
        }
    }

    public void restaurationPositionMarins(){
        int positionnementMarins =0;
        for (Marin marin : parsedInitGame.getMarins()) {
            marin.setX(tableauPositionPotentielleMarins[positionnementMarins][0]);
            marin.setY(tableauPositionPotentielleMarins[positionnementMarins][1]);
            positionnementMarins++;
        }
    }

    public void preConfigurationRamesBateau(boolean neMarchePasPourLePremiertour, int nombreTour, int[] nombreMarinAplacerCopie, List<Double> meilleurAngleRealisable, Calculator calculateur, int meilleurAngleRealisablePosition, List<Marin> marinsOccupes) {
        if (nombreTour != 0) {
            if (this.meilleurAngleRealisablePosition < meilleurAngleRealisable.size() - 1) {
                if (!neMarchePasPourLePremiertour) {
                    restaurationPositionMarins();
                    for (Marin m : parsedInitGame.getMarins()) if (!marinsOccupes.contains(m)) m.setLibre(true);
                    calculateur.setNombreMarinAplacer(nombreMarinAplacerCopie.clone());
                    calculateur.decrementationNombreMarinPlacer(nombreTour, true, true);
                }
                if (calculateur.getNombreMarinAplacer()[0] < 0 || calculateur.getNombreMarinAplacer()[1] < 0) {
                    ++meilleurAngleRealisablePosition;
                    this.meilleurAngleRealisablePosition = meilleurAngleRealisablePosition;
                    calculateur.setNombreMarinAplacer(parsedInitGame.getBateau().nombreMarinsBabordTribordRames(meilleurAngleRealisable.get(meilleurAngleRealisablePosition), parsedInitGame.getBateau().getListRames()));
                } else this.continuerConfigurationRames = false;
            }
        }
    }

    public Calculator configurationCalculateur(List<Double> meilleurAngleRealisable){
        Calculator calculateur = new Calculator();
        calculateur.setNombreMarinAplacer(parsedInitGame.getBateau().nombreMarinsBabordTribordRames(meilleurAngleRealisable.get(0), parsedInitGame.getBateau().getListRames()));
        calculateur.setNombreMarinAplacerCopie(calculateur.getNombreMarinAplacer().clone());
        return calculateur;
    }

    public void initConfigurationRames(int meilleurAngleRealisablePosition, List<Marin> marinsOccupés){
        this.marinsOccupes =marinsOccupés;
        this.meilleurAngleRealisablePosition=meilleurAngleRealisablePosition;
        creationTableauMarins();
        this.continuerConfigurationRames=true;
        priseEnComptePositionMarins();
        listeEntiteCopie = new ArrayList<>();
        actionsNextRoundTemporaire = new ArrayList<>();
    }

    public double configurationRames(List<Double> meilleurAngleRealisable, int meilleurAngleRealisablePosition, List<Marin> marinsOccupes){
        initConfigurationRames(meilleurAngleRealisablePosition,marinsOccupes);
        Calculator calculateur = configurationCalculateur(meilleurAngleRealisable);
        meilleurPositionnementMarins(calculateur,meilleurAngleRealisable);
        actionsNextRound.addAll(actionsNextRoundTemporaire);
        setRamesUsed(parsedInitGame,listeEntiteCopie);//vérifier si ca marche
        return meilleurAngleRealisable.get(this.meilleurAngleRealisablePosition);
    }

    public List<Action> meilleurPositionnementMarins(Calculator calculateur ,List<Double> meilleurAngleRealisable){
        boolean neMarchePasPourLePremiertour = true;
        int nombreTour=0;
        boolean marinPlaceGauche=false;
        boolean marinPlaceDroite=false;
        do {
            actionsNextRoundTemporaire = new ArrayList<>();
            preConfigurationRamesBateau(neMarchePasPourLePremiertour,nombreTour,calculateur.getNombreMarinAplacerCopie(),meilleurAngleRealisable,calculateur,this.meilleurAngleRealisablePosition, marinsOccupes);
            listeEntiteCopie = (ArrayList<Entity>) parsedInitGame.getBateau().getEntities().clone();
            for (Marin m : parsedInitGame.getMarins()){
                if (m.isLibre()) {
                    MOVING moving = m.deplacementMarinAllerRamer(listeEntiteCopie, calculateur.getNombreMarinAplacer()[0], calculateur.getNombreMarinAplacer()[1], (int) ((Rectangle) parsedInitGame.getBateau().getShape()).getWidth());
                    if (moving != null && (calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0)) {
                        if (moving.getYdistance() + m.getY() == 0 && calculateur.getNombreMarinAplacer()[0] > 0) { //Babord
                            calculateur.decrementationNombreMarinPlacer(1,true,false);
                            actionsNextRoundTemporaire.add(moving);marinPlaceGauche = true;
                        } else if (moving.getYdistance() + m.getY() != 0 && calculateur.getNombreMarinAplacer()[1] > 0) { //Tribord
                            calculateur.decrementationNombreMarinPlacer(1,false,true);
                            actionsNextRoundTemporaire.add(moving);marinPlaceDroite = true;
                        } else m.setLibre(true);
                        listeEntiteCopie=supprimerEntite(listeEntiteCopie, marinPlaceGauche, marinPlaceDroite, m, moving);
                        m.setX(moving.getXdistance() + m.getX());m.setY(moving.getYdistance() + m.getY());
                        marinPlaceGauche=false;
                        marinPlaceDroite=false;
                    }
                }
            }
            neMarchePasPourLePremiertour=false;nombreTour++;
        } while ((calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0) && this.continuerConfigurationRames);
        return actionsNextRoundTemporaire;
    }

    public Marin hisserLaVoile(){
        for (Marin marin : parsedInitGame.getMarins()) {
            if (marin.isLibre()) { //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                for (Entity e : parsedInitGame.getBateau().getEntities()) {
                    if (e instanceof Voile) {
                        if (!((Voile) e).isOpenned()) {
                            MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), true);
                            if (moving != null) {
                                actionsNextRound.add(moving);
                                marin.setX(moving.getXdistance() + marin.getX());
                                marin.setY(moving.getYdistance() + marin.getY());
                                ((Voile) e).setOpenned(true);
                                parsedInitGame.getBateau().setPosition(new Position(this.parsedInitGame.getBateau().getPosition().getX() + vent.getStrength() * Math.cos(vent.getOrientation()), this.parsedInitGame.getBateau().getPosition().getY() + vent.getStrength() * Math.sin(vent.getOrientation()), parsedInitGame.getBateau().getPosition().getOrientation()));
                                return marin;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Marin affaisserLaVoile(){
        for (Marin marin : parsedInitGame.getMarins()) {
            if (marin.isLibre()) { //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                for (Entity e : parsedInitGame.getBateau().getEntities()) {
                    if (e instanceof Voile) {
                        if (((Voile) e).isOpenned()) {
                            MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), false);
                            if (moving != null) {
                                actionsNextRound.add(moving);
                                marin.setX(moving.getXdistance() + marin.getX());
                                marin.setY(moving.getYdistance() + marin.getY());
                                ((Voile) e).setOpenned(false);
                                return marin;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Marin utilisationVoile(){
        if (utilisationVoileOuiNon(parsedInitGame)) return hisserLaVoile();
        else return affaisserLaVoile();
    }

    public boolean utilisationVoileOuiNon(InitGame game){
        double xAvecVoile=game.getBateau().getPosition().getX() + vent.getStrength()*Math.cos(vent.getOrientation());
        double yAvecVoile=game.getBateau().getPosition().getY() + vent.getStrength()*Math.sin(vent.getOrientation());
        Position positionSiRameactive = new Position(xAvecVoile,yAvecVoile, game.getBateau().getPosition().getOrientation());
        return Math.sqrt(Math.pow(2,positionSiRameactive.getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,positionSiRameactive.getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY())) <=
                Math.sqrt(Math.pow(2,game.getBateau().getPosition().getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,game.getBateau().getPosition().getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY()));
    }

    public Marin configurationGouvernail(){
        for (Marin m : parsedInitGame.getMarins()){
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
