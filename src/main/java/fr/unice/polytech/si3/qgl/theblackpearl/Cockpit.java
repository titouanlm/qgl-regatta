package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

public class Cockpit implements ICockpit {
	private InitGame parsedInitGame;
	private NextRound parsedNextRound;
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

	public String nextRound(String round) { //reinitialiser toute les actions à faire des marins
		try {
			parsedNextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(parsedNextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Vent vent = parsedNextRound.getVent();
		Captain captain = new Captain(parsedInitGame, vent);


		for (Marin marin : parsedInitGame.getMarins()) {
			marin.resetMarinPourUnNouveauTour();
		}

		for (Entity entite : parsedInitGame.getBateau().getEntities()){
			entite.setLibre(true);
		}

		List<Action> actionsNextRound = captain.captainFaitLeJob(parsedInitGame);
		for(Marin m : parsedInitGame.getMarins()){
			if (!m.isLibre()) {
				if (m.getActionAFaire().equals("Ramer")) {
					actionsNextRound.add(new OAR(m.getId()));
				}
				else if (m.getActionAFaire().equals("tournerGouvernail")){
					actionsNextRound.add(new TURN(m.getId(),parsedInitGame.getBateau().getGouvernail().getAngleRealise()));
				}
				else if (m.getActionAFaire().equals("HisserVoile")){
					actionsNextRound.add(new LIFT_SAIL(m.getId()));
				}
				else if (m.getActionAFaire().equals("BaisserLaVoile")){
					actionsNextRound.add(new LOWER_SAIL(m.getId()));
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

	@Override
	public List<String> getLogs() {
		return logs;
	}
}
