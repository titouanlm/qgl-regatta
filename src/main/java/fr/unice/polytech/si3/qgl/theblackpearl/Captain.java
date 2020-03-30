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
    private ArrayList<Action> actionsNextRound = new ArrayList<>();
    private double angleParfaitVersCheckpoint;
    private Checkpoint checkpointAViser;
    private SalleDesCommandes salleDesCommandes;
    private List<Marin> marinsOccupes = new ArrayList<>();
    private InitGame parsedInitGame;

    public Captain(InitGame game, Vent vent) {
        parsedInitGame = game;
        calculator = new Calculator();
        salleDesCommandes = new SalleDesCommandes(game,vent,actionsNextRound);
    }

    public ArrayList<Action> ordreCapitaine(){
        this.determinerCheckpointAViser();
        marinsOccupes.add(salleDesCommandes.configurationGouvernail());
        marinsOccupes.add(salleDesCommandes.utilisationVoile());
        double angleRealiseRames = salleDesCommandes.configurationRames(this.meilleurAngleRealisable(), 0, marinsOccupes);
        double resteAngleARealiser = this.angleParfaitVersCheckpoint - angleRealiseRames;
        Gouvernail gouvernail = parsedInitGame.getBateau().getGouvernail();
        gouvernail.setAngleRealise(gouvernail.angleGouvernail(resteAngleARealiser));
        return actionsNextRound;
    }

    public void determinerCheckpointAViser() {
        //1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
        if(parsedInitGame.getGoal() instanceof RegattaGoal){
            RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
            if(calculator.shapeInCollision(parsedInitGame.getBateau(), regatta.getCheckpoints().get(0))){
                regatta.removeCheckpoint();
            }
            //Checkpoint à viser
            checkpointAViser = regatta.getCheckpoints().get(0);
        }
    }

    public List<Double> meilleurAngleRealisable(){
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
