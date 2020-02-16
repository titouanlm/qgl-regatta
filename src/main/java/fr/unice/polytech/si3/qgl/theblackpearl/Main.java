package fr.unice.polytech.si3.qgl.theblackpearl;

//import fr.unice.polytech.si3.qgl.theblackpearl.Referee;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

public class Main {
    public static void main(String[] args) {
        Cockpit c = new Cockpit();
        //String initGame = "{ \"goal\": { \"mode\": \"REGATTA\", \"checkpoints\": [ { \"position\": { \"x\": 50, \"y\": 1000 }, \"shape\": { \"type\": \"circle\", \"radius\": 50.0 } }, { \"position\": { \"x\": 500, \"y\": -200 }, \"shape\": { \"type\": \"circle\", \"radius\": 50.0 } }, { \"position\": { \"x\": -500, \"y\": 1250 }, \"shape\": { \"type\": \"circle\", \"radius\": 60.0 } } ] }, \"ship\": { \"name\": \"Péquod\", \"life\": 300, \"deck\": { \"length\": 4, \"width\": 3 }, \"entities\": [ { \"type\": \"oar\", \"x\": 0, \"y\": 0 }, { \"type\": \"oar\", \"x\": 0, \"y\": 2 }, { \"type\": \"oar\", \"x\": 1, \"y\": 0 }, { \"type\": \"oar\", \"x\": 1, \"y\": 2 }, { \"type\": \"oar\", \"x\": 2, \"y\": 0 }, { \"type\": \"oar\", \"x\": 2, \"y\": 2 }, { \"type\": \"rudder\", \"x\": 3, \"y\": 1 } ] }, \"startingPositions\": [ { \"x\": 0.0, \"y\": 0.0, \"orientation\": 0.0 } ], \"wind\": { \"direction\": 0, \"strenght\": 0 }, \"seaEntities\": [], \"maxRound\": 300, \"minumumCrewSize\": 4, \"maximumCrewSize\": 4 }";

        String initGame = "{\n" +
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

        c.initGame(initGame);
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

