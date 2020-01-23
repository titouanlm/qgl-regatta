package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;

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

		//Update Shipe & Visibles Entities
		try {
			objectMapper = new ObjectMapper();
			NextRound nextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(nextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}


		List<Action> actionsNextRound = new ArrayList<>();
		// Positionne les marins de part et d'autres du bateau pour avoir la bonne poussée/orientation
		int nbMarinsPlacerBabord = 1; // a modifier avec les valeurs suite au calcul de la poussée
		int nbMarinsPlacerTribord = 1; // a modifier avec les valeurs suite au calcul de la poussée
		for (Marin m : parsedInitGame.getMarins()) {
			MOVING moving = m.planificationMarinAllerRamer(parsedInitGame.getBateau().getEntities(), nbMarinsPlacerBabord, nbMarinsPlacerTribord, (int) ((Rectangle) parsedInitGame.getBateau().getShape()).getWidth());
			if (moving != null) {
				if (moving.getYdistance()==0 && nbMarinsPlacerBabord > 0){
					nbMarinsPlacerBabord-=1;
					actionsNextRound.add(moving);
				}
				else if (moving.getYdistance()!=0 && nbMarinsPlacerTribord > 0){
					nbMarinsPlacerTribord-=1;
					actionsNextRound.add(moving);
				}
				else m.setLibre(true);
			}
		}
		//actionsNextRound.removeAll(Collections.singleton(null));

		//Creation of Actions
		for(Marin m : parsedInitGame.getMarins()){
			if (!m.getLibre()) {
				actionsNextRound.add(new OAR(m.getId()));
			}
		}

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
