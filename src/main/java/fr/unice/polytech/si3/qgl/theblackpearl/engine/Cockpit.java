package fr.unice.polytech.si3.qgl.theblackpearl.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Goal;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;

public class Cockpit implements ICockpit {

	public void initGame(String game) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Game parsedGame = objectMapper.readValue(game, Game.class);

			// Just to see if we have correctly parsed the data from Json file
			System.out.println(parsedGame.getbBateau().getType());
			System.out.println(parsedGame.getbBateau().getLife());
			System.out.println(parsedGame.getbBateau().getName());
			// some more tests to do ...

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			NextRound nextRound = objectMapper.readValue(round, NextRound.class);

			// Just to see if we have correctly parsed the data from Json file
			System.out.println(nextRound.getbBateau().getType());
			System.out.println(nextRound.getbBateau().getLife());
			System.out.println(nextRound.getbBateau().getName());
			// some more tests to do ...

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "";
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
