package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.Action;
import fr.unice.polytech.si3.qgl.theblackpearl.actions.MOVING;
import fr.unice.polytech.si3.qgl.theblackpearl.decisions.*;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;
import fr.unice.polytech.si3.qgl.theblackpearl.sea_elements.Vent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalleDesCommandesTest {

    private Vent vent;
    private InitGame parsedInitGame;
    private SalleDesCommandes salleDesCommandes;
    private List<Double> meilleurAngleRealisable;
    private List<Action> actionsNextRound;

    @BeforeEach
    public void setUp() throws Exception {
        actionsNextRound = new ArrayList<>();
        vent = new Vent(0,110);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            parsedInitGame = objectMapper.readValue(gameString, InitGame.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        salleDesCommandes = new SalleDesCommandes(parsedInitGame, vent, actionsNextRound);
        salleDesCommandes.creationTableauMarins();
        Captain captain = new Captain(parsedInitGame, vent);
        captain.determinerCheckpointAViser();
        meilleurAngleRealisable = captain.meilleurAngleRealisable();
    }

    @Test
    public void creationTableauMarinsTest(){
        //assertEquals(salleDesCommandes.getTableauPositionMarinOriginale().length,6);
    }


    @Test
    public void priseEnComptePositionMarinsTest(){
        salleDesCommandes.priseEnComptePositionMarins();
        assertEquals(parsedInitGame.getMarins().get(0).getX(),0);
        assertEquals(parsedInitGame.getMarins().get(0).getY(),0);
        assertEquals(parsedInitGame.getMarins().get(1).getX(),0);
        assertEquals(parsedInitGame.getMarins().get(1).getY(),1);
        assertEquals(parsedInitGame.getMarins().get(2).getX(),0);
        assertEquals(parsedInitGame.getMarins().get(2).getY(),2);
        assertEquals(parsedInitGame.getMarins().get(3).getX(),1);
        assertEquals(parsedInitGame.getMarins().get(3).getY(),0);
        assertEquals(parsedInitGame.getMarins().get(4).getX(),1);
        assertEquals(parsedInitGame.getMarins().get(4).getY(),1);
        assertEquals(parsedInitGame.getMarins().get(5).getX(),1);
        assertEquals(parsedInitGame.getMarins().get(5).getY(),2);
    }

    @Test
    public void restaurationPositionMarinsTest(){
        salleDesCommandes.priseEnComptePositionMarins();
        for (Marin m : parsedInitGame.getMarins()){
            m.setX(456);m.setY(789);
        }
        salleDesCommandes.restaurationPositionMarins();
        assertEquals(parsedInitGame.getMarins().get(0).getX(),0);
        assertEquals(parsedInitGame.getMarins().get(0).getY(),0);
        assertEquals(parsedInitGame.getMarins().get(1).getX(),0);
        assertEquals(parsedInitGame.getMarins().get(1).getY(),1);
        assertEquals(parsedInitGame.getMarins().get(2).getX(),0);
        assertEquals(parsedInitGame.getMarins().get(2).getY(),2);
        assertEquals(parsedInitGame.getMarins().get(3).getX(),1);
        assertEquals(parsedInitGame.getMarins().get(3).getY(),0);
        assertEquals(parsedInitGame.getMarins().get(4).getX(),1);
        assertEquals(parsedInitGame.getMarins().get(4).getY(),1);
        assertEquals(parsedInitGame.getMarins().get(5).getX(),1);
        assertEquals(parsedInitGame.getMarins().get(5).getY(),2);
    }

//    @Test
//    public void preConfigurationRamesBateauTest() {
//        ArrayList<Marin> marinsOccupes = new ArrayList<>();
//        Calculator calculateur = new Calculator();
//        calculateur.setNombreMarinAplacer(parsedInitGame.getBateau().nombreMarinsRamesBabordTribordRames(meilleurAngleRealisable.get(0), parsedInitGame.getBateau().getListRames()));
//        calculateur.setNombreMarinAplacerCopie(calculateur.getNombreMarinAplacer().clone());
//        salleDesCommandes.preConfigurationRamesBateau(true, 0, calculateur.getNombreMarinAplacerCopie(), meilleurAngleRealisable, calculateur, salleDesCommandes.meilleurAngleRealisablePosition,marinsOccupes);
//        //assertEquals(salleDesCommandes.meilleurAngleRealisablePosition, 0);
//        assertEquals(calculateur.getNombreMarinAplacer()[0], 3);
//        assertEquals(calculateur.getNombreMarinAplacer()[1], 0);
//        calculateur.setNombreMarinAplacer(new int[]{3, -1});
//        salleDesCommandes.preConfigurationRamesBateau(true, 0, calculateur.getNombreMarinAplacerCopie(), meilleurAngleRealisable, calculateur, salleDesCommandes.meilleurAngleRealisablePosition,marinsOccupes);
//        //assertEquals(salleDesCommandes.meilleurAngleRealisablePosition, 0);
//        assertEquals(calculateur.getNombreMarinAplacer()[0],3);
//        calculateur.setNombreMarinAplacer(new int[]{3, 2});
//        calculateur.setNombreMarinAplacerCopie(new int[]{3, 2});
//        salleDesCommandes.preConfigurationRamesBateau(false, 1, calculateur.getNombreMarinAplacerCopie(), meilleurAngleRealisable, calculateur, salleDesCommandes.meilleurAngleRealisablePosition,marinsOccupes);
//        assertEquals(calculateur.getNombreMarinAplacer()[0],2);
//        assertEquals(calculateur.getNombreMarinAplacer()[1],1);
//
//    }

    @Test
    public void configurationRamesTest(){

    }


    @Test
    public void configurationGouvernailTest(){
        salleDesCommandes.configurationGouvernail();
        assertEquals((actionsNextRound.get(0)).getSailorId(),0);
        assertEquals(( actionsNextRound.get(0)).getType(),"MOVING");
        assertEquals(((MOVING) actionsNextRound.get(0)).getYdistance(),0);
        assertEquals(((MOVING) actionsNextRound.get(0)).getXdistance(),5);
        assertEquals(actionsNextRound.size(),1);
    }

//    @Test
//    public void utilisationVoileTest(){
//        vent.setOrientation(Math.PI);
//        salleDesCommandes.utilisationVoile(parsedInitGame,actionsNextRound);
//        assertEquals(((MOVING) actionsNextRound.get(0)).getYdistance(),1);
//        assertEquals(((MOVING) actionsNextRound.get(0)).getXdistance(),2);
//        assertEquals(actionsNextRound.size(),1);
//        actionsNextRound = new ArrayList<>();
//        salleDesCommandes.utilisationVoile(parsedInitGame,actionsNextRound);
//        assertEquals(actionsNextRound.size(),0);
//        vent.setOrientation(0);
//        actionsNextRound = new ArrayList<>();
//        parsedInitGame.getBateau().getVoile().setOpenned(true);
//        salleDesCommandes.utilisationVoile(parsedInitGame,actionsNextRound);
//        assertEquals(((MOVING) actionsNextRound.get(0)).getYdistance(),0);
//        assertEquals(((MOVING) actionsNextRound.get(0)).getXdistance(),2);
//        assertEquals(actionsNextRound.size(),1);
//        actionsNextRound = new ArrayList<>();
//        salleDesCommandes.utilisationVoile(parsedInitGame,actionsNextRound);
//        assertEquals(actionsNextRound.size(),0);
//
//    }

    @Test
    public void utilisationVoileOuiNon(){
        assertFalse(salleDesCommandes.utilisationVoileOuiNon(parsedInitGame));
        vent.setOrientation(Math.PI);
        assertTrue(salleDesCommandes.utilisationVoileOuiNon(parsedInitGame));
    }


    private String gameString = "{\n" +
            "  \"goal\": {\n" +
            "    \"mode\": \"REGATTA\",\n" +
            "    \"checkpoints\": [\n" +
            "      {\n" +
            "        \"position\": {\n" +
            "          \"x\": -1000,\n" +
            "          \"y\": -50,\n" +
            "          \"orientation\": 0\n" +
            "        },\n" +
            "        \"shape\": {\n" +
            "          \"type\": \"circle\",\n" +
            "          \"radius\": 50\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"position\": {\n" +
            "          \"x\": 0,\n" +
            "          \"y\": 0,\n" +
            "          \"orientation\": 0\n" +
            "        },\n" +
            "        \"shape\": {\n" +
            "          \"type\": \"circle\",\n" +
            "          \"radius\": 50\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"ship\": {\n" +
            "    \"type\": \"ship\",\n" +
            "    \"life\": 100,\n" +
            "    \"position\": {\n" +
            "      \"x\": 0,\n" +
            "      \"y\": 0,\n" +
            "      \"orientation\": 0\n" +
            "    },\n" +
            "    \"name\": \"Les copaings d'abord!\",\n" +
            "    \"deck\": {\n" +
            "      \"width\": 3,\n" +
            "      \"length\": 6\n" +
            "    },\n" +
            "    \"entities\": [\n" +
            "      {\n" +
            "        \"x\": 1,\n" +
            "        \"y\": 0,\n" +
            "        \"type\": \"oar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 1,\n" +
            "        \"y\": 2,\n" +
            "        \"type\": \"oar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 3,\n" +
            "        \"y\": 0,\n" +
            "        \"type\": \"oar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 3,\n" +
            "        \"y\": 2,\n" +
            "        \"type\": \"oar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 4,\n" +
            "        \"y\": 0,\n" +
            "        \"type\": \"oar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 4,\n" +
            "        \"y\": 2,\n" +
            "        \"type\": \"oar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 2,\n" +
            "        \"y\": 1,\n" +
            "        \"type\": \"sail\",\n" +
            "        \"openned\": false\n" +
            "      },\n" +
            "      {\n" +
            "        \"x\": 5,\n" +
            "        \"y\": 0,\n" +
            "        \"type\": \"rudder\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"shape\": {\n" +
            "      \"type\": \"rectangle\",\n" +
            "      \"width\": 3,\n" +
            "      \"height\": 6,\n" +
            "      \"orientation\": 0\n" +
            "    }\n" +
            "  },\n" +
            "  \"sailors\": [\n" +
            "    {\n" +
            "      \"x\": 0,\n" +
            "      \"y\": 0,\n" +
            "      \"id\": 0,\n" +
            "      \"name\": \"Edward Teach\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"x\": 0,\n" +
            "      \"y\": 1,\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"Edward Pouce\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"x\": 0,\n" +
            "      \"y\": 2,\n" +
            "      \"id\": 2,\n" +
            "      \"name\": \"Tom Pouce\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"x\": 1,\n" +
            "      \"y\": 0,\n" +
            "      \"id\": 3,\n" +
            "      \"name\": \"Jack Teach\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"x\": 1,\n" +
            "      \"y\": 1,\n" +
            "      \"id\": 4,\n" +
            "      \"name\": \"Jack Teach\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"x\": 1,\n" +
            "      \"y\": 2,\n" +
            "      \"id\": 5,\n" +
            "      \"name\": \"Tom Pouce\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
