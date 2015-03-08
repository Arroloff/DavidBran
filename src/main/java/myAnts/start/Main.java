package myAnts.start;

import myAnts.impl.Grid;
import myAnts.impl.Point;

public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid();

        grid.createAntAt(new Point(10, 10));

        grid.addFood(new Point(9,9), 1);

        try {
            while (true) {
                Thread.sleep(1000);
                grid.getAnts().stream().forEach(ant -> System.out.println("Ant at Position: " + ant.getCurrentLocation() + "and has " + (ant.hasFood() ? "" : "no") + " food"));
                grid.nextTurn();
            }
        } catch (Exception e) {
            System.out.println("Thread killed");
        }

    }
}
