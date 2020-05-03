package fr.unice.polytech.si3.qgl.theblackpearl;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.Marin;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Entity;
import fr.unice.polytech.si3.qgl.theblackpearl.ship.entities.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MarinTest {

    private Marin marin1 = mock(Marin.class);
    private Marin marin2 = new Marin(2,0, 0, "Chef");
    private Marin marin3 = new Marin(3,0, 0, "General");
    private Marin marin4 = new Marin(4,0, 0, "Commandant");
    private ArrayList<Entity> Entities = new ArrayList<>();
    private Entity e1 = new Rame(1,0);
    private Entity e2 = new Rame(0,1);
    private Entity e3 = new Rame(1, 1);
    private Entity e4 = new Rame(0, 0);

    @BeforeEach
    void setUp(){
        when(marin1.isLibre()).thenReturn(false);
        Entities.add(e1);
        Entities.add(e2);
        Entities.add(e3);
        Entities.add(e4);
    }

    /*@Test
    public void planificationAllerRamerTest(){
        assertFalse(marin1.isLibre());
        assertTrue(marin2.isLibre());
        assertTrue(marin3.isLibre());
        assertTrue(marin4.isLibre());
        assertNull(marin1.marinADeplacer(Entities, 2,2,2));
        assertNull(marin2.marinADeplacer(Entities, 0,0,2));
        assertNotNull(marin2.marinADeplacer(Entities, 1,0,2));
        assertNull(marin2.marinADeplacer(Entities, 0,0,2));
        assertNotNull(marin3.marinADeplacer(Entities, 0,1,2));
        marin2.setLibre(true);
        ArrayList<Entity> aucuneEntite = new ArrayList<>();
        assertNull(marin2.marinADeplacer(aucuneEntite, 1,0,2));
        ArrayList<Entity> entitesPlaceLoin = new ArrayList<>();
        entitesPlaceLoin.add(new Rame(6,0));
        entitesPlaceLoin.add(new Rame(5, 1));
        assertNull(marin2.marinADeplacer(entitesPlaceLoin, 1,1,2));
        ArrayList<Entity> entitesDistancesDifferentes = new ArrayList<>();
        entitesDistancesDifferentes.add(new Rame(3,1));
        entitesDistancesDifferentes.add(new Rame(3, 0));
        assertEquals((marin2.marinADeplacer(entitesDistancesDifferentes,1,1,2)).getYdistance(),0);
        marin2.setLibre(true);
        entitesDistancesDifferentes.add(new Rame(1,1));
        assertEquals((marin2.marinADeplacer(entitesDistancesDifferentes,1,1,2)).getYdistance(),1);
    }*/

    @Test
    public void deplacementMarinVoileTest(){

    }

    @Test
    public void deplacementMarinGouvernailTest(){

    }

    @Test
    public void peutSeRendreALaVoileTest(){

    }
}
