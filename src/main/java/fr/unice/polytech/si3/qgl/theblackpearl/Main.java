package fr.unice.polytech.si3.qgl.theblackpearl;

//import fr.unice.polytech.si3.qgl.theblackpearl.Referee;
import fr.unice.polytech.si3.qgl.theblackpearl.engine.InitGame;

public class Main {
    public static void main(String[] args) {
        Cockpit c = new Cockpit();
        /*String gameString2 = "{\n" +
                "\"goal\":{\n" +
                "\"mode\":\"REGATTA\",\n" +
                "\"checkpoints\":[{" +
                "\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\n" +
                "\"shape\":{\"type\":\"circle\",\"radius\":50.0}},\n" +
                "{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\n" +
                "\"shape\":{\"type\":\"circle\",\"radius\":50.0}},\n" +
                "{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\n" +
                "\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\n" +
                "\"ship\":{\n" +
                "\"type\":\"ship\",\n" +
                "\"life\":300,\n" +
                "\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\n" +
                "\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\n" +
                "\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},\n" +
                "{\"x\":0,\"y\":2,\"type\":\"oar\"},\n" +
                "{\"x\":1,\"y\":0,\"type\":\"oar\"},\n" +
                "{\"x\":1,\"y\":2,\"type\":\"oar\"},\n" +
                "{\"x\":2,\"y\":0,\"type\":\"oar\"},\n" +
                "{\"x\":2,\"y\":2,\"type\":\"oar\"},\n" +
                "{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\n" +
                "\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\n" +
                "\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},\n" +
                "{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Tom Pouce\"},\n" +
                "{\"x\":0,\"y\":2,\"id:2,\"name\":\"Tom Pouce\"},\n" +
                "{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Pouce\"}],\n" +
                "\"shipCount\":1}\n";*/
        String gameString =  "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1600.0,\"y\":350.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":345.0,\"y\":1550.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":70.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Edward Teach\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":3,\"id\":3,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":4,\"id\":4,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":5,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":1,\"id\":6,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":2,\"id\":7,\"name\":\"Tom Pouce\"},{\"x\":1,\"y\":3,\"id\":8,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":4,\"id\":9,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":0,\"id\":10,\"name\":\"Luffy Teach\"},{\"x\":2,\"y\":1,\"id\":11,\"name\":\"Luffy Pouce\"},{\"x\":2,\"y\":2,\"id\":12,\"name\":\"Tom Teach\"},{\"x\":2,\"y\":3,\"id\":13,\"name\":\"Jack Pouce\"},{\"x\":2,\"y\":4,\"id\":14,\"name\":\"Edward Teach\"},{\"x\":3,\"y\":0,\"id\":15,\"name\":\"Tom Pouce\"},{\"x\":3,\"y\":1,\"id\":16,\"name\":\"Jack Teach\"},{\"x\":3,\"y\":2,\"id\":17,\"name\":\"Tom Pouce\"},{\"x\":3,\"y\":3,\"id\":18,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":4,\"id\":19,\"name\":\"Luffy Teach\"}],\"shipCount\":1}\n";
        String firstRound2 = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}\n";
        String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Drakkar\",\"deck\":{\"width\":5,\"length\":11},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"},{\"x\":8,\"y\":0,\"type\":\"oar\"},{\"x\":9,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":4,\"type\":\"oar\"},{\"x\":2,\"y\":4,\"type\":\"oar\"},{\"x\":3,\"y\":4,\"type\":\"oar\"},{\"x\":4,\"y\":4,\"type\":\"oar\"},{\"x\":5,\"y\":4,\"type\":\"oar\"},{\"x\":6,\"y\":4,\"type\":\"oar\"},{\"x\":7,\"y\":4,\"type\":\"oar\"},{\"x\":8,\"y\":4,\"type\":\"oar\"},{\"x\":9,\"y\":4,\"type\":\"oar\"},{\"x\":10,\"y\":4,\"type\":\"rudder\"},{\"x\":5,\"y\":2,\"type\":\"sail\",\"openned\":false}],\"life\":2200,\"shape\":{\"type\":\"rectangle\",\"width\":5.0,\"height\":11.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}";
        //String secondRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.8882140115877203,\"y\":143.35098771177786,\"orientation\":2.61799387799148},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}";
        c.initGame(gameString);
        System.out.println(c.nextRound(firstRound));
        //System.out.println(c.nextRound(secondRound));
        //System.out.println(c.getLogs());

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

