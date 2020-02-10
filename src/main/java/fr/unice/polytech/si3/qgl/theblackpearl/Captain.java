package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.Checkpoint;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;

import java.util.ArrayList;
import java.util.List;

public class Captain {


    public Captain() {
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

    public List<Action> configurationBateau(double meilleurAngleRealisable[], InitGame game){
        ArrayList<Rame> nombreRames = game.getBateau().getListRames();
        int[] nombreMarinAplacer;
        ArrayList<Entity> listeEntite = game.getBateau().getEntities();
        ArrayList<Action> actionsNextRoundTemporaire = new ArrayList<>();
        boolean marinPlaceGauche=false;
        boolean marinPlaceDroite=false;

        //do {
        nombreMarinAplacer = game.getBateau().nombreMarinsBabordTribord(meilleurAngleRealisable[0], game.getMarins().size(),nombreRames);
        for (Marin m : game.getMarins()){
            if (m.isLibre()) {
                MOVING moving = m.planificationMarinAllerRamer(listeEntite, nombreMarinAplacer[0], nombreMarinAplacer[1], (int) ((Rectangle) game.getBateau().getShape()).getWidth());
                if (moving != null && (nombreMarinAplacer[0] != 0 | nombreMarinAplacer[1] != 0)) {
                    if (moving.getYdistance() + m.getY() == 0 && nombreMarinAplacer[0] > 0) { //Babord
                        nombreMarinAplacer[0] -= 1;
                        actionsNextRoundTemporaire.add(moving);
                        marinPlaceGauche = true;
                    } else if (moving.getYdistance() + m.getY() != 0 && nombreMarinAplacer[1] > 0) { //Tribord
                        nombreMarinAplacer[1] -= 1;
                        actionsNextRoundTemporaire.add(moving);
                        marinPlaceDroite = true;
                    } else {
                        m.setLibre(true);
                    }
                    listeEntite=supprimerEntite(listeEntite, marinPlaceGauche, marinPlaceDroite, m, moving);
                    m.setX(moving.getXdistance() + m.getX());
                    m.setY(moving.getYdistance() + m.getY());
                    marinPlaceGauche=false;
                    marinPlaceDroite=false;
                }
            }
        }
        //} while (nombreMarinAplacer[0] != 0 && nombreMarinAplacer[1] != 0);

        return actionsNextRoundTemporaire;
    }

    public double[] meilleurAngleRealisable(InitGame game){
        double meilleurAngleRealisable[] = new double[game.getBateau().getListRames().size()+1];

        //1. Tester si on a atteint le check point (et si on a finit la course) ==> supprime le checkpoint
        if(game.getGoal() instanceof RegattaGoal){
            RegattaGoal regatta = (RegattaGoal) game.getGoal();
            if(regatta.shipIsInsideCheckpoint(game.getBateau())){
                regatta.removeCheckpoint();
            }
            //2. Calculer l'orientation du bateau pour qu'il soit dans l'axe du prochain checkpoint
            Checkpoint nextCheckpoint = regatta.getCheckpoints().get(0);
            if(nextCheckpoint!=null){
                Position pCheck = nextCheckpoint.getPosition();
                double angleIdeal = Math.atan2(pCheck.getY()-game.getBateau().getPosition().getY(),pCheck.getX()-game.getBateau().getPosition().getX()) - game.getBateau().getPosition().getOrientation();
                //System.out.println(angleIdeal);
                //System.out.println(parsedInitGame.getBateau().nbMarinRameBabord(parsedInitGame.getMarins()));
                //System.out.println(parsedInitGame.getBateau().nbMarinRameTribord(parsedInitGame.getMarins()));

                //System.out.println(parsedInitGame.getBateau().meilleurAngleRealisable(angleIdeal));
                //3. Calculer la solution la plus optimale pour orienter correctement le bateau (tout en avancant si possible) avec les éléments à notre disposition
                int compteur=0;
                for(double angleOptimal : game.getBateau().meilleurAngleRealisable(angleIdeal)){
                    meilleurAngleRealisable[compteur] = angleOptimal;
                    compteur++;
                }
            }
        }
        return meilleurAngleRealisable;
    }

}
