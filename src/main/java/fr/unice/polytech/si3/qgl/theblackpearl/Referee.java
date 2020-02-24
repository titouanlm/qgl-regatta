package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.TURN;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.Bateau;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
Classe représentant l'arbitre d'une partie de régate.

Responsablilités non exhaustives :
1) Récupérer les actions envoyées par la méthode nextRound de la classe Cockpit et effectuer les taches suivantes :
    a) Vérifier que les actions sont possibles, si non les ignorer.
    b) Si les actions sont faisables, mettre à jour les informations du point 2).

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
    private final int CONSTANTE_DE_VITESSE = 165 ;
    double rotationDesRames = 0 ; // C'est l'orientation induite par les rames actives (c'est un angle en radian)
    double rotationParRame ; // C'est l'orientation induite par UNE rame (c'est un angle en radian)
    int nombreRamesActives ;
    double vitesseDesRames ; // C'est la norme de la vitesse induite par les rames actives
    double rotationActuelleDuBateau ;
    double nouvelleRotationDuBateau ;
    int signeX;
    int signeY;
    double alpha; // angle utilisé pour les calculs
    double x;
    double y;


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

    public void setActions(String actions) {
        this.actions = actions;
    }

    public void setNextRound(String nextRound) {
        this.nextRound = nextRound;
    }

    public Cockpit getCockpit() {
        return this.cockpit;
    }

    /*
    TODO Cette méthode détermine si les actions sont possibles et supprime celles qui ne le sont pas (on ne fait donc que les ignorer)
    Pour l'instant, on vérifie juste qu'un marin peut en effet ramer
    */
    public void verifierActions(String actions){
        // TODO
    }

    //____________________________________________________________________________________________________________________________________________________________________________________________________________________

    public void mettreAJourBateau() {
        // Mise à jour des infos du bateau
        bateau = cockpit.getParsedNextRound().getBateau();
        rames = cockpit.getParsedNextRound().getBateau().getListRames();
        rotationActuelleDuBateau = bateau.getPosition().getOrientation();
        x = bateau.getPosition().getX();
        y = bateau.getPosition().getY();
        // Mise à zéro des valeurs
        nouvelleRotationDuBateau = 0;
        rotationDesRames = 0;
        nombreRamesActives = 0;
        vitesseDesRames = 0;
        rotationParRame = Math.PI / rames.size();

        // 1) Calcul de l'orientation induite par les rames
        for (Rame rame : rames) {
            if (rame.isUsed()) {
                nombreRamesActives += 1;
                if (bateau.getDeck().isStarboard(rame)) { // Starboard = tribord = droite
                    rotationDesRames += rotationParRame;
                } else if (bateau.getDeck().isPort(rame)) { // Port = bâbord = gauche
                    rotationDesRames -= rotationParRame;
                }
            }
        }

        // 2)a) Calcul de la nouvelle rotation du bateau
        nouvelleRotationDuBateau = rotationActuelleDuBateau + rotationDesRames;
        // 2)b) On travaille avec des rotations comprises entre -2PI et 2PI
        nouvelleRotationDuBateau = nouvelleRotationDuBateau % (2 * Math.PI);

        // 3) Calcul de la vitesse induite par les rames (actives)
        vitesseDesRames = (nombreRamesActives * CONSTANTE_DE_VITESSE) / rames.size();

        // 4) Mise à jour de l'orientation du bateau pour le prochain tour
        bateau.getPosition().setOrientation(nouvelleRotationDuBateau);

        // 5) Mise à jour des coordonnées du bateau pour le prochain tour
        // 4 cas à distinguer
        if ((x >= 0 && y >= 0) && ((nouvelleRotationDuBateau <= Math.PI / 2 && nouvelleRotationDuBateau >= 0) ||
                (nouvelleRotationDuBateau <= -3 * Math.PI / 2 && nouvelleRotationDuBateau >= -2 * Math.PI))) { // Si on est dans la partie supérieure droite de la map
            signeX = 1;
            signeY = 1;
            alpha = nouvelleRotationDuBateau;
        } else if ((x<=0 && y>=0) && ((nouvelleRotationDuBateau <= Math.PI && nouvelleRotationDuBateau >= Math.PI / 2) ||
                (nouvelleRotationDuBateau <= -1 * Math.PI && nouvelleRotationDuBateau >= -3 * Math.PI / 2))) { // Si on est dans la partie supérieure gauche de la map
            signeX = -1; // Les abscisses sont négatives dans cette partie de la map
            signeY = 1;
            alpha = Math.abs((nouvelleRotationDuBateau - Math.PI) % (2*Math.PI));
        } else if ((x<=0 && y<=0) && ((nouvelleRotationDuBateau <= 3 * Math.PI / 2 && nouvelleRotationDuBateau >= Math.PI) ||
                (nouvelleRotationDuBateau <= -1 * Math.PI / 2 && nouvelleRotationDuBateau >= -1 * Math.PI))) { // si on est dans la partie inférieure gauche de la map
            signeX = -1; // Les abscisses sont négatives dans cette partie de la map
            signeY = -1; // Les ordonnées sont négatives dans cette partie de la map
            alpha = Math.abs((nouvelleRotationDuBateau + Math.PI  ) % (2*Math.PI));
        } else if ((x>=0 && y<= 0) && ((nouvelleRotationDuBateau <= 2 * Math.PI && nouvelleRotationDuBateau >= 3 * Math.PI / 2) ||
                (nouvelleRotationDuBateau <= -0 && nouvelleRotationDuBateau >= -1 * Math.PI / 2))) { // Si on est dans la partie inférieure droite de la map
            signeX = 1;
            signeY = -1; // Les ordonnées sont négatives dans cette partie de la map
            alpha = Math.abs((nouvelleRotationDuBateau + Math.PI/2  ) % (2*Math.PI));
        }

        bateau.getPosition().setX(x +(signeX * vitesseDesRames * Math.cos(alpha)));
        bateau.getPosition().setY(y + (signeY * vitesseDesRames * Math.sin(alpha)));

        //rotationActuelleDuBateau = nouvelleRotationDuBateau;

    }

    //____________________________________________________________________________________________________________________________________________________________________________________________________________________

    /*
    Mettre à jour les infos de la chaine NextRound
     */
    public void mettreAJourJson() {
        // TODO Soit on arrive à trouver un moyen de juste mettre à jour les fields qui changent (orientation, position ...)
        //   Soit on recrée le Json en entier à chaque tour
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //setInitGame(objectMapper.writeValueAsString(cockpit.getParsedInitGame()));
            // System.out.println(initGame);
            setNextRound(objectMapper.writeValueAsString(cockpit.getParsedNextRound()));
            System.out.println(nextRound);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /*
        StringBuilder roundMisAJour = new StringBuilder("[");
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
        */

    }

    private void setInitGame(String initGame) {
        this.initGame = initGame;
    }

    //____________________________________________________________________________________________________________________________________________________________________________________________________________________

    /*
     1) TODO Vérifier que les actions sont possibles
     2) Mettre à jour l'objet bateau
     3) Mettre à jour les informations correspondantes dans le Json :
            - Bateau
                -   position
                -   orientation
                -   TODO plus tard, la vie
            -   TODO Vent
            -   TODO plus tard, entités visibles (Courant, AutreBateau et Recif)
     */
    public void mettreAJourNextRound()  {

        // TODO verifierActions();

        mettreAJourBateau();

        mettreAJourJson();

    }

}