package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.engine.Constants;

import java.util.ArrayList;

/*
Classe représentant l'arbitre d'une partie de régate.

Responsablilités non exhaustives :
1) Récupérer les actions envoyées par la méthode nextRound de la classe Cockpit et effectuer les taches suivantes :
    a) Vérifier que les actions sont possibles, si non les ignorer. (méthode verifyActions)
    b) Si les actions sont faisables, mettre à jour les informations du point 2). (méthode sendNextRound)

2) Envoyer à chaque tour les informations mises à jour (sous forme d'une String au format Json) à la méthode nextRound de la classe Cockpit :
    a) Infos liées au bateau : seules la position et l'orientation sont à mettre à jour (plus tard la vie du bateau également)
    b) Info sur le vent
    c) Plus tard, infos des entités visibles (Courant, AutreBateau et Recif)

 */

public class Referee {

    private String initGame;
    private String nextRound;
    private Cockpit cockpit;
    private String actions;

    Bateau bateau ;
    ArrayList<Rame> rames ;
    private final int SPEED_CONSTANT = 165;
    private final double MAX_RADIAL_SPEED = Math.PI/2 ;
    double radialSpeed = 0;
    double rotationSpeedPerOar ;


    public Referee(String gameInfo, String firstRound,Cockpit aCockpit){
        this.initGame = gameInfo;
        this.nextRound = firstRound;
        this.cockpit = aCockpit;
    }

    /*
    getteurs
     */
    public String getNextRound() {
        return this.nextRound;
    }

    public String getInitGame() {
        return this.initGame;
    }

    public String getActions() {
        return this.actions;
    }

    public Cockpit getCockpit() {
        return this.cockpit;
    }

    /*
    TODO
     1) Mettre à jour l'objet parsedNextRound
     2) Mettre à jour les informations correspondantes dans le Json :
            -   position du bateau
            -   orientation du bateau
            -   plus tard, la vie du bateau
            -   Vent
            -   plus tard, entités visibles (Courant, AutreBateau et Recif)
     */
    public void updateInfosForNextRound(String actions){
        // TODO verifyActions(actions);

        // update infos of the parsedNextRound object
        // Calculate the orientation that the ship takes with the given actions
        bateau = cockpit.getParsedNextRound().getBateau();
        rames = cockpit.getParsedNextRound().getBateau().getListRames();
        radialSpeed = 0;
        rotationSpeedPerOar = MAX_RADIAL_SPEED / rames.size();


        for (Rame rame : rames) {
            if (rame.isUsed()) {
                if (bateau.getDeck().isStarboard(rame)) { // Starboard = tribord = droite
                    radialSpeed += rotationSpeedPerOar;
                } else if (bateau.getDeck().isPort(rame)) { // Port = bâbord = gauche
                    radialSpeed -= rotationSpeedPerOar;
                }
            }
        }

        // TO FINISH

        // update infos of the string NextRound
        // Creation/Update of nextRound JSON file
        // TODO


    }
    /*
    TODO Cette méthode détermine si les actions sont possibles et supprime celles qui ne le sont pas (on ne fait donc que les ignorer)
    Pour l'instant, on vérifie juste qu'un marin peut en effet ramer
    */
    public void verifyActions(String actions){

    }
    


}
