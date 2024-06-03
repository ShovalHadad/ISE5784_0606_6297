package geometries;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import primitives.*;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}. 
     */
    @Test
    public void testGetNormal() {
        Tube tube = new Tube(5, new Ray(new Vector(1, 1, 1),new Point(2, 2, 2)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Wrong normal calculation (in case the point is not across the ray.p0)
        assertEquals(new Vector(Math.sqrt(1/2d),-1 * Math.sqrt(1/2d),0),
                tube.getNormal(new Point(12,2,7)),
                "getNormal() - does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: Wrong normal calculation (in case the point is across the ray.p0)
        assertEquals(new Vector(Math.sqrt(1/2d),-1 * Math.sqrt(1/2d),0),
                tube.getNormal(new Point(7, -3, 2)),
                "getNormal() - does not work correctly (Boundary test)");

    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)}. 
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(1.0, new Ray(new Vector(0, 0, 1), new Point(0, 0, 0)));

        // =============== Boundary Values Tests ==================
        // Test case 1: Ray intersects the tube
        List<Point> intersections1 = tube.findIntersections(new Ray(new Vector(1, 1, 1), new Point(0, 0, -1)));
        assertNotNull(intersections1,
                "Expected intersections with the tube");
        assertTrue(intersections1.size() > 0,
                "Expected intersections with the tube");

        // Test case 2: Ray does not intersect the tube
        List<Point> intersections2 = tube.findIntersections(new Ray(new Vector(0, 0, 1), new Point(10, 10, 10)));
        assertNull(intersections2, "Expected no intersections with the tube");
    }
}

