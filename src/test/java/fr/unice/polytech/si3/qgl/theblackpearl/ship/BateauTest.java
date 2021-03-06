package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import fr.unice.polytech.si3.qgl.theblackpearl.Marin;
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
    private ArrayList<Entity> entities;
    private Shape shape;
    private Bateau bateau;
    private List<Marin> listeMarins;

    BateauTest() {
    }

    @BeforeEach
    public void init() {
        entities = new ArrayList<>();
        listeMarins = new ArrayList<>();
        listeMarins.add(new Marin(0,0,0,"Edward Teach"));
        listeMarins.add(new Marin(1,0,0,"Edward Pouce"));
        listeMarins.add(new Marin(2,0,0,"Tom Pouce"));
        listeMarins.add(new Marin(3,0,0,"Jack Teach"));
        listeMarins.add(new Marin(4,0,0,"Marschall D Teach"));
        listeMarins.add(new Marin(5,0,0,"Edward New Gate"));
        entities.add(new Rame(0,0));
        entities.add(new Rame(1,0));
        entities.add(new Rame(0,1));
        entities.add(new Rame(1,1));
        entities.add(new Rame(2,0));
        entities.add(new Rame(2,1));
        pos = new Position(0,0,0);
        deck = new Deck(2, 3);
        shape = new Rectangle( 2, 3, 0 );
        bateau = new Bateau("ship", 100, pos, "Les copaings d'abord!", deck, entities, shape);
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
        assertThat(bateau.anglesPossiblesAvecRames(), is(anglesPossibles));
    }

    @Test
    void getNbRame() {
        assertEquals(6 , bateau.getNbRame());
    }

    @Test
    void nombreMarinsBabordTribord(){
        int nombreMarinAplacer[];
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames(-Math.PI/2, bateau.getListRames());
        assertEquals(3,nombreMarinAplacer[0]);
        assertEquals(0,nombreMarinAplacer[1]);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + Math.PI/6), bateau.getListRames());
        assertEquals(3,nombreMarinAplacer[0]);
        assertEquals(1,nombreMarinAplacer[1]);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 2*Math.PI/6), bateau.getListRames());
        assertEquals(3,nombreMarinAplacer[0]);
        assertEquals(2,nombreMarinAplacer[1]);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 3*Math.PI/6), bateau.getListRames());
        assertEquals(3,nombreMarinAplacer[0]);
        assertEquals(3,nombreMarinAplacer[1]);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 4*Math.PI/6), bateau.getListRames());
        assertEquals(2,nombreMarinAplacer[0]);
        assertEquals(3,nombreMarinAplacer[1]);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 5*Math.PI/6), bateau.getListRames());
        assertEquals(1,nombreMarinAplacer[0]);
        assertEquals(3,nombreMarinAplacer[1]);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 6*Math.PI/6), bateau.getListRames());
        assertEquals(0,nombreMarinAplacer[0]);
        assertEquals(3,nombreMarinAplacer[1]);
        ArrayList<Entity> listeRameEnlever = new ArrayList<>();
        listeRameEnlever.add(entities.get(4));
        listeRameEnlever.add(entities.get(5));
        entities.removeAll(listeRameEnlever);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 1*Math.PI/6), bateau.getListRames());
        assertNull(nombreMarinAplacer);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 2*Math.PI/6), bateau.getListRames());
        assertNull(nombreMarinAplacer);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 4*Math.PI/6), bateau.getListRames());
        assertNull(nombreMarinAplacer);
        nombreMarinAplacer = this.bateau.nombreMarinsRamesBabordTribordRames((-Math.PI/2 + 5*Math.PI/6), bateau.getListRames());
        assertNull(nombreMarinAplacer);
    }

    /*@Test
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
    }*/
}