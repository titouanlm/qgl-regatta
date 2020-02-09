package fr.unice.polytech.si3.qgl.theblackpearl;

import java.lang.module.Configuration;
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
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

import javax.swing.text.StyledEditorKit;

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

		try {
			NextRound nextRound = objectMapper.readValue(round, NextRound.class);
			parsedInitGame.setBateau(nextRound.getBateau());
			logs.add(parsedInitGame.getBateau().getPosition().toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		for (Marin marin : parsedInitGame.getMarins()) {
			marin.setLibre(true);
		}

		double meilleurAngleRealisable[] = meilleurAngleRealisable();
		List<Action> actionsNextRound ;
		actionsNextRound=configurationBateau(meilleurAngleRealisable);
		for(Marin m : parsedInitGame.getMarins()){
			if (!m.isLibre()) {
				actionsNextRound.add(new OAR(m.getId()));
			}
		}

		StringBuilder roundJSON=creationJson(actionsNextRound);
		return roundJSON.toString();
	}

	public ArrayList<Entity> supprimerEntite(ArrayList<Entity> listeEntite, boolean True, boolean True2, Marin m, MOVING moving){
		if (listeEntite!=null) {
			for (int b = 0; b < listeEntite.size(); b++) { // supprimer la rame utilisée pour cette configuration
				if (listeEntite.get(b) instanceof Rame && (True | True2)) {
					if ((listeEntite.get(b).getY() - m.getY()) == moving.getYdistance() && (listeEntite.get(b).getX() - m.getX()) == moving.getXdistance()) {
						listeEntite.remove(b);
						break;
					}
				}
			}
			return listeEntite;
		}
		else return null;
	}

	public List<Action> configurationBateau(double meilleurAngleRealisable[]){
		ArrayList<Rame> nombreRames = parsedInitGame.getBateau().getListRames();
		int[] nombreMarinAplacer;
		ArrayList<Entity> listeEntite = parsedInitGame.getBateau().getEntities();
		ArrayList<Action> actionsNextRoundTemporaire = new ArrayList<>();
		boolean True=false;
		boolean True2=false;

		//do {
		nombreMarinAplacer = parsedInitGame.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable[0], parsedInitGame.getMarins().size(),nombreRames);
		for (Marin m : parsedInitGame.getMarins()){
			if (m.isLibre()) {
				MOVING moving = m.planificationMarinAllerRamer(listeEntite, nombreMarinAplacer[0], nombreMarinAplacer[1], (int) ((Rectangle) parsedInitGame.getBateau().getShape()).getWidth());
				if (moving != null && (nombreMarinAplacer[0] != 0 | nombreMarinAplacer[1] != 0)) {
					if (moving.getYdistance() + m.getY() == 0 && nombreMarinAplacer[0] > 0) { //Babord
						nombreMarinAplacer[0] -= 1;
						actionsNextRoundTemporaire.add(moving);
						True = true;
					} else if (moving.getYdistance() + m.getY() != 0 && nombreMarinAplacer[1] > 0) { //Tribord
						nombreMarinAplacer[1] -= 1;
						actionsNextRoundTemporaire.add(moving);
						True2 = true;
					} else {
						m.setLibre(true);
					}
					listeEntite=supprimerEntite(listeEntite, True, True2, m, moving);
					m.setX(moving.getXdistance() + m.getX());
					m.setY(moving.getYdistance() + m.getY());
					True=false;
					True2=false;
				}
			}
		}
		//} while (nombreMarinAplacer[0] != 0 && nombreMarinAplacer[1] != 0);

		return actionsNextRoundTemporaire;
	}

	public double[] meilleurAngleRealisable(){
		double meilleurAngleRealisable[] = new double[parsedInitGame.getBateau().getListRames().size()+1];

		//1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
		if(parsedInitGame.getGoal() instanceof RegattaGoal){
			RegattaGoal regatta = (RegattaGoal) parsedInitGame.getGoal();
			if(regatta.shipIsInsideCheckpoint(parsedInitGame.getBateau())){
				regatta.removeCheckpoint();
			}
			//2. Calculer l'orientation du bateau pour qu'il soit dans l'axe du prochain checkpoint
			Checkpoint nextCheckpoint = regatta.getCheckpoints().get(0);
			if(nextCheckpoint!=null){
				Position pCheck = nextCheckpoint.getPosition();
				double angleIdeal = Math.atan2(pCheck.getY()-parsedInitGame.getBateau().getPosition().getY(),pCheck.getX()-parsedInitGame.getBateau().getPosition().getX()) - parsedInitGame.getBateau().getPosition().getOrientation();
				//System.out.println(angleIdeal);
				//System.out.println(parsedInitGame.getBateau().nbMarinRameBabord(parsedInitGame.getMarins()));
				//System.out.println(parsedInitGame.getBateau().nbMarinRameTribord(parsedInitGame.getMarins()));

				//System.out.println(parsedInitGame.getBateau().meilleurAngleRealisable(angleIdeal));
				//3. Calculer la solution la plus optimale pour orienter correctement le bateau (tout en avancant si possible) avec les éléments à notre disposition
				int compteur=0;
				for(double angleOptimal : parsedInitGame.getBateau().meilleurAngleRealisable(angleIdeal)){
					meilleurAngleRealisable[compteur] = angleOptimal;
					compteur++;
				}
			}
		}
		return meilleurAngleRealisable;
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

	@Override
	public List<String> getLogs() {
		return logs;
	}
}
