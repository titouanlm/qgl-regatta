package fr.unice.polytech.si3.qgl.theblackpearl.decisions;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.*;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.*;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.*;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.*;

import java.util.ArrayList;
import java.util.List;


public class Captain {

    private Calculator calculator;
    private ArrayList<Action> actionsNextRound = new ArrayList<>();
    private double angleParfaitVersCheckpoint;
    private Checkpoint checkpointAViser;
    private ControlRoom controlRoom;
    private List<Sailor> marinsOccupes = new ArrayList<>();
    private InitGame parsedInitGame;

    public Captain(InitGame game, Wind wind) {
        parsedInitGame = game;
        calculator = new Calculator();
        controlRoom = new ControlRoom(game, wind,actionsNextRound);
    }

    public ArrayList<Action> ordreCapitaine() throws Exception {
        if (this.determinerCheckpointAViser()){
            if (controlRoom.isThereASail())
                marinsOccupes.add(controlRoom.utilisationVoile());
            if (controlRoom.isThereARudder())
                marinsOccupes.add(controlRoom.configurationGouvernail());
            double angleRealiseRames = controlRoom.configurationRames(this.meilleurAngleRealisable(), 0, marinsOccupes);
            if (controlRoom.isThereARudder()) {
                double resteAngleARealiser = this.angleParfaitVersCheckpoint - angleRealiseRames;
                Rudder rudder = parsedInitGame.getBateau().getGouvernail();
                rudder.setAngleRealise(rudder.angleGouvernail(resteAngleARealiser));
            }
            return actionsNextRound;
        }
        else return null;
    }

    public boolean determinerCheckpointAViser() throws Exception {
        //1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
        if(parsedInitGame.getGoal() instanceof RegattaGoal){
            RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
            Calculator c = new Calculator();
            if (c.shipIsInsideCheckpoint(parsedInitGame.getBateau(),regatta.getCheckpoints().get(0))){
                regatta.removeCheckpoint();
            }

            //Checkpoint à viser
            if (regatta.getCheckpoints().size() != 0){
                checkpointAViser = regatta.getCheckpoints().get(0);
                return true;
            }
        }
        return false;
    }

    public List<Double> meilleurAngleRealisable(){
        List<Double> meilleurAngleRealisable = new ArrayList<>();
        //2. Calculer l'orientation du bateau pour qu'il soit dans l'axe du prochain checkpoint
        if(checkpointAViser!=null){
            //3. Calculer la solution la plus optimale pour orienter correctement le bateau (tout en avancant si possible) avec les éléments à notre disposition
            angleParfaitVersCheckpoint=calculator.calculateIdealAngle(parsedInitGame.getBateau().getPosition(), checkpointAViser.getPosition());
            meilleurAngleRealisable = parsedInitGame.getBateau().meilleurAngleRealisable(angleParfaitVersCheckpoint);
        }
        return meilleurAngleRealisable;
    }

}
