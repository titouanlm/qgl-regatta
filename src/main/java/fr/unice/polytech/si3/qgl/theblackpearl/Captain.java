package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

import java.util.ArrayList;
import java.util.zip.InflaterInputStream;

public class Captain {

    private int[][] tableauPositionPotentielleMarins;
    private Calculator calculator;
    ArrayList<Action> actionsNextRound;
    int meilleurAngleRealisablePosition;
    private double angleParfaitVersCheckpoint;

    public double getAngleRealiseGouvernail() {
        return angleRealiseGouvernail;
    }

    private double angleRealiseGouvernail;

    public Captain() {
        calculator = new Calculator();
    }

    public ArrayList<Action> captainFaitLeJob(InitGame parsedInitGame){
        double[] meilleurAngleRealisable = meilleurAngleRealisable(parsedInitGame);
        double angleParfait = angleParfait(parsedInitGame);
        double angleRealiseRames = configurationBateau(meilleurAngleRealisable, parsedInitGame);
        double resteAngleARealiser = angleParfait - angleRealiseRames;
        angleRealiseGouvernail = angleGouvernail(resteAngleARealiser);
        if (angleRealiseGouvernail != 0.0) configurationGouvernail(parsedInitGame,angleRealiseGouvernail);
        return actionsNextRound;
    }

    public ArrayList<Entity> supprimerEntite(ArrayList<Entity> listeEntite, boolean marinPlaceGauche, boolean marinPlaceDroite, Marin m, MOVING moving){
        if (listeEntite!=null) {
            for (int b = 0; b < listeEntite.size(); b++) { // supprimer la rame utilisée pour cette configuration
                if (listeEntite.get(b) instanceof Rame && (marinPlaceGauche | marinPlaceDroite)) {
                    if ((listeEntite.get(b).getY() - m.getY()) == moving.getYdistance() && (listeEntite.get(b).getX() - m.getX()) == moving.getXdistance()) {
                        game.getBateau().getEntities().get(b).setLibre(false);
                        listeEntite.remove(b);
                        break;
                    }
                }
            }
            return listeEntite;
        }
        else return null;
    }

    public void resetCapitain(){
        actionsNextRound=null;
        tableauPositionPotentielleMarins=null;
        calculator=new Calculator();
        meilleurAngleRealisablePosition=0;
    }

    public void priseEnComptePositionMarins(InitGame game){
        tableauPositionPotentielleMarins = new int[game.getMarins().size()][2];
        for (int b=0;b<game.getMarins().size();b++){
            tableauPositionPotentielleMarins[b][0] = game.getMarins().get(b).getX();
            tableauPositionPotentielleMarins[b][1] = game.getMarins().get(b).getY();
        }
    }

    public void restaurationPositionMarins(InitGame game){
        int positionnementMarins =0;
        for (Marin marin :game.getMarins()) {
            marin.setX(tableauPositionPotentielleMarins[positionnementMarins][0]);
            marin.setY(tableauPositionPotentielleMarins[positionnementMarins][1]);
            positionnementMarins++;
        }
    }

    public void preConfigurationBateau(InitGame game, boolean neMarchePasPourLePremiertour, int nombreTour,int[] nombreMarinAplacerCopie,double meilleurAngleRealisable[], Calculator calculateur){
        actionsNextRound=new ArrayList<>();
        if (!neMarchePasPourLePremiertour){
            restaurationPositionMarins(game);
            calculateur.setNombreMarinAplacer(nombreMarinAplacerCopie.clone());
            calculateur.decrementationNombreMarinPlacer(nombreTour,true,true);
        }
        if (calculateur.getNombreMarinAplacer()[0] < 0 || calculateur.getNombreMarinAplacer()[1] < 0){
            ++meilleurAngleRealisablePosition;
            calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable[meilleurAngleRealisablePosition], game.getMarins().size(),game.getBateau().getListRames()));
        }

    }

    public double configurationBateau(double meilleurAngleRealisable[], InitGame game){
        Calculator calculateur = new Calculator();
        calculateur.setNombreMarinAplacer(game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable[0], game.getMarins().size(),game.getBateau().getListRames()));
        calculateur.setNombreMarinAplacerCopie(calculateur.getNombreMarinAplacer().clone());
        boolean neMarchePasPourLePremiertour = true;int nombreTour=0;boolean marinPlaceGauche=false; boolean marinPlaceDroite=false;
        priseEnComptePositionMarins(game);
        do {
            preConfigurationBateau(game,neMarchePasPourLePremiertour,nombreTour,calculateur.getNombreMarinAplacerCopie(),meilleurAngleRealisable,calculateur);
            ArrayList<Entity> listeEntiteCopie = (ArrayList<Entity>) game.getBateau().getEntities().clone();
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
                    listeEntite=supprimerEntite(listeEntite, marinPlaceGauche, marinPlaceDroite, m, moving);
                    m.setX(moving.getXdistance() + m.getX());
                    m.setY(moving.getYdistance() + m.getY());
                    marinPlaceGauche=false;
                    marinPlaceDroite=false;
                }
            }
            neMarchePasPourLePremiertour=false;nombreTour++;
        } while (calculateur.getNombreMarinAplacer()[0] != 0 || calculateur.getNombreMarinAplacer()[1] != 0);
        return meilleurAngleRealisable[meilleurAngleRealisablePosition];
    }

    public double angleGouvernail(double angleAFaire) {
        if (angleAFaire == 0) return 0.0;
        else if ((angleAFaire == Math.PI/2) || (angleAFaire == Math.PI)) return 45 * Math.PI / 180;
        else if (angleAFaire == -Math.PI/2) return -45 * Math.PI / 180;
        else if (angleAFaire > 0 && angleAFaire > 45 * Math.PI / 180) return 45 * Math.PI / 180;
        else if (angleAFaire > 0) return angleAFaire % (45 * Math.PI / 180);
        else if (angleAFaire < 0 && angleAFaire < -45 * Math.PI / 180) return -45 * Math.PI / 180;
        else return -angleAFaire % (45 * Math.PI / 180);
    }

    public void configurationGouvernail(InitGame game,double angleAFaire){
        for (Marin m :game.getMarins()){
            if (m.isLibre()){
                MOVING moving = m.deplacementMarinGouvernail(game.getBateau().getEntities());
                if (moving!=null){
                    actionsNextRound.add(moving);
                    m.setX(moving.getXdistance() + m.getX());m.setY(moving.getYdistance() + m.getY());
                    return;
                }
            }
        }
    }

    public double[] meilleurAngleRealisable(InitGame parsedInitGame){
        double meilleurAngleRealisable[] = new double[parsedInitGame.getBateau().getListRames().size()+1];

        //1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
        if(parsedInitGame.getGoal() instanceof RegattaGoal){
            RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
            if(calculator.pointIsInsideCheckpoint(parsedInitGame.getBateau().getPosition(), regatta.getCheckpoints().get(0))){
                regatta.removeCheckpoint();
            }
            //2. Calculer l'orientation du bateau pour qu'il soit dans l'axe du prochain checkpoint
            Checkpoint nextCheckpoint = regatta.getCheckpoints().get(0);
            if(nextCheckpoint!=null){
                //3. Calculer la solution la plus optimale pour orienter correctement le bateau (tout en avancant si possible) avec les éléments à notre disposition
                int compteur=0;
                angleParfaitVersCheckpoint=calculator.calculAngleIdeal(parsedInitGame.getBateau().getPosition(), nextCheckpoint.getPosition());
                for(double angleOptimal : parsedInitGame.getBateau().meilleurAngleRealisable(calculator.calculAngleIdeal(parsedInitGame.getBateau().getPosition(), nextCheckpoint.getPosition()))){
                    meilleurAngleRealisable[compteur] = angleOptimal;
                    compteur++;
                }
            }
        }
        return meilleurAngleRealisable;
    }

    public double angleParfait(InitGame parsedInitGame){ // A appeler toujours après meilleurAngleRealisable
        return angleParfaitVersCheckpoint;
    }

}
