package tutorial.mvc;

import myAnts.impl.Point;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 6/5/14.
 */
@Controller
public class AntController {
    @RequestMapping("/antPositions")
    public @ResponseBody List<Point> getAntsPosition()
    {
        List<Point> points = new LinkedList<>();
        points.add(new Point(1,1));
        return points;
    }
}
