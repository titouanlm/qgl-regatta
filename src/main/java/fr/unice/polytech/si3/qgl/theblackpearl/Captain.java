package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Gouvernail;

import java.util.ArrayList;
import java.util.List;

public class Captain {

    private Calculator calculator;
    ArrayList<Action> actionsNextRound = new ArrayList<>();
    private double angleParfaitVersCheckpoint;
    private Checkpoint checkpointAViser;
    private SalleDesCommandes salleDesCommandes;

    public Captain(InitGame game, Vent vent) {
        calculator = new Calculator();
        salleDesCommandes = new SalleDesCommandes(game,vent);
    }

    public ArrayList<Action> captainFaitLeJob(InitGame parsedInitGame){
        this.determinerCheckpointAViser(parsedInitGame);
        double angleRealiseRames = salleDesCommandes.configurationRames(this.meilleurAngleRealisable(parsedInitGame), actionsNextRound,0);
        double resteAngleARealiser = this.angleParfaitVersCheckpoint - angleRealiseRames;
        Gouvernail gouvernail = parsedInitGame.getBateau().getGouvernail();
        gouvernail.setAngleRealise(gouvernail.angleGouvernail(resteAngleARealiser));
        if (gouvernail.getAngleRealise() != 0.0) {
            salleDesCommandes.configurationGouvernail(parsedInitGame,actionsNextRound);
        }
        return actionsNextRound;
    }

    public void determinerCheckpointAViser(InitGame parsedInitGame) {
        //1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
        if(parsedInitGame.getGoal() instanceof RegattaGoal){
            RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
            if(calculator.pointIsInsideCheckpoint(parsedInitGame.getBateau().getPosition(), regatta.getCheckpoints().get(0))){
                regatta.removeCheckpoint();
            }
            //Checkpoint à viser
            checkpointAViser = regatta.getCheckpoints().get(0);
        }
    }

    public List<Double> meilleurAngleRealisable(InitGame parsedInitGame){
        List<Double> meilleurAngleRealisable = new ArrayList<>();
        //2. Calculer l'orientation du bateau pour qu'il soit dans l'axe du prochain checkpoint
        if(checkpointAViser!=null){
            //3. Calculer la solution la plus optimale pour orienter correctement le bateau (tout en avancant si possible) avec les éléments à notre disposition
            angleParfaitVersCheckpoint=calculator.calculAngleIdeal(parsedInitGame.getBateau().getPosition(), checkpointAViser.getPosition());
            meilleurAngleRealisable = parsedInitGame.getBateau().meilleurAngleRealisable(angleParfaitVersCheckpoint);
        }
        return meilleurAngleRealisable;
    }

}
