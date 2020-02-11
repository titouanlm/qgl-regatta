package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;

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

		try {
			parsedNextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(parsedNextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		int i = 0;
		for (Marin marin : parsedInitGame.getMarins()) {
			marin.setLibre(true);
			parsedInitGame.getBateau().getListRames().get(i).setUsed(false);
			i++;
		}

		double[] meilleurAngleRealisable = captain.meilleurAngleRealisable(parsedInitGame);
		List<Action> actionsNextRound ;
		actionsNextRound = captain.configurationBateau(meilleurAngleRealisable, parsedInitGame);

		for(Marin m : parsedInitGame.getMarins()){
			if (!m.isLibre()) {
				actionsNextRound.add(new OAR(m.getId()));
				parsedNextRound.getBateau().initRameUsed(parsedInitGame.getMarins());
			}
		}

		StringBuilder roundJSON=creationJson(actionsNextRound);
		return roundJSON.toString();
	}

	public StringBuilder creationJson(List <Action> actionsNextRound){
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
}
