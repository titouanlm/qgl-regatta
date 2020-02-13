package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CockpitTest {

    private ArrayList<Entity> listeEntite;
    private List<Marin> listeMarins;
    private Cockpit cockpit;

    @BeforeEach
    public void setUp(){
        cockpit=new Cockpit();
        listeEntite=new ArrayList<>();
        listeMarins=new ArrayList<>();
        listeEntite.add(new Rame("oar",0,0));
        listeEntite.add(new Rame("oar",1,0));
        listeEntite.add(new Rame("oar",2,0));
        listeEntite.add(new Rame("oar",7,8));
        listeEntite.add(new Rame("oar",1,8));
        listeEntite.add(new Rame("oar",2,8));
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
        assertEquals((cockpit.getCaptain().supprimerEntite(listeEntite,True,True2,marin,moving,cockpit.getParsedInitGame()).size()),5);
        assertEquals((cockpit.getCaptain().supprimerEntite(listeEntite,True,True2,marin,moving,cockpit.getParsedInitGame()).size()),5);
        assertEquals((cockpit.getCaptain().supprimerEntite(listeEntite,True3,True2,marin,moving,cockpit.getParsedInitGame()).size()),5);
        ArrayList<Entity> listeNulle = new ArrayList<>();
        assertEquals((cockpit.getCaptain().supprimerEntite(listeNulle,True3,True2,marin,moving,cockpit.getParsedInitGame())).size(),0);
    }


}