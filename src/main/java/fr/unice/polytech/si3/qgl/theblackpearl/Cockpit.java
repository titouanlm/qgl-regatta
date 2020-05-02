package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.*;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Calculator;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Captain;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.referee.Referee2;

public class Cockpit implements ICockpit {
	private InitGame parsedInitGame;
	private NextRound parsedNextRound;
	private ObjectMapper objectMapper;
	private List<Action> actionsNextRound;
	private List<String> logs;
	private Referee2 ref;


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

		//Copie l'état du bateau et de toutes les entités avant de les modifier (utile dans détection de colision)
		InitGame initGameDebutTour = parsedInitGame.clone();

		resetMarinNouveauTour();
		creerLogNouveautour();

		Captain captain = new Captain(parsedInitGame, parsedNextRound.getWind());

		try {
			actionsNextRound = captain.ordreCapitaine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tacheMarins(Objects.requireNonNull(actionsNextRound));
		StringBuilder roundJSON=creationJson(Objects.requireNonNull(actionsNextRound));
		StringBuilder saveRoundJSON = roundJSON;

		while(true){
			InitGame initGameClone = initGameDebutTour.clone();
			ref = new Referee2(initGameClone, parsedNextRound.clone());
			Calculator c = new Calculator();
			try {
				if (!ref.startRound(roundJSON.toString())){
					RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
					if(ref.getGoThroughCheckpoint() && !c.shapeInCollision(initGameClone.getBateau(),regatta.getCheckpoints().get(0))){
						roundJSON = modificationJsonRalentir();
					}else{
						break;
					}
				}else{
					//collision
					roundJSON = modificationJsonObstacles();
				}
			} catch (Exception e) {
				roundJSON = saveRoundJSON;
				break;
			}
			//System.exit(0);
		}

		return roundJSON.toString();
	}

	private StringBuilder modificationJsonRalentir() {
		StringBuilder newRoundJson;
		Action actionOARLeft =null;
		Action actionOARRight = null;

		for(Action a : actionsNextRound){
			if(a instanceof OAR){
				Marin sailorOAR = parsedInitGame.getSailorById(a.getSailorId());
				if(actionOARLeft==null && sailorOAR.getY() == parsedInitGame.getBateau().getDeck().getWidth()-1){
					actionOARLeft = a;
				}
				if(actionOARRight==null && sailorOAR.getY() == 0){
					actionOARRight = a;
				}
			}
		}
		if(actionOARRight!=null && actionOARLeft!=null){
			actionsNextRound.remove(actionOARRight);
			actionsNextRound.remove(actionOARLeft);
			newRoundJson = creationJson(actionsNextRound);
		}else{
			newRoundJson = null;
		}
		return newRoundJson;
	}

	public StringBuilder modificationJsonObstacles(){ // À faire
		return null;
	}

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
