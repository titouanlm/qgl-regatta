package fr.unice.polytech.si3.qgl.theblackpearl;

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


}