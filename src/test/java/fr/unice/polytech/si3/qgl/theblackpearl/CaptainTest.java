package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity.supprimerEntite;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaptainTest {

    private ArrayList<Entity> listeEntite;
    private List<Marin> listeMarins;
    private Captain captain;

    @BeforeEach
    public void setUp(){
        captain = new Captain(null);
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
        Marin marin = new Marin(1, 5 ,6,"Edward Teach");
        MOVING moving = new MOVING(1,"MOVING",2,2);
        assertEquals((supprimerEntite(listeEntite,false,true,marin,moving).size()),5);
        assertEquals((supprimerEntite(listeEntite,false,true,marin,moving).size()),5);
        assertEquals((supprimerEntite(listeEntite,false,true,marin,moving).size()),5);
        ArrayList<Entity> listeNulle = new ArrayList<>();
        assertEquals((supprimerEntite(listeNulle,false,true,marin,moving)).size(),0);
    }

    @Test
    public void configurationBateauTest(){

    }

    @Test
    public void priseEnComptePositionMarinsTest(){

    }

    @Test
    public void restaurationPositionMarinsTest(){

    }

    @Test
    public void meilleurAngleRealisableTest(){

    }

    @Test
    public void resetCapitainTest(){

    }

    @Test
    public void preConfigurationBateauTest(){

    }

    @Test
    public void angleGouvernailTest(){

    }

    @Test
    public void configurationGouvernailTest(){

    }

    @Test
    public void captainFaitLeJobTest(){

    }
}