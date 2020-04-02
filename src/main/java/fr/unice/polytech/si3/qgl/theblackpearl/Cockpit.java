package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.referee.Referee2;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.VisibleEntity;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;

public class Cockpit implements ICockpit {
	private InitGame parsedInitGame;
	private NextRound parsedNextRound;
	private ObjectMapper objectMapper;
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


		Captain captain = new Captain(parsedInitGame, parsedNextRound.getWind());

		creerLogNouveautour();

		List<Action> actionsNextRound = captain.ordreCapitaine();
		tacheMarins(actionsNextRound);

		StringBuilder roundJSON=creationJson(actionsNextRound);


		InitGame cloneInitGame =  parsedInitGame.clone();
		NextRound cloneNextRound = parsedNextRound.clone();
		ref = new Referee2(cloneInitGame, cloneNextRound);
		while(ref.startRound(roundJSON.toString())){
			ref = new Referee2(cloneInitGame, cloneNextRound);
			System.exit(0);
			//ON CHANGE LE CAP ICI ** roundJSON a modifier **
		}

		return roundJSON.toString();
	}

	public void creerLogNouveautour(){
		StringBuilder log = new StringBuilder();
		for (Marin marin : parsedInitGame.getMarins()) {
			marin.resetMarinPourUnNouveauTour();
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
