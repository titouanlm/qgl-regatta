package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity.supprimerEntite;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CockpitTest {

    private ArrayList<Entity> listeEntite;
    private List<Marin> listeMarins;

    @BeforeEach
    public void setUp(){
        listeEntite=new ArrayList<>();
        listeMarins=new ArrayList<>();
        listeEntite.add(new Rame(0,0));
        listeEntite.add(new Rame(1,0));
        listeEntite.add(new Rame(2,0));
        listeEntite.add(new Rame(7,8));
        listeEntite.add(new Rame(1,8));
        listeEntite.add(new Rame(2,8));
        listeMarins.add(new Marin(0,0,0,"Edward Teach"));
        listeMarins.add(new Marin(1,0,0,"Edward Pouce"));
        listeMarins.add(new Marin(2,0,0,"Tom Pouce"));
        listeMarins.add(new Marin(3,0,0,"Jack Teach"));
        listeMarins.add(new Marin(4,0,0,"Marschall D Teach"));
        listeMarins.add(new Marin(5,0,0,"Edward New Gate"));
    }

    @Test
    public void supprimerEntiteTest(){
        boolean True=false;
        boolean True2=true;
        boolean True3=false;
        Marin marin = new Marin(1, 5 ,6,"Edward Teach");
        MOVING moving = new MOVING(1,"MOVING",2,2);
        assertEquals(5,supprimerEntite(listeEntite,True,True2,marin,moving).size());
        assertEquals(5, supprimerEntite(listeEntite,True,True2,marin,moving).size());
        assertEquals(5, supprimerEntite(listeEntite,True3,True2,marin,moving).size());
        ArrayList<Entity> listeNulle = new ArrayList<>();
        assertEquals(0, supprimerEntite(listeNulle,True3,True2,marin,moving).size());
    }

    @Test
    void initGame() {

    }

    @Test
    void nextRound() {
    }

    @Test
    void modificationJsonObstacles() {
    }

    @Test
    void resetMarinNouveauTour() {
    }

    @Test
    void creerLogNouveautour() {
    }

    @Test
    void tacheMarins() {
        Cockpit c = new Cockpit();
        c.initGame("{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Tom Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Pouce\"}],\"shipCount\":1}");
        c.getParsedInitGame().getMarins().get(0).setActionAFaire("Ramer");
        c.getParsedInitGame().getMarins().get(1).setActionAFaire("tournerGouvernail");
        c.getParsedInitGame().getMarins().get(2).setActionAFaire("HisserVoile");
        c.getParsedInitGame().getMarins().get(3).setActionAFaire("BaisserLaVoile");
        c.getParsedInitGame().getMarins().get(0).setLibre(false);
        c.getParsedInitGame().getMarins().get(1).setLibre(false);
        c.getParsedInitGame().getMarins().get(2).setLibre(false);
        c.getParsedInitGame().getMarins().get(3).setLibre(false);
        List<Action> actionsNextRound = new ArrayList<>();
        c.tacheMarins(actionsNextRound);
        assertEquals(4, actionsNextRound.size());
    }

    @Test
    void creationJson() {
    }

    @Test
    void getLogs() {
    }
}