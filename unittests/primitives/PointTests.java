package primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for primitives.Point class
 */
public class PointTests {
    private static final double DELTA = 0.000001;

    /**
     * Test method for {@link primitives.Point#add(Vector)}.
     */
    @Test
    public void testAdd() {
        Point p = new Point(1, 2, 3);
        Vector v1 = new Vector(4, 5, 6);
        Vector v2 = new Vector(-1, -2, -3);
        Point expected = new Point(5, 7, 9);
        Point p0 = new Point(Double3.ZERO);
        // ============ Equivalence Partitions Tests ==============
        // test Add function
        assertEquals(expected, p.add(v1), "ERROR: (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        // test if Add function can be equal to (0,0,0)
        assertEquals(p0, p.add(v2), "ERROR: (point + vector) = center of coordinates does not work correctly");
    }
    
    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    public void testSubtract() {
        Point p1 = new Point(5, 6, 7);
        Point p2 = new Point(1, 2, 3);
        Vector expected = new Vector(4, 4, 4);
        // =============== Boundary Values Tests ==================
        // test if exception is thrown
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "ERROR: (point - itself) does not throw an exception");
        // ============ Equivalence Partitions Tests ==============
        // test Subtract function
        assertEquals(expected, p1.subtract(p2), "ERROR: (point2 - point1) does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}.
     */
    @Test
    public void testDistance() {
        double d0 = 0.0;
        double expected = 3.0;
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(1, 2, 0);
        // =============== Boundary Values Tests ==================
        // test if distance function can be equal to 0.0
        assertEquals(d0, p1.distance(p1), "ERROR: point distance to itself is not zero");
        // ============ Equivalence Partitions Tests ==============
        // test distance function
        assertEquals(expected, p1.distance(p2), DELTA, "ERROR: distance between points to itself is wrong");
        assertEquals(expected, p2.distance(p1), DELTA, "ERROR: distance between points to itself is wrong");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}.
     */
    @Test
    public void testDistanceSquared() {
        double d0 = 0.0;
        double expected = 6.0;
        Point p1 = new Point(2, 2, 2);
        Point p2 = new Point(1, 1, 0);
        // =============== Boundary Values Tests ==================
        // test if distanceSquared function can be equal to 0.0
        assertEquals(d0, p1.distanceSquared(p1), DELTA, "ERROR: point squared distance to itself is not zero");
        // ============ Equivalence Partitions Tests ==============
        // test distanceSquared function
        assertEquals(expected, p1.distanceSquared(p2), DELTA, "ERROR: squared distance between points is wrong");
        assertEquals(expected, p2.distanceSquared(p1), DELTA, "ERROR: squared distance between points is wrong");
    }
}
