package geometries;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 */
public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    public void findIntersections() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================
        //TC01: empty geometries list
        assertNull(geometries.findIntersections(new Ray(new Vector(1.0, 0.0, 5.0), new Point(0.0, 1.0, 0.0))));

        geometries.add(new Plane(new Point(1.0, 1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
        geometries.add(new Triangle(new Point(1.0, 0.0, 0.0), new Point(0.0, 1.0, 0.0), new Point(0.0, 0.0, 1.0)));
        geometries.add(new Sphere(1.0, new Point(1.0, 0.0, 0.0)));
        //TC02: each geometry doesn't have intersection points
        assertNull(geometries.findIntersections(new Ray(new Vector(0.0, -1.0, 0.0), new Point(0.0, 0.0, 2.0))));

        List<Point> points = geometries.findIntersections(new Ray(new Vector(0.0, 0.0, 1.0), new Point(0.0, 5.0, -1.0)));
        //TC03: just one geometry has intersections point
        assertEquals(1, points.size());

        //TC04: all the geometries have intersection points
        Geometries geometries1 = new Geometries(
                new Sphere(0.5, new Point(0, 0, 2)),
                new Polygon(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 0, 0),
                        new Point(0, -1, 0)
                ),
                new Triangle(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                )
        );
        List<Point> result = geometries1.findIntersections(new Ray(new Vector(0, 0, 1), new Point(0.2, 0.2, -0.6)));
        assertEquals(4,
                result.size(),
                "All geometries intersects");

        // ============ Equivalence Partitions Tests ==============
        //TC05: part of the geometries has intersection points
        assertEquals(2,
                geometries.findIntersections(new Ray(new Vector(0.0, 0.0, 1.0), new Point(1.0, 0.0, -1.0))).size());
    }
}
