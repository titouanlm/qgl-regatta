package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import fr.unice.polytech.si3.qgl.theblackpearl.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BateauTest {
    private Position pos;
    private Deck deck ;
    private Entity[] entities;
    private Shape shape;
    private Bateau bateau;

    @BeforeEach
    public void init() {
        pos = new Position(0,0,0);
        deck = new Deck(2, 3);
        entities = new Entity[6];
        entities[0] = new Rame("oar",0,0);
        entities[1] = new Rame("oar",1,0);
        entities[2] = new Rame("oar",0,1);
        entities[3] = new Rame("oar",1,1);
        entities[4] = new Rame("oar",2,0);
        entities[5] = new Rame("oar",2,1);
        shape = new Rectangle("rectangle", 2, 3, 0 );
        bateau = new Bateau("ship", 100, pos, "Les copaings d'abord!", deck, entities, shape );
    }

    @Test
    void anglesPossibles() {
       System.out.println(bateau.anglesPossibles());
    }

    @Test
    void getNbRame() {
        assertEquals(6 , bateau.getNbRame());
    }

    @Test
    void meilleurAngleRealisable() {
        System.out.println(bateau.meilleurAngleRealisable(0.223981633974483));
       // assertEquals( ,);
    }
}