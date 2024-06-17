package geometries;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {
    Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

    /**
     * Test method for {@link geometries.Sphere# Sphere.Sphere(radious,Point).}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(5, new Point(1, 2, 3));
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct sphere");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when the radius is 0.
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(0, new Point(1, 2, 3)),
                "Constructed a sphere while the radius is 0");

        // TC03: Test when the radius is negative,-1.
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(-1, new Point(1, 2, 3)),
                "Constructed a sphere while the radius is negative");

    }

    /**
     * Test method for {@link geometries.Sphere# Sphere.getNormal(Point).}
     */
    @Test
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp = new Sphere(1.0, new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 2)));
    }
    
    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    public void findIntsersectionsTest() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(1, 1, 0), new Point(-1, 0, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<GeoPoint> sphereIntersections = sphere.findGeoIntersections(new Ray(new Vector(3, 1, 0), new Point(-1, 0, 0)));

        assertEquals(2, sphereIntersections.size(),
                "Wrong number of points");

        if (sphereIntersections.get(0).point.getX() > sphereIntersections.get(1).point.getX()) {
            sphereIntersections = List.of(sphereIntersections.get(1), sphereIntersections.get(0));
        }
        assertEquals(List.of(new GeoPoint(sphere, p1), new GeoPoint(sphere, p2)),
                sphereIntersections,
                "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(new GeoPoint(sphere, p2)),
                List.of(sphere.findGeoIntersections(new Ray(new Vector(3, 1, 0), new Point(0.5, 0.5, 0))).get(0)),
                "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(3, 1, 0), new Point(2, 1, 0))),
                "Sphere behind Ray");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(2, 0, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Vector(1, 1, 0), new Point(1, -1, 0))).getFirst()),
                "Ray from sphere inside");

        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(1, 1, 0), new Point(2, 0, 0))),
                "Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        assertEquals(2,
                sphereIntersections.size(),
                "Wrong number of points");
        sphereIntersections = sphere.findGeoIntersections(new Ray(new Vector(0, 1, 0), new Point(1, -2, 0)));

        if (sphereIntersections.get(0).point.getY() > sphereIntersections.get(1).point.getY()) {
            sphereIntersections = List.of(sphereIntersections.get(1), sphereIntersections.get(0));
        }

        assertEquals(List.of(new GeoPoint(sphere, new Point(1, -1, 0)),
                new GeoPoint(sphere, new Point(1, 1, 0))),
                sphereIntersections,
                "Line through O, ray crosses sphere");

        // TC08: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new GeoPoint(sphere,new Point(1, 1, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Vector(0, 1, 0), new Point(1, -1, 0))).get(0)),
                "Line through O, ray from and crosses sphere");

        // TC09: Ray starts inside (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(1, 1, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 0.5, 0))).get(0)),
                "Line through O, ray from inside sphere");

        // TC10: Ray starts at the center (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(1, 1, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 0, 0))).get(0)),
                "Line through O, ray from O");

        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 1, 0))),
                "Line through O, ray from sphere outside");

        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 2, 0))),
                "Line through O, ray outside sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(1, 0, 0), new Point(0, 1, 0))),
                "Tangent line, ray before sphere");

        // TC14: Ray starts at the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(1, 0, 0), new Point(1, 1, 0))),
                "Tangent line, ray at sphere");

        // TC14: Ray starts after the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(1, 0, 0), new Point(2, 1, 0))),
                "Tangent line, ray after sphere");

        // **** Group: Special cases
        // TC15: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findGeoIntersections(new Ray(new Vector(0, 0, 1), new Point(-1, 0, 0))),
                "Ray orthogonal to ray head -> O line");

    }
}