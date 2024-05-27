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
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));
        Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(1, 1, 0), new Point(0.5, 0, 0));
        geometries.add(sphere, plane, triangle);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Empty collection (0 points)
        Geometries emptyGeometries = new Geometries();
        assertNull(emptyGeometries.findIntersections(new Ray(new Vector(0, 0, 1),new Point(1, 1, 1))),
                "Empty collection should return null");

        // TC02: No shape intersects (0 points)
        assertNull(geometries.findIntersections(new Ray(new Vector(0, 0, -1),new Point(-1, -1, -1))),
                "No shape intersects");

        // TC03: One shape intersects (1 point)
        assertEquals(1, geometries.findIntersections(new Ray(new Vector(0, 0, 1), new Point(1, 0.5, -1))).size(),
                "One shape intersects");

        // TC04: Several shapes intersect (but not all) (2 points)
        assertEquals(2, geometries.findIntersections(new Ray(new Vector(1, 0, 0), new Point(-1, 0.5, 0.5))).size(),
                "Several shapes intersect");

        // TC05: All shapes intersect (3 points)
        assertEquals(3, geometries.findIntersections(new Ray(new Vector(0, 0, 1), new Point(0.5, 0.5, -1))).size(),
                "All shapes intersect");
    }
}
