package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.TURN;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Gouvernail;

public class Cockpit implements ICockpit {
	private InitGame parsedInitGame;
	private NextRound parsedNextRound;
	private ObjectMapper objectMapper;
	private List<String> logs;
	private Captain captain;


	public Cockpit(){
		objectMapper = new ObjectMapper();
		logs = new ArrayList<>();
		captain = new Captain();
	}

	public void initGame(String game) {
		try {
			parsedInitGame = objectMapper.readValue(game, InitGame.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {
		captain.resetCapitain();
		try {
			parsedNextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(parsedNextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		for (Marin marin : parsedInitGame.getMarins()) {
			marin.resetMarinPourUnNouveauTour();
			parsedInitGame.getBateau().getListRames().get(i).setUsed(false);
			i++;
		}
		List<Action> actionsNextRound = captain.captainFaitLeJob(parsedInitGame);
		for(Marin m : parsedInitGame.getMarins()){
			if (!m.isLibre()) {
				if (m.getActionAFaire().equals("Ramer")) {
					actionsNextRound.add(new OAR(m.getId()));
					parsedNextRound.getBateau().initRameUsed(parsedInitGame.getMarins());
				}
				else if (m.getActionAFaire().equals("tournerGouvernail")){
					actionsNextRound.add(new TURN(m.getId(),captain.getAngleRealiseGouvernail()));
				}
			}
		}

		StringBuilder roundJSON=creationJson(actionsNextRound);
		return roundJSON.toString();
	}

	public StringBuilder creationJson(List <Action> actionsNextRound){
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

	public Captain getCaptain() {
		return captain;
	}

	@Override
	public List<String> getLogs() {
		return logs;
	}

	public NextRound getParsedNextRound() {
		return parsedNextRound;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public InitGame getParsedInitGame() {
		return parsedInitGame;
	}
}
