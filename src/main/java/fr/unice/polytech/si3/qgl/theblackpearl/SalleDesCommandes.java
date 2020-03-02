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
    private InitGame game;
    int meilleurAngleRealisablePosition=0;
    private boolean continuerConfigurationRames=true;

    public SalleDesCommandes(InitGame game, Vent vent){
        this.game=game;
        this.vent=vent;
    }

    public int[][] getTableauPositionPotentielleMarins() {
        return tableauPositionPotentielleMarins;
    }

    public void creationTableauMarins(){
        tableauPositionPotentielleMarins = new int[game.getMarins().size()][2];
    }
    public void priseEnComptePositionMarins(){
        for (int b=0;b<game.getMarins().size();b++){
            tableauPositionPotentielleMarins[b][0] = game.getMarins().get(b).getX();
            tableauPositionPotentielleMarins[b][1] = game.getMarins().get(b).getY();
        }
    }

    public void restaurationPositionMarins(){
        int positionnementMarins =0;
        for (Marin marin :game.getMarins()) {
            marin.setX(tableauPositionPotentielleMarins[positionnementMarins][0]);
            marin.setY(tableauPositionPotentielleMarins[positionnementMarins][1]);
            positionnementMarins++;
        }
    }

    public void preConfigurationRamesBateau(boolean neMarchePasPourLePremiertour, int nombreTour, int[] nombreMarinAplacerCopie, List<Double> meilleurAngleRealisable, Calculator calculateur, int meilleurAngleRealisablePosition, int IdMarinIntouchable) {
        if (nombreTour != 0) {
            if (this.meilleurAngleRealisablePosition < meilleurAngleRealisable.size() - 1) {
                if (!neMarchePasPourLePremiertour) {
                    restaurationPositionMarins();
                    for (Marin m : game.getMarins()) if (m.getId()!=IdMarinIntouchable) m.setLibre(true);
                    calculateur.setNombreMarinAplacer(nombreMarinAplacerCopie.clone());
                    calculateur.decrementationNombreMarinPlacer(nombreTour, true, true);
                }
                if (calculateur.getNombreMarinAplacer()[0] < 0 || calculateur.getNombreMarinAplacer()[1] < 0) {
                    ++meilleurAngleRealisablePosition;
                    this.meilleurAngleRealisablePosition = meilleurAngleRealisablePosition;
                    calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribordRames(meilleurAngleRealisable.get(meilleurAngleRealisablePosition), game.getBateau().getListRames()));
                } else this.continuerConfigurationRames = false;
            }
        }
    }

    public double configurationRames(InitGame game, List<Double> meilleurAngleRealisable, ArrayList<Action> actionsNextRound, int meilleurAngleRealisablePosition, int IdMarinIntouchable){
        this.meilleurAngleRealisablePosition=meilleurAngleRealisablePosition;Calculator calculateur = new Calculator();creationTableauMarins();this.continuerConfigurationRames=true;
        calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribordRames(meilleurAngleRealisable.get(0), game.getBateau().getListRames()));
        calculateur.setNombreMarinAplacerCopie(calculateur.getNombreMarinAplacer().clone());
        boolean neMarchePasPourLePremiertour = true;int nombreTour=0;boolean marinPlaceGauche=false;boolean marinPlaceDroite=false;
        priseEnComptePositionMarins();
        ArrayList<Entity> listeEntiteCopie;ArrayList<Action> actionsNextRoundTemporaire;
        do {
            actionsNextRoundTemporaire = new ArrayList<>();
            preConfigurationRamesBateau(neMarchePasPourLePremiertour,nombreTour,calculateur.getNombreMarinAplacerCopie(),meilleurAngleRealisable,calculateur,this.meilleurAngleRealisablePosition,IdMarinIntouchable);
            listeEntiteCopie = (ArrayList<Entity>) game.getBateau().getEntities().clone();
            //System.out.print("Avant " + Arrays.toString(calculateur.getNombreMarinAplacer()));
            for (Marin m : game.getMarins()){
                if (m.isLibre()) {
                    MOVING moving = m.deplacementMarinAllerRamer(listeEntiteCopie, calculateur.getNombreMarinAplacer()[0], calculateur.getNombreMarinAplacer()[1], (int) ((Rectangle) game.getBateau().getShape()).getWidth());
                    if (moving != null && (calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0)) {
                        if (moving.getYdistance() + m.getY() == 0 && calculateur.getNombreMarinAplacer()[0] > 0) { //Babord
                            calculateur.decrementationNombreMarinPlacer(1,true,false);
                            actionsNextRoundTemporaire.add(moving);marinPlaceGauche = true;
                        } else if (moving.getYdistance() + m.getY() != 0 && calculateur.getNombreMarinAplacer()[1] > 0) { //Tribord
                            calculateur.decrementationNombreMarinPlacer(1,false,true);
                            actionsNextRoundTemporaire.add(moving);marinPlaceDroite = true;
                        } else {
                            m.setLibre(true);
                        }
                        listeEntiteCopie=supprimerEntite(listeEntiteCopie, marinPlaceGauche, marinPlaceDroite, m, moving);
                        m.setX(moving.getXdistance() + m.getX());m.setY(moving.getYdistance() + m.getY());
                        marinPlaceGauche=false;marinPlaceDroite=false;
                    }
                }
            }
            neMarchePasPourLePremiertour=false;nombreTour++;
            //System.out.print("Après " + Arrays.toString(calculateur.getNombreMarinAplacer()) + "\n");
        } while ((calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0) && this.continuerConfigurationRames);
        actionsNextRound.addAll(actionsNextRoundTemporaire);
        setRamesUsed(game,listeEntiteCopie);//vérifier si ca marche
        // if (!this.continuerConfigurationRames) return meilleurAngleRealisable.get(this.meilleurAngleRealisablePosition-1);
        return meilleurAngleRealisable.get(this.meilleurAngleRealisablePosition);
    }

    public void utilisationVoile(InitGame parsedInitGame, List<Action> actionsNextRound){
        for (Marin marin : parsedInitGame.getMarins()) {
            if (marin.isLibre()) { //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                if (utilisationVoileOuiNon(parsedInitGame)) {
                    for (Entity e : parsedInitGame.getBateau().getEntities()) {
                        if (e instanceof Voile) {
                            if (!((Voile) e).isOpenned()) {
                                MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), true);
                                if (moving != null) {
                                    actionsNextRound.add(moving);
                                    marin.setX(moving.getXdistance() + marin.getX());
                                    marin.setY(moving.getYdistance() + marin.getY());
                                    ((Voile) e).setOpenned(true);
                                    parsedInitGame.getBateau().setPosition(new Position(game.getBateau().getPosition().getX() + vent.getStrength() * Math.cos(vent.getOrientation()), game.getBateau().getPosition().getY() + vent.getStrength() * Math.sin(vent.getOrientation()), parsedInitGame.getBateau().getPosition().getOrientation()));
                                    return;
                                }
                            }
                        }
                    }
                } else {//baisser la voile si c'est pas rentable
                    for (Entity e : parsedInitGame.getBateau().getEntities()) {
                        if (e instanceof Voile) {
                            if (((Voile) e).isOpenned()) {
                                MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), false);
                                if (moving != null) {
                                    actionsNextRound.add(moving);
                                    marin.setX(moving.getXdistance() + marin.getX());
                                    marin.setY(moving.getYdistance() + marin.getY());
                                    ((Voile) e).setOpenned(false);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean utilisationVoileOuiNon(InitGame game){
        double xAvecVoile=game.getBateau().getPosition().getX() + vent.getStrength()*Math.cos(vent.getOrientation());
        double yAvecVoile=game.getBateau().getPosition().getY() + vent.getStrength()*Math.sin(vent.getOrientation());
        Position positionSiRameactive = new Position(xAvecVoile,yAvecVoile, game.getBateau().getPosition().getOrientation());
        return Math.sqrt(Math.pow(2,positionSiRameactive.getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,positionSiRameactive.getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY())) <=
                Math.sqrt(Math.pow(2,game.getBateau().getPosition().getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.pow(2,game.getBateau().getPosition().getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY()));
    }

    public int configurationGouvernail(InitGame game,List<Action> actionsNextRound){
        for (Marin m :game.getMarins()){
            if (m.isLibre()){
                MOVING moving = m.deplacementMarinGouvernail(game.getBateau().getEntities());
                if (moving!=null){
                    actionsNextRound.add(moving);
                    m.setX(moving.getXdistance() + m.getX());
                    m.setY(moving.getYdistance() + m.getY());
                    // System.out.println(m.getId() + " "+ m.isLibre());
                    return m.getId();
                }
            }
        }
        return -1;
    }

}
