package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

import java.util.ArrayList;

import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity.supprimerEntite;
import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame.setRamesUsed;

public class SalleDesCommandes {

    private int[][] tableauPositionPotentielleMarins;
    private Vent vent;
    private InitGame game;

    public SalleDesCommandes(InitGame game, Vent vent){
        this.game=game;
        tableauPositionPotentielleMarins = new int[game.getMarins().size()][2];
        this.vent=vent;
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

    public void preConfigurationRamesBateau(boolean neMarchePasPourLePremiertour, int nombreTour, int[] nombreMarinAplacerCopie, double meilleurAngleRealisable[], Calculator calculateur, ArrayList<Action> actionsNextRound, int meilleurAngleRealisablePosition){
        if (!neMarchePasPourLePremiertour){
            restaurationPositionMarins();
            calculateur.setNombreMarinAplacer(nombreMarinAplacerCopie.clone());
            calculateur.decrementationNombreMarinPlacer(nombreTour,true,true);
        }
        if (calculateur.getNombreMarinAplacer()[0] < 0 || calculateur.getNombreMarinAplacer()[1] < 0){
            ++meilleurAngleRealisablePosition;
            calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable[meilleurAngleRealisablePosition], game.getMarins().size(),game.getBateau().getListRames()));
        }

    }

    public double configurationRames(double meilleurAngleRealisable[], ArrayList<Action> actionsNextRound, int meilleurAngleRealisablePosition){
        Calculator calculateur = new Calculator();
        calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable[0], game.getMarins().size(),game.getBateau().getListRames()));
        calculateur.setNombreMarinAplacerCopie(calculateur.getNombreMarinAplacer().clone());
        boolean neMarchePasPourLePremiertour = true;int nombreTour=0;boolean marinPlaceGauche=false; boolean marinPlaceDroite=false;
        priseEnComptePositionMarins();
        ArrayList<Entity> listeEntiteCopie;
        do {
            preConfigurationRamesBateau(neMarchePasPourLePremiertour,nombreTour,calculateur.getNombreMarinAplacerCopie(),meilleurAngleRealisable,calculateur, actionsNextRound,meilleurAngleRealisablePosition);
            listeEntiteCopie = (ArrayList<Entity>) game.getBateau().getEntities().clone();
            for (Marin m : game.getMarins()){
                if (m.isLibre()) {
                    MOVING moving = m.deplacementMarinAllerRamer(listeEntiteCopie, calculateur.getNombreMarinAplacer()[0], calculateur.getNombreMarinAplacer()[1], (int) ((Rectangle) game.getBateau().getShape()).getWidth());
                    if (moving != null && (calculateur.getNombreMarinAplacer()[0] != 0 | calculateur.getNombreMarinAplacer()[1] != 0)) {
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
        return meilleurAngleRealisable[meilleurAngleRealisablePosition];
    }

    public boolean utilisationRameOuiNon(InitGame game){
        double xAvecVoile=game.getBateau().getPosition().getX() + vent.getStrength()*Math.cos(vent.getOrientation());
        double yAvecVoile=game.getBateau().getPosition().getX() + vent.getStrength()*Math.sin(vent.getOrientation());
        Position positionSiRameactive = new Position(xAvecVoile,yAvecVoile, game.getBateau().getPosition().getOrientation());
        return estUnePositionRentable(positionSiRameactive,game.getBateau().getPosition(),((RegattaGoal) game.getGoal()).getCheckpoints().get(0));
    }

    public boolean estUnePositionRentable(Position position, Position position2, Checkpoint chekpoint){
        return Math.abs(position.getX()-chekpoint.getPosition().getX()) + Math.abs(position.getY()-chekpoint.getPosition().getY()) <=
                Math.abs(position2.getX()-chekpoint.getPosition().getX()) + Math.abs(position2.getY()-chekpoint.getPosition().getY());
    }


    public boolean possibiliteSeRendreVoile(ArrayList<Action> actionsNextRound){
        for (Marin m :game.getMarins()){
            if (m.isLibre()){
                MOVING moving = m.deplacementMarinGouvernail(game.getBateau().getEntities());
                if (moving!=null){
                    actionsNextRound.add(moving);
                    m.setX(moving.getXdistance() + m.getX());m.setY(moving.getYdistance() + m.getY());
                    return true;
                }
            }
        }
        return false;
    }


}
