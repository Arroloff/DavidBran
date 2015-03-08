package tests.myAnts;

import myAnts.impl.Ant;
import myAnts.impl.Grid;
import myAnts.impl.Point;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.*;

public class GridTest {

    @Test
    public void testGetAntsOnPoint() {
        Grid grid = new Grid();
        Point antsMeetingPoint = new Point(1, 0);
        Ant ant1 = grid.createAntAt(antsMeetingPoint);
        Ant ant2 = grid.createAntAt(new Point(1, -1));
        Ant ant3 = grid.createAntAt(antsMeetingPoint);

        List<Ant> antsOnPoint = grid.getAntsOnPoint(antsMeetingPoint);

        assertEquals(antsOnPoint.size(), 2);
        assertTrue(antsOnPoint.contains(ant1));
        assertTrue(antsOnPoint.contains(ant3));
    }

    @Test
    public void testAddNewFoodSource() throws Exception {
        Point foodPoint = new Point(2, 3);
        int foodAmount = 4;

        Grid grid = new Grid();
        grid.addFood(foodPoint, foodAmount);
        grid.addFood(foodPoint, foodAmount);

        assertEquals(foodAmount * 2, (int) grid.foodOnPoint(foodPoint).get());
    }

    @Test
    public void testRemoveFoodSomeFoodLeft() throws Exception {
        Point foodPoint = new Point(2, 3);
        Grid grid = new Grid();

        grid.addFood(foodPoint, 2);
        grid.removeFood(foodPoint, 1);

        assertEquals(1,(int)grid.foodOnPoint(foodPoint).get());
    }


    @Test
    public void testRemoveFoodNoFoodLeft() throws Exception {
        Point foodPoint = new Point(2, 3);
        Grid grid = new Grid();

        grid.addFood(foodPoint, 2);
        grid.removeFood(foodPoint, 2);

        assertFalse(grid.foodOnPoint(foodPoint).isPresent());
    }

    @Test
    public void testRemove2MuchFood() throws Exception {
        Point foodPoint = new Point(2, 3);
        Grid grid = new Grid();

        grid.addFood(foodPoint, 2);
        boolean removedFood = grid.removeFood(foodPoint, 3);

        assertFalse(removedFood);
        assertEquals(2, (int) grid.foodOnPoint(foodPoint).get());
    }

    @Test
    public void testNewRound() throws Exception {
        Grid grid = new Grid();
        Ant antOne = mock(Ant.class);
        Ant antTwo = mock(Ant.class);
        grid.getAnts().add(antOne);
        grid.getAnts().add(antTwo);

        grid.nextTurn();

        verify(antOne, times(1)).move();
        verify(antTwo, times(1)).move();
    }
}