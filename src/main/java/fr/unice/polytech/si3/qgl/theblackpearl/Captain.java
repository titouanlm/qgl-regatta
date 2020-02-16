package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.seaElements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Gouvernail;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Voile;

import java.util.ArrayList;

public class Captain {

    private Calculator calculator;
    ArrayList<Action> actionsNextRound = new ArrayList<>();
    int meilleurAngleRealisablePosition;
    private double angleParfaitVersCheckpoint;
    private SalleDesCommandes salleDesCommandes;

    public Captain(InitGame game, Vent vent) {
        calculator = new Calculator();
        salleDesCommandes = new SalleDesCommandes(game,vent);
    }

    public ArrayList<Action> captainFaitLeJob(InitGame parsedInitGame){
        double[] meilleurAngleRealisable = meilleurAngleRealisable(parsedInitGame);
        double angleParfait = angleParfait(parsedInitGame);
        double angleRealiseRames = salleDesCommandes.configurationRames(meilleurAngleRealisable, actionsNextRound,meilleurAngleRealisablePosition);
        double resteAngleARealiser = angleParfait - angleRealiseRames;
        Gouvernail gouvernail = parsedInitGame.getBateau().getGouvernail();
        gouvernail.setAngleRealise(gouvernail.angleGouvernail(resteAngleARealiser));
        if (gouvernail.getAngleRealise() != 0.0) if(!salleDesCommandes.possibiliteSeRendreVoile(actionsNextRound)){
            gouvernail.setAngleRealise(0.0);
        }
        utilisationVoile(parsedInitGame);
        return actionsNextRound;
    }

    public void utilisationVoile(InitGame parsedInitGame){
        for (Marin marin : parsedInitGame.getMarins())
            if (marin.isLibre()) //calcul nouvelle postion du bateau si l'on utilise la voile, si pas rentable on ne la prend pas
                if (marin.peutSeRendreALavoile(parsedInitGame.getBateau().getEntities()))
                    if(salleDesCommandes.utilisationRameOuiNon(parsedInitGame)){
                        for (Entity e : parsedInitGame.getBateau().getEntities()) {
                            if (e instanceof Voile)
                                if (!((Voile) e).isOpenned()) {
                                    MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), true);
                                    if (moving != null) actionsNextRound.add(moving);
                                }
                        }
                    }
                    else {//baisser la voile si c'est pas rentable
                        for (Entity e : parsedInitGame.getBateau().getEntities()) {
                            if (e instanceof Voile)
                                if (((Voile) e).isOpenned()) {
                                    MOVING moving = marin.deplacementMarinVoile(parsedInitGame.getBateau().getEntities(), false);
                                    if (moving != null) actionsNextRound.add(moving);
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
    // APPELER CETTE FONCTION TOUJOURS APRES MEILLEUR ANGLE REALISABLE

}
