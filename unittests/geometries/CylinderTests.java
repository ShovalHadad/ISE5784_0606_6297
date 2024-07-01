package geometries;

import primitives.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * Unit tests for geometries.Cylinder class
 */
public class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        Cylinder c = new Cylinder(5, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 5);

        // ============ Equivalence Partitions Tests ==============
        // test if the point is on the side (like in tube)
        assertEquals(new Vector(0, -1, 0),
                c.getNormal(new Point(0, -5, 2)),
                "ERROR: Cylinder.getNormal() does not work correctly (point on the side)");

        // test if the point is on the top
        assertEquals(new Vector(0, 0, 1),
                c.getNormal(new Point(0, 2, 5)),
                "ERROR: Cylinder.getNormal() does not work correctly (point on top)");

        // test if the point is on the bottom
        assertEquals(new Vector(0, 0, -1),
                c.getNormal(new Point(0, 2, 0)),
                "ERROR: Cylinder.getNormal() does not work correctly (point on the bottom)");

        // =============== Boundary Values Tests ==================
        // test if the point is on the edge between the side and the top
        assertEquals(new Vector(0, 0, 1),
                c.getNormal(new Point(0, 5, 5)),
                "ERROR: Cylinder.getNormal() does not work correctly (point on the edge between the side and the top)");

        // test if the point is on the edge between the side and the bottom
        assertEquals(new Vector(0, 0, -1),
                c.getNormal(new Point(-5, 0, 0)),
                "ERROR: Cylinder.getNormal() does not work correctly (point on the edge between the side and the bottom)");
    }

    /**
     * Test method for {@link geometries.Cylinder#findGeoIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(1.0, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 5.0);

        // =============== Boundary Values Tests ==================
        // Test case 1: Ray intersects the cylinder's tube
        List<GeoPoint> intersections = cylinder.findGeoIntersections(new Ray(new Point(-2, 0, 2), new Vector(1, 0, 0)));
        assertNotNull(intersections, "Expected intersections with the tube");
        assertFalse(intersections.isEmpty(), "Expected intersections with the tube");

        // Test case 2: Ray intersects the cylinder's bottom cap
        intersections = cylinder.findGeoIntersections(new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1)));
        assertNotNull(intersections, "Expected intersections with the bottom cap");
        assertFalse(intersections.isEmpty(), "Expected intersections with the bottom cap");

        // Test case 3: Ray intersects the cylinder's top cap
        intersections = cylinder.findGeoIntersections(new Ray(new Point(0.5, 0.5, 6), new Vector(0, 0, -1)));
        assertNotNull(intersections, "Expected intersections with the top cap");
        assertFalse(intersections.isEmpty(), "Expected intersections with the top cap");

        // ============ Equivalence Partitions Tests ==============
        // Test case 4: Ray does not intersect the cylinder
        intersections = cylinder.findGeoIntersections(new Ray(new Point(2, 2, 2), new Vector(0, 1, 0)));
        assertNull(intersections, "Expected no intersections with the cylinder");
    }
}
