package geometries;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import geometries.Intersectable.GeoPoint;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane# Plane.getNormal(Point).}
     */
    @Test
    public void testGetNormal() {
        Plane plane = new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );
        double n = Math.sqrt(1d / 3);
        assertEquals(new Vector(n, n, n),
                plane.getNormal(new Point(0, 0, 1)),
                "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane# Plane.Plane(Point, Point,Point).}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        assertDoesNotThrow(() -> new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                ),
                "Failed constructing a correct plane");

        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        assertThrows(IllegalArgumentException.class,
                () -> new Plane
                        (
                                new Point(1, 0, 0),
                                new Point(1, 0, 0),
                                new Point(0, 0, 1)
                        ),
                "Constructed a plane while a point equal to b point");

        //TC03: Test when a point equal to c point.
        assertThrows(IllegalArgumentException.class,
                () -> new Plane
                        (
                                new Point(1, 0, 0),
                                new Point(0, 0, 1),
                                new Point(1, 0, 0)
                        ),
                "Constructed a plane while a point equal to c point");

        //TC04: Test when b point equal to c point.
        assertThrows(IllegalArgumentException.class,
                () -> new Plane
                        (
                                new Point(1, 0, 0),
                                new Point(0, 0, 1),
                                new Point(0, 0, 1)
                        ),
                "Constructed a plane while b point equal to c point");

        //TC05: Test when all 3 points are on the same line.
        assertThrows(IllegalArgumentException.class,
                () -> new Plane
                        (
                                new Point(1, 2, 3),
                                new Point(2, 3, 4),
                                new Point(3, 4, 5)
                        ),
                "Constructed a plane while all 3 point on the same plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() {
        Plane plane = new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 points)
        List<GeoPoint> result = plane.findGeoIntersections(new Ray(new Point(0,1,1), new Vector(0, 0, -1)));
        assertEquals(result.size(),
                1,
                "Wrong number of points");
        assertEquals(new GeoPoint(plane,new Point(0, 1, 0)),
                result.getFirst(),
                "Ray intersects the plane");

        // TC02: Ray doesn't intersect the plane (0 points)
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 1, 1), new Vector(0, 0, 1))),
                "Ray doesn't intersect the plane");

        // =============== Boundary Values Tests ==================
        //**** Group: Ray is parallel to the plane
        //TC03: Ray is included in the plane
        assertNull(plane.findGeoIntersections(new Ray(new Point(0, 0, 1), new Vector(1, -1, 0))),
                "Ray is included in the plane. Ray is parallel to the plane");

        //TC04: Ray isn't included in the plane
        assertNull(plane.findGeoIntersections(new Ray(new Point(0, 0, 2), new Vector(1, -1, 0))),
                "Ray isn't included in the plane. Ray is parallel to the plane");

        //**** Group: Special case
        //TC05: Ray begins at the plane (p0 is in the plane, but not the ray)
        assertNull(plane.findGeoIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, -1))),
                "Ray begins at the plane (p0 is in the plane, but not the ray)");

        //TC06: Ray begins in the plane's reference point
        assertNull(plane.findGeoIntersections(new Ray(plane.getQ(), new Vector(1, 0, 0))),
                "Ray begins in the plane's reference point");
    }
}