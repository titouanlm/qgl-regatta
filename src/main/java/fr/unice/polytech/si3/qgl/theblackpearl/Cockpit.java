package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.referee.Referee2;

public class Cockpit implements ICockpit {
	private InitGame parsedInitGame;
	private NextRound parsedNextRound;
	private ObjectMapper objectMapper;
	private List<String> logs;
	private Referee2 ref;
	private ArrayList<Marin> marinsClone;


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

	public String nextRound(String round) { //reinitialiser toutes les actions à faire des marins
		try {
			parsedNextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(parsedNextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//Copie l'état des marins avant de les bouger (sert dans le détecteur de colision)
		copyOfSailorsBeforeMove();
		resetMarinNouveauTour();
		creerLogNouveautour();

		Captain captain = new Captain(parsedInitGame, parsedNextRound.getWind());

		List<Action> actionsNextRound = null;
		try {
			actionsNextRound = captain.ordreCapitaine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tacheMarins(Objects.requireNonNull(actionsNextRound));
		StringBuilder roundJSON=creationJson(Objects.requireNonNull(actionsNextRound));

		while(true){
			InitGame initGameClone = parsedInitGame.clone();
			initGameClone.setMarins(marinsClone);
			ref = new Referee2(initGameClone, parsedNextRound.clone());
			try {
				if (ref.startRound(roundJSON.toString())) /*modificationJsonObstacles(roundJSON)*/;
				else break;
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(0);
			//this.logs.add("********** COLLISION DETECTE ");
			//ON CHANGE LE CAP ICI ** roundJSON a modifier **
		}

		return roundJSON.toString();
	}

	private void copyOfSailorsBeforeMove() {
		marinsClone = new ArrayList<>();
		for(Marin m : parsedInitGame.getMarins()){
			marinsClone.add(m.clone());
		}
	}

	/*public void modificationJsonObstacles(StringBuilder roundJson){ // À faire
		resetMarinNouveauTour();
		Captain captain = new Captain(parsedInitGame, parsedNextRound.getWind());
	}*/

	public void resetMarinNouveauTour(){
		for (Marin marin : parsedInitGame.getMarins()) {
			marin.resetMarinPourUnNouveauTour();
		}
	}

	public void creerLogNouveautour(){
		StringBuilder log = new StringBuilder();
		for (Marin marin : parsedInitGame.getMarins()) {
			log.append(marin.toString());
		}
		logs.add(log.toString());
	}

	public void tacheMarins(List<Action> actionsNextRound){
		for(Marin m : parsedInitGame.getMarins()){
			if (!m.isLibre()) {
				switch (m.getActionAFaire()) {
					case "Ramer":
						actionsNextRound.add(new OAR(m.getId()));
						break;
					case "tournerGouvernail":
						actionsNextRound.add(new TURN(m.getId(), parsedInitGame.getBateau().getGouvernail().getAngleRealise()));
						break;
					case "HisserVoile":
						actionsNextRound.add(new LIFT_SAIL(m.getId()));
						break;
					case "BaisserLaVoile":
						actionsNextRound.add(new LOWER_SAIL(m.getId()));
						break;
				}
			}
		}
	}

	public StringBuilder creationJson(List<Action> actionsNextRound){
		StringBuilder roundJSON= new StringBuilder("[");
		try {
			for(int i=0; i<actionsNextRound.size(); i++){
				if (actionsNextRound.get(i) instanceof TURN) roundJSON.append(actionsNextRound.get(i).toString());
				else roundJSON.append(objectMapper.writeValueAsString(actionsNextRound.get(i)));
				if(i!=actionsNextRound.size()-1){
					roundJSON.append(",");
				}
			}
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		roundJSON.append("]");

		return roundJSON;
	}

	@Override
	public List<String> getLogs() {
		return logs;
	}

	public InitGame getParsedInitGame() {
		return parsedInitGame;
	}
}