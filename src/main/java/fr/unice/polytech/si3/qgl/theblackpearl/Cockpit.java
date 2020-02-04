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
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

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

		int vitesse = ;//un angle peut avoir différente configuration donc il nous faut la vitesse nécéssaire
		double[] anglepossible = parsedInitGame.getBateau().anglesPossibles(parsedInitGame.getMarins().size());
		Double angleoptimal = ;// méthode qui nous renvoie l'angle optimal
		ArrayList<Rame> nombreRames = parsedInitGame.getBateau().getListRames();
		int[] nombreMarinAplacer = parsedInitGame.getBateau().nombreMarinsBabordTribord(angleoptimal, parsedInitGame.getMarins().size(),nombreRames, vitesse);
		ArrayList<Entity> listeEntite = parsedInitGame.getBateau().getEntities();
		ArrayList<Action> actionsNextRoundTemporaire = new ArrayList<>();

		do {
			for (Marin m : parsedInitGame.getMarins()) {
				MOVING moving = m.planificationMarinAllerRamer(listeEntite, nombreMarinAplacer[0], nombreMarinAplacer[1], (int) ((Rectangle) parsedInitGame.getBateau().getShape()).getWidth());
				if (moving != null && nombreMarinAplacer[0] != 0 && nombreMarinAplacer[1] != 0) { // on considère que les rames sont au bord du bateau (mais on ne sait jamais) d'ou le else if et pas le else
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
					}
				}
			}
			angleoptimal= ; // méthode qui nous renvoie le deuxième angle le plus optimal
		} while (nombreMarinAplacer[0] != 0 || nombreMarinAplacer[1] != 0);
		actionsNextRound=actionsNextRoundTemporaire;




		//Creation of
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


	@Override
	public List<String> getLogs() {
		return logs;
	}
}
