package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.engine.Cockpit;

public class Main {
    public static void main (String[] args){
        Cockpit c = new Cockpit();

        c.initGame("{\n" +
                "  \"goal\": {\n" +
                "    \"mode\": \"REGATTA\",\n" +
                "    \"checkpoints\": [\n" +
                "      {\n" +
                "        \"position\": {\n" +
                "          \"x\": 1000,\n" +
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
                "  \"shipCount\": 1,\n" +
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
                "      \"width\": 2,\n" +
                "      \"length\": 1\n" +
                "    },\n" +
                "    \"entities\": [\n" +
                "      {\n" +
                "        \"x\": 0,\n" +
                "        \"y\": 0,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 1,\n" +
                "        \"y\": 0,\n" +
                "        \"type\": \"oar\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"shape\": {\n" +
                "      \"type\": \"rectangle\",\n" +
                "      \"width\": 2,\n" +
                "      \"height\": 3,\n" +
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
                "      \"x\": 1,\n" +
                "      \"y\": 0,\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Tom Pouce\"\n" +
                "    }\n" +
                "  ]\n" +
                "}");
                   // for future test


                // for future test
               String test =  c.nextRound(
                "{" +
                    "\"ship\": " +
                        "{" +
                            "\"type\": \"ship\" ," +
                            "\"life\": 100," +
                            "\"position\":" +
                                "{" +
                                    "\"x\": 10.654, " +
                                    "\"y\": 3, " +
                                    "\"orientation\": 2.05 " +
                                "}," +
                            "\"name\": \"Les copaings d'abord!\" ," +
                            "\"deck\": " +
                                "{ " +
                                    "\"width\": 2, " +
                                    "\"length\": 1 " +
                                "}," +
                            "\"entities\": " +
                                "[" +
                                    "{" +
                                        "\"x\": 0," +
                                        "\"y\": 0," +
                                        "\"type\": \"oar\"" +
                                    "}," +
                                    "{" +
                                        "\"x\": 1," +
                                        "\"y\": 0," +
                                        "\"type\": \"oar\"" +
                                    "}" +
                                "]" +
                        "}," +
                        "\"visibleEntities\": [] " +
                "}"
                );

        System.out.println(test);


    }


}
