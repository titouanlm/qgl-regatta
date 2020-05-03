package fr.unice.polytech.si3.qgl.theblackpearl.ship;

import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Position;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Rectangle;
import fr.unice.polytech.si3.qgl.theblackpearl.shape.Shape;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Gouvernail;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Voile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BateauTest {
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
        listeMarins.add(new Marin(1,0,2,"Edward Pouce"));
        listeMarins.add(new Marin(2,0,2,"Tom Pouce"));
        listeMarins.add(new Marin(3,0,0,"Jack Teach"));
        listeMarins.add(new Marin(4,9,0,"Marschall D Teach"));
        listeMarins.add(new Marin(5,9,0,"Edward New Gate"));
        entities.add(new Rame(0,0));
        entities.add(new Rame(1,0));
        entities.add(new Rame(0,1));
        entities.add(new Rame(1,1));
        entities.add(new Rame(2,0));
        entities.add(new Rame(2,1));
        Position pos = new Position(0, 0, 0);
        Deck deck = new Deck(2, 3);
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
    void getNbVoile() {
        assertEquals(0 , bateau.nbVoile());
    }

    @Test
    void getNbVoileOuverte() {
        assertEquals(0 , bateau.nbVoile());
        Voile v = new Voile(0,0, false);
        entities.add(v);
        assertEquals(1, bateau.nbVoile());
        assertEquals(0, bateau.nbVoileOuverte());
        v.setOpenned(true);
        assertEquals(1, bateau.nbVoileOuverte());
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


    @Test
    void getGouvernail(){
         assertNull(bateau.getGouvernail());
         entities.add(new Gouvernail(0,2));
         assertNotNull(bateau.getGouvernail());
    }

    @Test
    void isOnRudderNotUsed(){
        entities.add(new Gouvernail(0,2));
        assertFalse(bateau.isOnRudderNotUsed(listeMarins.get(0)));
        assertTrue(bateau.isOnRudderNotUsed(listeMarins.get(1)));
        assertFalse(bateau.isOnRudderNotUsed(listeMarins.get(2)));
    }


    @Test
    void isOnOarNotUsedTest(){
        assertFalse(bateau.isOnOarNotUsed(listeMarins.get(5)));
        assertTrue(bateau.isOnOarNotUsed(listeMarins.get(0)));
        assertFalse(bateau.isOnOarNotUsed(listeMarins.get(2)));
    }

    @Test
    void isOnSailNotUsedNotOppenedTest(){
        assertFalse(bateau.isOnSailNotUsedNotOppened(listeMarins.get(0)));
        Voile v = new Voile(9,0, true);
        entities.add(v);
        assertFalse(bateau.isOnSailNotUsedNotOppened(listeMarins.get(5)));
        v.setOpenned(false);
        assertTrue(bateau.isOnSailNotUsedNotOppened(listeMarins.get(5)));
        assertFalse(bateau.isOnSailNotUsedNotOppened(listeMarins.get(4)));
    }

    @Test
    void isOnSailNotUsedOppenedTest(){
        assertFalse(bateau.isOnSailNotUsedOppened(listeMarins.get(0)));
        Voile v = new Voile(9,0, false);
        entities.add(v);
        assertFalse(bateau.isOnSailNotUsedOppened(listeMarins.get(5)));
        v.setOpenned(true);
        assertTrue(bateau.isOnSailNotUsedOppened(listeMarins.get(5)));
        assertFalse(bateau.isOnSailNotUsedOppened(listeMarins.get(4)));
    }


    @Test
    void nbMarinRameTribord(){
        assertEquals(0, bateau.nbMarinRameTribord());
        entities.add(new Gouvernail(8,8));
        for(Entity e : entities){
            e.setLibre(false); // Marin dessus
        }
        assertEquals(3, bateau.nbMarinRameTribord());
    }

    @Test
    void nbMarinRameBabord(){
        assertEquals(0, bateau.nbMarinRameBabord());
        entities.add(new Gouvernail(8,8));
        for(Entity e : entities){
            e.setLibre(false); // Marin dessus
        }
        assertEquals(3, bateau.nbMarinRameBabord());
    }

    @Test
    void getterSetter(){
        assertEquals(shape, bateau.getShape());
        assertEquals("ship", bateau.getType());
        bateau.setType("");
        assertEquals("", bateau.getType());
        bateau.setPosition(null);
        assertNull(bateau.getPosition());
        entities=null;
        assertNotNull(bateau.toString());
    }


}