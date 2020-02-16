package fr.unice.polytech.si3.qgl.theblackpearl;

//import fr.unice.polytech.si3.qgl.theblackpearl.Referee;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

public class Main {
    public static void main(String[] args) {
        Cockpit c = new Cockpit();

        String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Tom Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Edward Teach\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Tom Teach\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Luffy Pouce\"}],\"shipCount\":1}\n";

        String firstRound = "{\n" +
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
                "  \"visibleEntities\": [\n" +
                "    {\n" +
                "      \"type\": \"stream\",\n" +
                "      \"position\": {\n" +
                "        \"x\": 500,\n" +
                "        \"y\": 0,\n" +
                "        \"orientation\": 0\n" +
                "      },\n" +
                "      \"shape\": {\n" +
                "        \"type\": \"rectangle\",\n" +
                "        \"width\": 50,\n" +
                "        \"height\": 500,\n" +
                "        \"orientation\": 0\n" +
                "      },\n" +
                "      \"strength\": 40\n" +
                "    }\n" +
                "  ],\n" +
                "  \"wind\": {\n" +
                "    \"orientation\": 0,\n" +
                "    \"strength\": 110\n" +
                "  }\n" +
                "}";

        c.initGame(gameString);
        System.out.println(c.nextRound(firstRound));

/*      Cockpit c = new Cockpit();
        Referee referee = new Referee(initGame,firstRound,c);
        final int nombreTours = 10; // valeur arbitraire pour l'instant

        c.initGame(initGame);

       for(int i = 0 ; i < nombreTours ; i++ ){
           referee.setActions(c.nextRound(referee.getNextRound()));
           referee.mettreAJourNextRound(); // aller voir dans Referee la methode mettreAJourJson() non operationnelle
       }*/
    }
}

