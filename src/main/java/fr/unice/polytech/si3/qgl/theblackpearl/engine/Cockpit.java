package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Goal;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

public class Cockpit implements ICockpit {
	private Game parsedGame;
	private ObjectMapper objectMapper;

	public Cockpit(){
		objectMapper = new ObjectMapper();
	}

	public void initGame(String game) {
		try {
			parsedGame = objectMapper.readValue(game, Game.class);

			// Just to see if we have correctly parsed the data from Json file
			System.out.println(parsedGame);
			System.out.println(parsedGame.getBateau().getType());
			System.out.println(parsedGame.getBateau().getLife());
			System.out.println(parsedGame.getBateau().getName());
			// some more tests to do ...

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {

		//Update Shipe & Visibles Entities
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			NextRound nextRound = objectMapper.readValue(round, NextRound.class);
			parsedGame.setBateau(nextRound.getBateau());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//Creation of Actions
		List<Action> actionsNextRound = new ArrayList<>();
		for(Marin m : parsedGame.getMarins()){
			actionsNextRound.add(new OAR(m.getId()));
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
	public String toString() {
		return "Cockpit{" +
				"parsedGame=" + parsedGame +
				'}';
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
