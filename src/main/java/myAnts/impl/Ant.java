package myAnts.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Ant {
    private Point currentLocation;
    private Point hive;
    private Grid grid;
    private Optional<Point> foodLocation = Optional.empty();
    private boolean hasFood;

    public Ant(Grid grid, Point startLocation, Point hive) {
        this.grid = grid;
        this.currentLocation = new Point(startLocation);
        this.hive = new Point(hive);
    }

    public void move() {
        if (!hasFood) {
            moveOnFoodSearch();
        } else {
            goHome();
        }

    }

    private void goHome() {
        if (hive.x > currentLocation.x) {
            currentLocation.x++;
        } else if (hive.x < currentLocation.x){
            currentLocation.x--;
        }
        if (hive.y > currentLocation.y) {
            currentLocation.y++;
        } else if (hive.y < currentLocation.y){
            currentLocation.y--;
        }
    }

    private void moveOnFoodSearch() {
        // move
        this.currentLocation = randomField();
        // check if there is food
        if (grid.removeFood(this.currentLocation, 1)) {
            // food taken
            this.hasFood = true;
            this.foodLocation = Optional.of(new Point(this.currentLocation));
        }
        // ask for food location
        List<Ant> antsNearMe = grid.getAntsOnPoint(this.currentLocation);
        Optional<Ant> antWithFoodLocation = antsNearMe.stream().filter(ant -> ant.getFoodLocation().isPresent()).findFirst();

        if (antWithFoodLocation.isPresent()) {
            this.foodLocation = antWithFoodLocation.get().getFoodLocation();
        }
    }

    private Point randomField() {
        Point newPoint = new Point(currentLocation);
        Random randomGenerator = new Random();
        // clockwise direction starting right
        switch (randomGenerator.nextInt(7)) {
            case 0: // right
                newPoint.x++;
                break;
            case 1: // right + up
                newPoint.x++;
                newPoint.y++;
                break;
            case 2: // up
                newPoint.y++;
                break;
            case 3: // left + up
                newPoint.y++;
                newPoint.x--;
                break;
            case 4: // left
                newPoint.x--;
                break;
            case 5: // left + down
                newPoint.x--;
                newPoint.y--;
                break;
            case 6: // down
                newPoint.y--;
                break;
            case 7: // right + down
                newPoint.y--;
                newPoint.x++;
                break;
        }
        return newPoint;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public boolean hasFood() {
        return hasFood;
    }

    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setFoodLocation(Optional<Point> foodLocation) {
        this.foodLocation = foodLocation;
    }

    public Optional<Point> getFoodLocation() {
        return foodLocation;
    }

    public Point getHive() {
        return hive;
    }

    public void setHive(Point hive) {
        this.hive = hive;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
