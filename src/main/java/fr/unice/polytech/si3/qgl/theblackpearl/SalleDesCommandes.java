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

    public void preConfigurationRamesBateau(boolean neMarchePasPourLePremiertour, int nombreTour, int[] nombreMarinAplacerCopie, List<Double> meilleurAngleRealisable, Calculator calculateur, int meilleurAngleRealisablePosition){
        if (!neMarchePasPourLePremiertour){
            restaurationPositionMarins();
            calculateur.setNombreMarinAplacer(nombreMarinAplacerCopie.clone());
            calculateur.decrementationNombreMarinPlacer(nombreTour,true,true);
        }
        if (calculateur.getNombreMarinAplacer()[0] < 0 || calculateur.getNombreMarinAplacer()[1] < 0){
            ++meilleurAngleRealisablePosition;
            this.meilleurAngleRealisablePosition=meilleurAngleRealisablePosition;
            calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable.get(meilleurAngleRealisablePosition),game.getBateau().getListRames()));
        }

    }

    public double configurationRames(List<Double> meilleurAngleRealisable, ArrayList<Action> actionsNextRound, int meilleurAngleRealisablePosition){
        this.meilleurAngleRealisablePosition=meilleurAngleRealisablePosition;
        Calculator calculateur = new Calculator();
        creationTableauMarins();
        calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable.get(0), game.getBateau().getListRames()));
        calculateur.setNombreMarinAplacerCopie(calculateur.getNombreMarinAplacer().clone());
        boolean neMarchePasPourLePremiertour = true;
        int nombreTour=0;
        boolean marinPlaceGauche=false;
        boolean marinPlaceDroite=false;
        priseEnComptePositionMarins();
        ArrayList<Entity> listeEntiteCopie;
        do {
            preConfigurationRamesBateau(neMarchePasPourLePremiertour,nombreTour,calculateur.getNombreMarinAplacerCopie(),meilleurAngleRealisable,calculateur,this.meilleurAngleRealisablePosition);
            listeEntiteCopie = (ArrayList<Entity>) game.getBateau().getEntities().clone();
            for (Marin m : game.getMarins()){
                if (m.isLibre()) {
                    MOVING moving = m.deplacementMarinAllerRamer(listeEntiteCopie, calculateur.getNombreMarinAplacer()[0], calculateur.getNombreMarinAplacer()[1], (int) ((Rectangle) game.getBateau().getShape()).getWidth());
                    if (moving != null && (calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0)) {
                        if (moving.getYdistance() + m.getY() == 0 && calculateur.getNombreMarinAplacer()[0] > 0) { //Babord
                            calculateur.decrementationNombreMarinPlacer(1,true,false);
                            actionsNextRound.add(moving);marinPlaceGauche = true;
                        } else if (moving.getYdistance() + m.getY() != 0 && calculateur.getNombreMarinAplacer()[1] > 0) { //Tribord
                            calculateur.decrementationNombreMarinPlacer(1,false,true);
                            actionsNextRound.add(moving);marinPlaceDroite = true;
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
        } while (calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0);
        setRamesUsed(game,listeEntiteCopie);//vérifier si ca marche
        return meilleurAngleRealisable.get(this.meilleurAngleRealisablePosition);
    }

    public void utilisationVoile(InitGame parsedInitGame, List<Action> actionsNextRound){
        for (Marin marin : parsedInitGame.getMarins()) {
            if (marin.isLibre()) { //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                if (utilisationVoileOuiNon(parsedInitGame)) {
                    for (Entity e : parsedInitGame.getBateau().getEntities()) {
                        if (e instanceof Voile)
                            if (!((Voile) e).isOpenned()) {
                                MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), true);
                                if (moving != null) {
                                    actionsNextRound.add(moving);
                                    marin.setX(moving.getXdistance() + marin.getX());
                                    marin.setY(moving.getYdistance() + marin.getY());
                                    ((Voile) e).setOpenned(true);
                                    return;
                                }
                            }
                    }
                } else {//baisser la voile si c'est pas rentable
                    for (Entity e : parsedInitGame.getBateau().getEntities()) {
                        if (e instanceof Voile)
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

    public boolean utilisationVoileOuiNon(InitGame game){
        double xAvecVoile=game.getBateau().getPosition().getX() + vent.getStrength()*Math.cos(vent.getOrientation());
        double yAvecVoile=game.getBateau().getPosition().getY() + vent.getStrength()*Math.sin(vent.getOrientation());
        Position positionSiRameactive = new Position(xAvecVoile,yAvecVoile, game.getBateau().getPosition().getOrientation());
        return Math.abs(positionSiRameactive.getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.abs(positionSiRameactive.getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY()) <=
                Math.abs(game.getBateau().getPosition().getX()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getX()) + Math.abs(game.getBateau().getPosition().getY()-((RegattaGoal) game.getGoal()).getCheckpoints().get(0).getPosition().getY());
    }

    public void configurationGouvernail(InitGame game,List<Action> actionsNextRound){
        for (Marin m :game.getMarins()){
            if (m.isLibre()){
                MOVING moving = m.deplacementMarinGouvernail(game.getBateau().getEntities());
                if (moving!=null){
                    actionsNextRound.add(moving);
                    m.setX(moving.getXdistance() + m.getX());
                    m.setY(moving.getYdistance() + m.getY());
                    return;
                }
            }
        }
    }


}
