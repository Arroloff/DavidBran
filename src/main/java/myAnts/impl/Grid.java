package myAnts.impl;

import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Grid {

    private Point hive = new Point(10,10);
    private List<Ant> ants = new LinkedList<>();
    private Map<Point, Integer> foodLocations = new HashMap<>();

    public List<Ant> getAntsOnPoint(final Point antBirthPoint) {
        return ants.stream().filter(ant -> ant.getCurrentLocation().equals(antBirthPoint)).collect(toList());
    }

    public List<Ant> getAnts() {
        return ants;
    }

    // TODO das durch ne gescheite remove Methode ersetzen?
    public void setAnts(List<Ant> ants) {
        this.ants = ants;
    }

    public Ant createAntAt(Point point) {
        Ant newAnt = new Ant(this, point, hive);
        ants.add(newAnt);
        return newAnt;
    }

    public Point getHive() {
        return hive;
    }

    public void setHive(Point hive) {
        this.hive = hive;
    }

    public void addFood(Point foodPoint, int newFood) {
        Integer foodAmount = this.foodLocations.get(foodPoint) != null ? this.foodLocations.get(foodPoint) : 0;
        this.foodLocations.put(foodPoint, foodAmount+newFood);
    }

    public Optional<Integer> foodOnPoint(Point foodPoint) {
        return Optional.ofNullable(foodLocations.get(foodPoint));
    }

    public boolean removeFood(Point foodPoint, int foodRemoved) {
        Integer foodAmount = this.foodLocations.get(foodPoint) != null ? this.foodLocations.get(foodPoint) : 0;

        if (foodAmount < foodRemoved) {
            return false;
        }

        int newFoodAmount = foodAmount - foodRemoved;
        if (newFoodAmount == 0) {
            this.foodLocations.remove(foodPoint);
        } else {
            this.foodLocations.put(foodPoint, newFoodAmount);
        }
        return true;
    }

    public void nextTurn() {
//        ants.stream().forEach(ant -> ant.move());
        for (Ant ant : ants) {
            ant.move();
        }
    }
}
