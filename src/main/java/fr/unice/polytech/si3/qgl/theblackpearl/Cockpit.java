package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;

public class Cockpit implements ICockpit {
	private InitGame parsedInitGame;
	private ObjectMapper objectMapper;
	private List<String> logs;

	public Cockpit(){
		objectMapper = new ObjectMapper();
		logs = new ArrayList<>();
	}

	public void initGame(String game) {
		try {
			parsedInitGame = objectMapper.readValue(game, InitGame.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {

		//Update Ship & Visibles Entities
		try {
			NextRound nextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(nextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
		if(parsedInitGame.getGoal() instanceof RegattaGoal){
			RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
			if(regatta.shipIsInsideCheckpoint(parsedInitGame.getBateau())){
					regatta.removeCheckpoint();
			}
			//2. Calculer l'orientation du bateau pour qu'il soit dans l'axe du prochain checkpoint
			Checkpoint nextCheckpoint = regatta.getCheckpoints().get(0);
			if(nextCheckpoint!=null){
				Position pCheck = nextCheckpoint.getPosition();
				double angleIdeal = Math.atan2(pCheck.getY()-parsedInitGame.getBateau().getPosition().getY(),pCheck.getX()-parsedInitGame.getBateau().getPosition().getX()) - parsedInitGame.getBateau().getPosition().getOrientation();
				System.out.println(angleIdeal);
				System.out.println(parsedInitGame.getBateau().nbMarinRameBabord(parsedInitGame.getMarins()));
				System.out.println(parsedInitGame.getBateau().nbMarinRameTribord(parsedInitGame.getMarins()));

				System.out.println(parsedInitGame.getBateau().meilleurAngleRealisable(angleIdeal));
				//3. Calculer la solution la plus optimale pour orienter correctement le bateau (tout en avancant si possible) avec les éléments à notre disposition
				for(double angle : parsedInitGame.getBateau().meilleurAngleRealisable(angleIdeal)){
					if(angle == parsedInitGame.getBateau().getPosition().getOrientation()){
						//parsedInitGame.getBateau().allerToutDroit(parsedInitGame.getMarins());
						break;
					}else{

//						if(!=null){
//							break;
//						}
					}
				}
			}

		}

        //4. Faire et Renvoyer les actions pour le tour d'après

        // TESTER LE CODE DANS PLUSIEURS CAS

		//Creation of Actions
		List<Action> actionsNextRound = new ArrayList<>();


		//Creation of actions JSON file
		StringBuilder roundJSON= new StringBuilder("[");
		try {
			for(int i=0; i<actionsNextRound.size(); i++){
				roundJSON.append(objectMapper.writeValueAsString(actionsNextRound.get(i)));
				if(i!=actionsNextRound.size()-1){
					roundJSON.append(",");
				}
			}
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		roundJSON.append("]");

		return roundJSON.toString();
	}

	@Override
	public List<String> getLogs() {
		return logs;
	}
}
