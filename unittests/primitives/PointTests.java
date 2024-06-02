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
        Point p123 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // test Add function
        assertEquals(new Point(5, 7, 9),
                p123.add(new Vector(4, 5, 6)),
                "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================
        // test if Add function can be equal to (0,0,0)
        assertEquals(new Point(Double3.ZERO),
                p123.add(new Vector(-1, -2, -3)),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }
    
    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    public void testSubtract() {
        Point p567 = new Point(5, 6, 7);

        // =============== Boundary Values Tests ==================
        // test if exception is thrown
        assertThrows(IllegalArgumentException.class,
                () -> p567.subtract(p567),
                "ERROR: (point - itself) does not throw an exception");

        // ============ Equivalence Partitions Tests ==============
        // test Subtract function
        assertEquals(new Vector(4, 4, 4),
                p567.subtract(new Point(1, 2, 3)),
                "ERROR: (point2 - point1) does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}.
     */
    @Test
    public void testDistance() {
        Point p123 = new Point(1, 2, 3);
        Point p120 = new Point(1, 2, 0);

        // =============== Boundary Values Tests ==================
        // test if distance function can be equal to 0.0
        assertEquals(0.0,
                p123.distance(p123),
                "ERROR: point distance to itself is not zero");

        // ============ Equivalence Partitions Tests ==============
        // test distance function
        assertEquals(3.0,
                p123.distance(p120),
                DELTA,
                "ERROR: distance between points to itself is wrong");
        assertEquals(3.0,
                p120.distance(p123),
                DELTA,
                "ERROR: distance between points to itself is wrong");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}.
     */
    @Test
    public void testDistanceSquared() {
        Point p222 = new Point(2, 2, 2);
        Point p110 = new Point(1, 1, 0);

        // =============== Boundary Values Tests ==================
        // test if distanceSquared function can be equal to 0.0
        assertEquals(0.0,
                p222.distanceSquared(p222),
                DELTA,
                "ERROR: point squared distance to itself is not zero");

        // ============ Equivalence Partitions Tests ==============
        // test distanceSquared function
        assertEquals(6.0,
                p222.distanceSquared(p110),
                DELTA,
                "ERROR: squared distance between points is wrong");
        assertEquals(6.0,
                p110.distanceSquared(p222),
                DELTA,
                "ERROR: squared distance between points is wrong");
    }
}
