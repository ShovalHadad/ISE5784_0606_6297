package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTests {
    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Ray ray2 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Ray ray3 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // =============== Boundary Values Tests ==================
        // Test Negative distance
        assertEquals(new Point(-1, 0, 0),
                ray1.getPoint(-1),
                "Negative distance");

        // Test Zero distance
        assertEquals(new Point(0, 0, 0),
                ray2.getPoint(0),
                "Zero distance");

        // Test Positive distance
        assertEquals(new Point(2, 0, 0),
                ray3.getPoint(2),
                "Positive distance");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        List<Point> points = new LinkedList<>();

        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(2, 2, 2);
        Point p3 = new Point(3, 3, 3);

        points.add(p1);
        points.add(p2);
        points.add(p3);

        Vector vector = new Vector(0, -0.5, 0);

        // ============ Equivalence Partitions Tests ==============
        //TC01: The closest point is in the middle of the list
        assertEquals(p2,
                (new Ray(new Point(2, 2.5, 2), vector)).findClosestPoint(points),
                "The point in the middle");

        // =============== Boundary Values Tests ==================
        //TC02: The closest point is the first point in the list
        assertEquals(p1,
                (new Ray(new Point(1, 1.25, 1), vector)).findClosestPoint(points),
                "The point is the first one");

        //TC03: The closest point is the last point in the list
        Ray ray = new Ray(new Point(3, 3.5, 3), vector);
        assertEquals(p3, ray.findClosestPoint(points),
                "The point is the last one");

        //TC04: The list is null
        points.clear();
        assertNull(ray.findClosestPoint(points),
                "The list is empty");
    }

}
