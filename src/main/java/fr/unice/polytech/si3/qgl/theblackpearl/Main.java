package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.referee.Referee2;

public class Main {
    private static Position posBis;
    public static void main(String[] args) throws Exception {
            //WEEK 3
            //String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Tom Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
            //String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}";

            //WEEK 4
            //String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1600.0,\"y\":350.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":345.0,\"y\":1550.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":70.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"theblackpearl\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Edward Pouce\"},{\"x\":0,\"y\":3,\"id\":3,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":4,\"id\":4,\"name\":\"Edward Teach\"},{\"x\":1,\"y\":0,\"id\":5,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":1,\"id\":6,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":2,\"id\":7,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":3,\"id\":8,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":4,\"id\":9,\"name\":\"Edward Teach\"},{\"x\":2,\"y\":0,\"id\":10,\"name\":\"Luffy Pouce\"},{\"x\":2,\"y\":1,\"id\":11,\"name\":\"Luffy Teach\"},{\"x\":2,\"y\":2,\"id\":12,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":3,\"id\":13,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":4,\"id\":14,\"name\":\"Edward Pouce\"},{\"x\":3,\"y\":0,\"id\":15,\"name\":\"Edward Pouce\"},{\"x\":3,\"y\":1,\"id\":16,\"name\":\"Edward Teach\"},{\"x\":3,\"y\":2,\"id\":17,\"name\":\"Tom Teach\"},{\"x\":3,\"y\":3,\"id\":18,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":4,\"id\":19,\"name\":\"Tom Teach\"}],\"shipCount\":1}";
            //String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"theblackpearl\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":50.0}}";


            //WEEK 5
            //String gameString ="{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1500.0,\"y\":300.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":300.0,\"y\":1500.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":70.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":3,\"id\":3,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":4,\"id\":4,\"name\":\"Edward Teach\"},{\"x\":1,\"y\":0,\"id\":5,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":1,\"id\":6,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":2,\"id\":7,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":3,\"id\":8,\"name\":\"Tom Teach\"},{\"x\":1,\"y\":4,\"id\":9,\"name\":\"Jack Teach\"},{\"x\":2,\"y\":0,\"id\":10,\"name\":\"Luffy Teach\"},{\"x\":2,\"y\":1,\"id\":11,\"name\":\"Tom Pouce\"},{\"x\":2,\"y\":2,\"id\":12,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":3,\"id\":13,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":4,\"id\":14,\"name\":\"Luffy Teach\"},{\"x\":3,\"y\":0,\"id\":15,\"name\":\"Jack Teach\"},{\"x\":3,\"y\":1,\"id\":16,\"name\":\"Edward Teach\"},{\"x\":3,\"y\":2,\"id\":17,\"name\":\"Tom Teach\"},{\"x\":3,\"y\":3,\"id\":18,\"name\":\"Jack Teach\"},{\"x\":3,\"y\":4,\"id\":19,\"name\":\"Luffy Teach\"}],\"shipCount\":1}";
            //String firstRound ="{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"visibleEntities\":[{\"type\":\"stream\",\"position\":{\"x\":500.0,\"y\":0.0,\"orientation\":0.0},\"shape\":{\"type\":\"rectangle\",\"width\":300.0,\"height\":600.0,\"orientation\":0.0},\"strength\":40.0},{\"type\":\"stream\",\"position\":{\"x\":780.0,\"y\":780.0,\"orientation\":-0.52},\"shape\":{\"type\":\"rectangle\",\"width\":300.0,\"height\":900.0,\"orientation\":0.0},\"strength\":80.0},{\"type\":\"stream\",\"position\":{\"x\":1250.0,\"y\":1250.0,\"orientation\":3.36},\"shape\":{\"type\":\"rectangle\",\"width\":250.0,\"height\":900.0,\"orientation\":0.0},\"strength\":80.0}],\"wind\":{\"orientation\":0.0,\"strength\":3.36}}";

            //WEEK 6 COULOIR
            //String gameString ="{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":500.0,\"y\":500.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":1000.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":1500.0,\"y\":1500.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Tom Pouce\"},{\"x\":0,\"y\":3,\"id\":3,\"name\":\"Tom Pouce\"},{\"x\":0,\"y\":4,\"id\":4,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":0,\"id\":5,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":1,\"id\":6,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":2,\"id\":7,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":3,\"id\":8,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":4,\"id\":9,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":0,\"id\":10,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":1,\"id\":11,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":2,\"id\":12,\"name\":\"Luffy Pouce\"},{\"x\":2,\"y\":3,\"id\":13,\"name\":\"Luffy Teach\"},{\"x\":2,\"y\":4,\"id\":14,\"name\":\"Edward Teach\"},{\"x\":3,\"y\":0,\"id\":15,\"name\":\"Luffy Teach\"},{\"x\":3,\"y\":1,\"id\":16,\"name\":\"Edward Teach\"},{\"x\":3,\"y\":2,\"id\":17,\"name\":\"Tom Pouce\"},{\"x\":3,\"y\":3,\"id\":18,\"name\":\"Luffy Pouce\"},{\"x\":3,\"y\":4,\"id\":19,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
            //String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"visibleEntities\":[{\"type\":\"stream\",\"position\":{\"x\":1000.0,\"y\":1000.0,\"orientation\":0.78539816339},\"shape\":{\"type\":\"rectangle\",\"width\":100.0,\"height\":1400.0,\"orientation\":0.0},\"strength\":150.0},{\"type\":\"reef\",\"position\":{\"x\":1500.0,\"y\":500.0,\"orientation\":0.78539816339},\"shape\":{\"type\":\"rectangle\",\"width\":1250.0,\"height\":1300.0,\"orientation\":0.0}},{\"type\":\"reef\",\"position\":{\"x\":500.0,\"y\":1500.0,\"orientation\":0.78539816339},\"shape\":{\"type\":\"rectangle\",\"width\":1250.0,\"height\":1300.0,\"orientation\":0.0}}],\"wind\":{\"orientation\":0.0,\"strength\":50.0}}";

            //WEEK 6 ILE DE RE
            String gameString ="{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":300.0,\"y\":1800.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":1900.0,\"y\":3800.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":2000.0,\"y\":5240.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":3600.0,\"y\":5160.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":3300.0,\"y\":3500.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":2400.0,\"y\":2000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":1800.0,\"y\":360.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}},{\"position\":{\"x\":360.0,\"y\":320.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":150.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":360.0,\"y\":320.0,\"orientation\":1.5708},\"name\":\"Eskif\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":0,\"y\":1,\"type\":\"watch\"},{\"x\":1,\"y\":1,\"type\":\"sail\",\"openned\":false}],\"life\":600,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Tom Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Edward Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Teach\"},{\"x\":1,\"y\":1,\"id\":4,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Edward Teach\"},{\"x\":2,\"y\":0,\"id\":6,\"name\":\"Luffy Teach\"}],\"shipCount\":1}";
            String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":360.0,\"y\":320.0,\"orientation\":1.5708},\"name\":\"Eskif\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":0,\"y\":1,\"type\":\"watch\"},{\"x\":1,\"y\":1,\"type\":\"sail\",\"openned\":false}],\"life\":600,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[{\"type\":\"reef\",\"position\":{\"x\":1015.0,\"y\":835.0,\"orientation\":0.0},\"shape\":{\"type\":\"polygon\",\"vertices\":[{\"x\":285.0,\"y\":-435.0},{\"x\":185.0,\"y\":-35.0},{\"x\":-255.0,\"y\":465.0},{\"x\":-215.0,\"y\":5.0}],\"orientation\":0.0}},{\"type\":\"reef\",\"position\":{\"x\":1655.0,\"y\":1145.0,\"orientation\":0.0},\"shape\":{\"type\":\"polygon\",\"vertices\":[{\"x\":-455.0,\"y\":-345.0},{\"x\":145.0,\"y\":-105.0},{\"x\":225.0,\"y\":95.0},{\"x\":85.0,\"y\":355.0}],\"orientation\":0.0}},{\"type\":\"reef\",\"position\":{\"x\":1172.0,\"y\":1548.0,\"orientation\":0.0},\"shape\":{\"type\":\"polygon\",\"vertices\":[{\"x\":28.0,\"y\":-748.0},{\"x\":568.0,\"y\":-48.0},{\"x\":68.0,\"y\":712.0},{\"x\":-252.0,\"y\":332.0},{\"x\":-412.0,\"y\":-248.0}],\"orientation\":0.0}},{\"type\":\"stream\",\"position\":{\"x\":1000.0,\"y\":1000.0,\"orientation\":0.78539816339},\"shape\":{\"type\":\"rectangle\",\"width\":100.0,\"height\":1400.0,\"orientation\":0.0},\"strength\":150.0}],\"wind\":{\"orientation\":0.0,\"strength\":150.0}}";

            Cockpit cockpit = new Cockpit();
            Referee2 referee2 = new Referee2(gameString,firstRound, cockpit);
            referee2.startGame(400);
            /*cockpit.initGame(gameString);
            System.out.println(cockpit.nextRound(firstRound));*/

    }
}



