package fr.unice.polytech.si3.qgl.theblackpearl;

import fr.unice.polytech.si3.qgl.theblackpearl.referee.Referee2;

public class Main {
    public static void main(String[] args) {
        //Cockpit c = new Cockpit();

        String gameString = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":50.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":500.0,\"y\":-200.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":50.0}},{\"position\":{\"x\":-500.0,\"y\":1250.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":60.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":0,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Edward Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Teach\"}],\"shipCount\":1}";

        String firstRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":0,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[]}";
        //String secondRound = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.8882140115877203,\"y\":143.35098771177786,\"orientation\":2.61799387799148},\"name\":\"Péquod\",\"deck\":{\"width\":3,\"length\":4},\"entities\":[{\"x\":0,\"y\":0,\"type\":\"oar\"},{\"x\":0,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}";
        //c.initGame(gameString);
        //System.out.println(c.nextRound(firstRound));
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

        Cockpit cockpit = new Cockpit();
        Referee2 referee2 = new Referee2(gameString,firstRound, cockpit);
        referee2.startGame();
    }
}

