package geometries;//package unittests;

import static org.junit.jupiter.api.Assertions.*;
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
//        // ============ Equivalence Partitions Tests ==============
//        // Simple test for normal of a plane
//        Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
//        Vector normal = plane.getNormal(new Point(0, 0, 1));
//
//        // Ensure the normal is of unit length
//        assertEquals(1, normal.length(), "Plane getNormal() wrong normal length");
//
//        // Ensure the normal is orthogonal to the plane
//        assertTrue(normal.dotProduct(new Vector(0, 0, 1)) == 0, "Plane getNormal() is not orthogonal to the plane");
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
