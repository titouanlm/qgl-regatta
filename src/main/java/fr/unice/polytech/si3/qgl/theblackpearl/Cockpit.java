package fr.unice.polytech.si3.qgl.theblackpearl;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.OAR;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.NextRound;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;

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

		ArrayList<Action> actionsNextRound = new ArrayList<>();


		// Retourne liste constitué d'ensemble de rames à activer
		// Test toute les configuration possibles, si c'est impossible on passe à la configuration suivante
		// Ne retiens que la meilleure configuration

		for (Marin marin : parsedInitGame.getMarins()){// chaque next round les marins redeviennent dispo
			marin.setLibre(true);
		}

		for (int i=0;i<parsedInitGame.getBateau().anglesPossibles(parsedInitGame.getMarins().size()).length;i++) { // boucle pour tous les angles possibles
			int distanceCheckpoint = 99999999;
			ArrayList<Checkpoint> listCheckpoints = new ArrayList<>(parsedInitGame.getGoal().getListCheckpoints());
			ArrayList<Action> actionsNextRoundTemporaire = new ArrayList<>();
			if (parsedInitGame.getBateau().nombreMarinsBabordTribord(listCheckpoints, parsedInitGame.getMarins().size()) != null ) {
				int[] nombreMarinAplacer = parsedInitGame.getBateau().nombreMarinsBabordTribord(listCheckpoints, parsedInitGame.getMarins().size()); // c'est ici qu'est donné le nombre de marin babord/tribord
				ArrayList<Entity> listeEntite = parsedInitGame.getBateau().getEntities();
				InitGame parsedInitGameTemporaire = parsedInitGame;
				for (Marin m : parsedInitGameTemporaire.getMarins()) {
					MOVING moving = m.planificationMarinAllerRamer(listeEntite, nombreMarinAplacer[0], nombreMarinAplacer[1], (int) ((Rectangle) parsedInitGame.getBateau().getShape()).getWidth());
					if (moving != null) { // on considère que les rames sont au bord du bateau (mais on ne sait jamais) d'ou le else if et pas le else
						if (moving.getYdistance() == 0 && nombreMarinAplacer[0] > 0) { //Babord
							nombreMarinAplacer[0] -= 1;
							actionsNextRoundTemporaire.add(moving);
						} else if (moving.getYdistance() != 0 && nombreMarinAplacer[1] > 0) { //Tribord
							nombreMarinAplacer[1] -= 1;
							actionsNextRoundTemporaire.add(moving);
						}
						for (int b = 0; b < listeEntite.size(); b++) { // supprimer la rame utilisé pour cette configuration
							if (listeEntite.get(b).getY() == moving.getYdistance() && listeEntite.get(b).getX() == moving.getXdistance()) {
								listeEntite.remove(b);
								break;
							}
							// à faire
						}// calculer l'avancement hypothétique du bateau pour choisir la meilleure configuraiton
						// on se base sur la distance qu'il lui reste pour arriver jusqu'au checkpoint


					}
				}
				if ( <distanceCheckpoint){
					actionsNextRound = actionsNextRoundTemporaire;
				}
			}
		}



		//Creation of Actions
		for(Marin m : parsedInitGame.getMarins()){
			if (!m.isLibre()) {
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


	public int DistanceCheckpoint(ArrayList<Action> listAction){
		int distanceCheckpoints=999999999;
		double positionCheckpointX=parsedInitGame.getGoal().getListCheckpoints().get(0).getPosition().getX();
		double positionCheckpointY=parsedInitGame.getGoal().getListCheckpoints().get(0).getPosition().getY();
		for (int i=0;i<50;i++){

		}
		return distanceCheckpoints;
	}


	@Override
	public List<String> getLogs() {
		return logs;
	}
}
