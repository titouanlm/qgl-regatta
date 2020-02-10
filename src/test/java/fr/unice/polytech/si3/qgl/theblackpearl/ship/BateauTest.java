package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BateauTest {
    private Position pos;
    private Deck deck ;
    private List<Entity> entities;
    private Shape shape;
    private Bateau bateau;

    @BeforeEach
    public void init() {
        pos = new Position(0,0,0);
        deck = new Deck(2, 3);
        entities = new ArrayList<>();
        entities.add(new Rame("oar",0,0));
        entities.add(new Rame("oar",1,0));
        entities.add(new Rame("oar",0,1));
        entities.add(new Rame("oar",1,1));
        entities.add(new Rame("oar",2,0));
        entities.add(new Rame("oar",2,1));
        shape = new Rectangle("rectangle", 2, 3, 0 );
        bateau = new Bateau("ship", 100, pos, "Les copaings d'abord!", deck, entities, shape );
    }

    @Test
    void anglesPossiblesAvec3RamesDeChaqueCote() {
        List<Double> anglesPossibles = new ArrayList<>();
        anglesPossibles.add(Math.PI/6);
        anglesPossibles.add(-Math.PI/6);
        anglesPossibles.add(Math.PI/3);
        anglesPossibles.add(-Math.PI/3);
        anglesPossibles.add(Math.PI/2);
        anglesPossibles.add(-Math.PI/2);
        anglesPossibles.add(0.0);
        assertThat(bateau.anglesPossibles(), is(anglesPossibles));
    }

    @Test
    void getNbRame() {
        assertEquals(6 , bateau.getNbRame());
    }

    @Test
    void meilleurAngleRealisable() {
        List<Double> meilleurAngleRealisableSort= new ArrayList<>();
        meilleurAngleRealisableSort.add(0.0);
        meilleurAngleRealisableSort.add(Math.PI/6);
        meilleurAngleRealisableSort.add(-Math.PI/6);
        meilleurAngleRealisableSort.add(Math.PI/3);
        meilleurAngleRealisableSort.add(-Math.PI/3);
        meilleurAngleRealisableSort.add(Math.PI/2);
        meilleurAngleRealisableSort.add(-Math.PI/2);

        assertThat(bateau.meilleurAngleRealisable(0.223981633974483), is(meilleurAngleRealisableSort));
    }


}