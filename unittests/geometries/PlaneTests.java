package geometries;//package unittests;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane p = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
        Vector result = p.getNormal(new Point(0, 0, 1));
        // test that the length is 1
        assertEquals(1, result.length(), 0.00001, "ERROR: the length of the normal is not 1");
        // check the normal in orthogonal to the plane
        assertTrue(isZero(result.dotProduct(new Vector(0, -1, 1))));
        assertTrue(isZero(result.dotProduct(new Vector(-1, 1, 0))));
    }

    /**
     * Test method for {@link geometries.Plane()}.
     */
    @Test
    public void testConstructor() {
        // ============ Boundary Values Tests ==================
        // Test constructor with two identical points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(0, 0, 0)), "Plane constructor does not throw an exception for identical points");

        // Test constructor with collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)), "Plane constructor does not throw an exception for collinear points");
    }
}
