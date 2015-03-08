package tests.myAnts;

import myAnts.impl.Ant;
import myAnts.impl.Grid;
import myAnts.impl.Point;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class AntTest {

    @Test
    public void testAntMove() throws Exception {
        Grid grid = new Grid();
        Ant ant = grid.createAntAt(new Point(10,10));
        Point startingPoint = new Point(ant.getCurrentLocation().x, ant.getCurrentLocation().y);

        ant.move();
        Point endPoint = ant.getCurrentLocation();

        assertNotEquals(startingPoint, endPoint);
    }

    @DataProvider(name = "foodDetection")
    public static Object[][] foodDetection() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    @Test(dataProvider = "foodDetection")
    public void testAntFoodLocationDetection(boolean foodOnLocation) {
        Grid grid = spy(new Grid());
        doReturn(foodOnLocation).when(grid).removeFood(any(Point.class), anyInt());
        Ant ant = new Ant(grid, new Point(2, 1), new Point(2,1));

        ant.move();

        assertEquals(foodOnLocation, ant.getFoodLocation().isPresent());
    }

    @Test
    public void testCommunicateFoodLocation() throws Exception {
        Grid grid = spy(new Grid());
        Ant ant = new Ant(grid, new Point(2, 2), new Point(10,10));
        Ant antWithFoodLocation = new Ant(grid, new Point(2, 1), new Point(11,11));
        antWithFoodLocation.setFoodLocation(Optional.of(new Point(42, 34)));

        List<Ant> antsOnPoint = new LinkedList<Ant>();
        antsOnPoint.add(antWithFoodLocation);
        doReturn(antsOnPoint).when(grid).getAntsOnPoint(any(Point.class));

        ant.move();

        assertEquals(ant.getFoodLocation(),antWithFoodLocation.getFoodLocation());
    }

    @DataProvider(name = "goneHome")
    public static Object[][] goHome() {
        return new Object[][] {
                {new Point(2,1), new Point(3,2), new Point(7,8)},
                {new Point(20,1), new Point(19,2), new Point(7,8)},
                {new Point(23,10), new Point(22,9), new Point(7,8)},
                {new Point(2,10), new Point(3,9), new Point(7,8)},
                {new Point(2,8), new Point(3,8), new Point(7,8)},
                {new Point(7,1), new Point(7,2), new Point(7,8)}
        };
    }

    @Test(dataProvider = "goneHome")
    public void testGoHome(Point currentPosition, Point nextPosition, Point hive) throws Exception {
        Ant ant = new Ant(new Grid(), currentPosition, hive);
        ant.setHasFood(true);

        ant.move();
        assertEquals(ant.getCurrentLocation(), nextPosition);
    }
}