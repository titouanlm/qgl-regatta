package fr.unice.polytech.si3.qgl.theblackpearl;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private Entity e3 = new Rame("oar",0, 2);

    @BeforeEach
    void setUp(){
        when(marin1.getLibre()).thenReturn(false);
        Entities.add(e1);
        Entities.add(e2);
        Entities.add(e3);
    }

    @Test
    public void planificationTest(){
        assertFalse(marin1.getLibre());
        assertTrue(marin2.getLibre());
        assertTrue(marin3.getLibre());
        assertTrue(marin4.getLibre());
        assertNull(marin2.planificationMarinAllerRamer(Entities, 0,0,2));
        assertNotNull(marin2.planificationMarinAllerRamer(Entities, 1,0,2));
        assertNull(marin2.planificationMarinAllerRamer(Entities, 0,0,2));
        assertNotNull(marin3.planificationMarinAllerRamer(Entities, 0,1,2));
    }
}
