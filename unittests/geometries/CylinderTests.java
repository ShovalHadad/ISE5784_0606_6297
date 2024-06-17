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
        Cylinder c = new Cylinder(5, new Ray( new Vector(0,0,1),new Point(0,0,0 )),5);

        // ============ Equivalence Partitions Tests ==============
        // test if the point is on the side (like in tube)
        assertEquals(c.getNormal(new Point(0,-5,2)),
                new Vector(0,-1,0),
                "ERROR: Cylinder.getNormal() does not work correctly(point on the side)");

        // test if the point is on the top
        assertEquals(new Vector(0,0,1),
                c.getNormal(new Point(0,2,5)),
                "ERROR: Cylinder.getNormal() does not work correctly(point on top)");

        // test point is on the bottom
        assertEquals(c.getNormal(new Point(0,2,0)),
                new Vector(0,0,1),
                "ERROR: Cylinder.getNormal() does not work correctly(point is on the bottom)");

        // =============== Boundary Values Tests ==================
        // test if the point is on the edge between the side and the top
        assertEquals(c.getNormal(new Point(0,5,5)),
                new Vector(0,0,1),
                "ERROR: Cylinder.getNormal() does not work correctly");

        // test point is on the edge between the side and the bottom
        assertEquals(c.getNormal(new Point(-5,0,0)),
                new Vector(0,0,1),
                "ERROR: Cylinder.getNormal() does not work correctly");
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(1.0, new Ray(new Vector(0, 0, 1), new Point(0, 0, 0)), 5.0);

        // =============== Boundary Values Tests ==================
        // Test case 1: Ray intersects the cylinder's tube
        List<GeoPoint> intersections1 = cylinder.findGeoIntersections(new Ray(new Vector(1, 1, 1), new Point(0, 0, -1)));
        assertNotNull(intersections1,
                "Expected intersections with the tube");
        assertFalse(intersections1.isEmpty(), "Expected intersections with the tube");

        // Test case 2: Ray intersects the cylinder's bottom cap
        List<GeoPoint> intersections2 = cylinder.findGeoIntersections(new Ray(new Vector(1, 1, -1), new Point(0, 0, -5)));
        assertNotNull(intersections2,
                "Expected intersections with the bottom cap");
        assertFalse(intersections2.isEmpty(),
                "Expected intersections with the bottom cap");

        // Test case 3: Ray intersects the cylinder's top cap
        List<GeoPoint> intersections3 = cylinder.findGeoIntersections(new Ray(new Vector(-1, -1, 1), new Point(0, 0, 5)));
        assertNotNull(intersections3,
                "Expected intersections with the top cap");
        assertFalse(intersections3.isEmpty(),
                "Expected intersections with the top cap");

        // ============ Equivalence Partitions Tests ==============
        // Test case 4: Ray does not intersect the cylinder
        List<GeoPoint> intersections4 = cylinder.findGeoIntersections(new Ray(new Vector(0, 0, 1), new Point(10, 10, 10)));
        assertNull(intersections4,
                "Expected no intersections with the cylinder");
    }
}

