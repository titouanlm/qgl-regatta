package fr.unice.polytech.si3.qgl.theblackpearl;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MarinTest {

    private Marin marin1 = mock(Marin.class);
    private Marin marin2 = new Marin(2,0, 0, "Chef");
    private Marin marin3 = new Marin(3,0, 0, "General");
    private Marin marin4 = new Marin(4,0, 0, "Commandant");
    private Marin marin5 = new Marin(5,0, 0, "Capitaine");
    private ArrayList<Entity> Entities = new ArrayList<>();
    private Entity e1 = new Rame("oar",1,0);
    private Entity e2 = new Rame("oar",0,1);
    private Entity e3 = new Rame("oar",1, 1);
    private Entity e4 = new Rame("oar",0, 0);

    @BeforeEach
    void setUp(){
        when(marin1.isLibre()).thenReturn(false);
        Entities.add(e1);
        Entities.add(e2);
        Entities.add(e3);
        Entities.add(e4);
    }

    @Test
    public void planificationAllerRamerTest(){
        assertFalse(marin1.isLibre());
        assertTrue(marin2.isLibre());
        assertTrue(marin3.isLibre());
        assertTrue(marin4.isLibre());
        assertNull(marin1.planificationMarinAllerRamer(Entities, 2,2,2));
        assertNull(marin2.planificationMarinAllerRamer(Entities, 0,0,2));
        assertNotNull(marin2.planificationMarinAllerRamer(Entities, 1,0,2));
        assertNull(marin2.planificationMarinAllerRamer(Entities, 0,0,2));
        assertNotNull(marin3.planificationMarinAllerRamer(Entities, 0,1,2));
        marin2.setLibre(true);
        ArrayList<Entity> aucuneEntite = new ArrayList<>();
        assertNull(marin2.planificationMarinAllerRamer(aucuneEntite, 1,0,2));
        ArrayList<Entity> entitesPlaceLoin = new ArrayList<>();
        entitesPlaceLoin.add(new Rame("oar",6,0));
        entitesPlaceLoin.add(new Rame("oar",5, 1));
        assertNull(marin2.planificationMarinAllerRamer(entitesPlaceLoin, 1,1,2));
        ArrayList<Entity> entitesDistancesDifferentes = new ArrayList<>();
        entitesDistancesDifferentes.add(new Rame("oar",3,1));
        entitesDistancesDifferentes.add(new Rame("oar",3, 0));
        assertEquals((marin2.planificationMarinAllerRamer(entitesDistancesDifferentes,1,1,2)).getYdistance(),0);
        marin2.setLibre(true);
        entitesDistancesDifferentes.add(new Rame("oar",1,1));
        assertEquals((marin2.planificationMarinAllerRamer(entitesDistancesDifferentes,1,1,2)).getYdistance(),1);
    }
}
