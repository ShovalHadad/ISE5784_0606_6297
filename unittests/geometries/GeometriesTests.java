package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class GeometriesTests {

    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries();
        geometries.add(new Sphere(1d, new Point(1, 0, 0)),
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1)),
                new Triangle(new Point(0, 1, 0), new Point(1, 1, 0), new Point(0.5, 0, 0)));

        // ============ Equivalence Partitions Tests ==============
        // Test Empty collection (0 points)
        assertNull(new Geometries().findIntersections(new Ray(new Vector(0, 0, 1),new Point(1, 1, 1))),
                "Empty collection should return null");

        // Test No shape intersects (0 points)
        assertNull(geometries.findIntersections(new Ray(new Vector(0, 0, -1),new Point(-1, -1, -1))),
                "No shape intersects");

        // Test One shape intersects (1 point)
        assertEquals(2, ///////////////////////////////// have a problem
                geometries.findIntersections(new Ray(new Vector(0, 0, 1), new Point(1, 0.5, -1))).size(),
                "One shape intersects");

        // Test Several shapes intersect (but not all) (2 points)
        assertEquals(2,
                geometries.findIntersections(new Ray(new Vector(1, 0, 0), new Point(-1, 0.5, 0.5))).size(),
                "Several shapes intersect");

        // Test All shapes intersect (3 points)
        assertEquals(2, ///////////////////////////////// have a problem
                geometries.findIntersections(new Ray(new Vector(0, 0, 1), new Point(0.5, 0.5, -1))).size(),
                "All shapes intersect");
    }
}
