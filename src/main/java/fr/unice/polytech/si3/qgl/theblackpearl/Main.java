package fr.unice.polytech.si3.qgl.theblackpearl;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.theblackpearl.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.theblackpearl.referee.Referee2;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
            //WEEK 3
            //String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Tom Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
            //String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}";

            //WEEK 4
            String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1600.0,\"y\":350.0,\"orientation\":0.0},\"shape\":{\"type\":\"rectangle\",\"width\":50.0,\"height\":80.0}},{\"position\":{\"x\":345.0,\"y\":1550.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":70.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Edward Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":3,\"id\":3,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":4,\"id\":4,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":0,\"id\":5,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":1,\"id\":6,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":2,\"id\":7,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":3,\"id\":8,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":4,\"id\":9,\"name\":\"Edward Teach\"},{\"x\":2,\"y\":0,\"id\":10,\"name\":\"Edward Teach\"},{\"x\":2,\"y\":1,\"id\":11,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":2,\"id\":12,\"name\":\"Edward Teach\"},{\"x\":2,\"y\":3,\"id\":13,\"name\":\"Jack Pouce\"},{\"x\":2,\"y\":4,\"id\":14,\"name\":\"Luffy Teach\"},{\"x\":3,\"y\":0,\"id\":15,\"name\":\"Edward Pouce\"},{\"x\":3,\"y\":1,\"id\":16,\"name\":\"Luffy Teach\"},{\"x\":3,\"y\":2,\"id\":17,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":3,\"id\":18,\"name\":\"Edward Teach\"},{\"x\":3,\"y\":4,\"id\":19,\"name\":\"Edward Teach\"}],\"shipCount\":1}";
            String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":765.1086506594644,\"y\":118.1824737006437,\"orientation\":0.17453292519943317},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":true}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"visibleEntities\":[{\n" +
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
                    "    }],\"wind\":{\"orientation\":0.0,\"strength\":50.0}}\n";
            Cockpit cockpit = new Cockpit();
            Referee2 referee2 = new Referee2(gameString,firstRound, cockpit);
            referee2.startGame(400);
            /*cockpit.initGame(gameString);
            System.out.println(cockpit.nextRound(firstRound));*/
            System.out.println("**********************************************\n\n\n");

        System.out.println(referee2.getJsonPositionsShip());
        System.out.println(referee2.getJsonCheckpoints());
    }
}

