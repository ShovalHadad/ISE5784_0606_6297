//package geometries;
//
//import org.junit.jupiter.api.Test;
//
//import primitives.*;
//
//import java.util.Comparator;
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Unit tests for geometries.Sphere class
// */
//public class SphereTests {
//
//    /**
//     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
//     */
//    @Test
//    public void testGetNormal() {
//        Sphere sphere = new Sphere(1.0, new Point(0,0,0));
//        Point p = new Point(1, 0, 0);
//        Vector expect = new Vector(1, 0, 0).normalize();
//        // ============ Equivalence Partitions Tests ==============
//        // test point on the sphere
//        assertEquals(expect, sphere.getNormal(p), "getNormal() for Sphere did not return the expected normal");
//    }
//
//
//
//
//    private final Point p001 = new Point(0, 0, 1);
//    private final Point p100 = new Point(1, 0, 0);
//    private final Vector v001 = new Vector(0, 0, 1);
//
//    /**
//     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
//     */
//    @Test
//    public void testFindIntersections() {
//        Sphere sphere = new Sphere(1d, p100);
//        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
//        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
//        final var exp = List.of(gp1, gp2);
//        final Vector v310 = new Vector(3, 1, 0);
//        final Vector v110 = new Vector(1, 1, 0);
//        final Point p01 = new Point(-1, 0, 0);
//
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Ray's line is outside the sphere (0 points)
//        assertNull(sphere.findIntersections(new Ray(v110, p01)), "Ray's line out of sphere");
//
//        // TC02: Ray starts before and crosses the sphere (2 points)
//        Point p1 = sphere.getCenter();
//        final List<Point> result02 = sphere.findIntersections(new Ray(v310, p01));
//        assertEquals(2, result02.size(), "Wrong number of points");
//        assertEquals(exp, result02, "Ray crosses sphere");
//
//        // TC03: Ray starts inside the sphere (1 point)
//        final Point p200 = new Point(2, 0, 0);
//        final Vector v01 = new Vector(-1, 0, 0);
//        List<Point> result03 = sphere.findIntersections(new Ray(v01, p200));
//        assertEquals(1, result03.size(), "Ray starts inside sphere");
//        assertNull(sphere.findIntersections(new Ray(v001, p001)), "Ray starts inside sphere");
//
//        // TC04: Ray starts after the sphere (0 points)
//        final Vector v100 = new Vector(1, 0, 0);
//        assertNull(sphere.findIntersections(new Ray(v100, new Point(3, 0, 0))), "Ray starts after sphere");
//
//        // =============== Boundary Values Tests ==================
//        // **** Group: Ray's line crosses the sphere (but not the center)
//        // TC11: Ray starts at sphere and goes inside (1 point).
//        List<Point> result11 = sphere.findIntersections(new Ray(v01, p200));
//        assertEquals(1, result11.size(), "Ray starts at sphere and goes inside");
//        if (!result11.isEmpty()) {
//            assertTrue(result11.get(0).equals(p200.add(v01)), "Wrong intersection point");
//        }
//
//
//        // TC12: Ray starts at sphere and goes outside (0 points)
//        assertNull(sphere.findIntersections(new Ray(v100, p200)), "Ray starts at sphere and goes outside");
//
//        // **** Group: Ray's line goes through the center
//        // TC13: Ray starts before the sphere (2 points)
//        Point p0 = new Point(0, 0, 0);
//        List<Point> result13 = sphere.findIntersections(new Ray(v100, p0));
//        assertEquals(2, result13.size(), "Ray starts before sphere");
//        assertEquals(p0.add(v100), result13.get(0), "Ray starts before sphere");
//        assertEquals(p200.add(v01), result13.get(1), "Ray starts before sphere");
//
//        // TC14: Ray starts at sphere and goes inside (1 points).
//        List<Point> result14 = sphere.findIntersections(new Ray(v01, p200));
//        assertEquals(1, result14.size(), "Ray starts at sphere and goes inside");
//        assertEquals(p200.add(v01), result14.get(0), "Ray starts at sphere and goes inside");
//
//        // TC15: Ray starts inside (1 points).
//        Point p = new Point(1.5, 0, 0);
//        List<Point> result15 = sphere.findIntersections(new Ray(v01, p));
//        assertEquals(1, result15.size(), "Ray starts inside");
//        assertEquals(p.add(v01), result15.get(0), "Ray starts inside");
//
//        // TC16: Ray starts at the center (1 points).
//        List<Point> result16 = sphere.findIntersections(new Ray(v100, p100));
//        assertEquals(1, result16.size(), "Ray starts at the center");
//        assertEquals(p.add(v100), result16.get(0), "Ray starts at the center");
//
//        // TC17: Ray starts at sphere and goes outside (0 points)
//        assertNull(sphere.findIntersections(new Ray(v100, p100)), "Ray starts at sphere and goes outside");
//
//        // TC18: Ray starts after sphere (0 points)
//        assertNull(sphere.findIntersections(new Ray(v100,p200)),"Ray starts after sphere");
//
//        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
//        // TC19: Ray starts before the tangent point
//        List<Point> result19 = sphere.findIntersections(new Ray(v100,p0));
//        assertNull(result19, "Ray starts before the tangent point");
//
//        // TC20: Ray starts at the tangent point
//        List<Point> result20 = sphere.findIntersections(new Ray(v100,p0));
//        assertNull(result20, "Ray starts at the tangent point");
//
//        // TC21: Ray starts after the tangent point
//        Point p02 = new Point(-2, 0, 0);
//        List<Point> result21 = sphere.findIntersections(new Ray(v100,p02));
//        assertEquals(2, result21.size(), "Ray starts after the tangent point");
//        assertEquals(p.add(v100), result21.get(0), "Ray starts after the tangent point");
//        assertEquals(p.add(new Vector(3, 0, 0)), result21.get(1), "Ray starts after the tangent point");
//
//        // **** Group: Special cases
//        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
//        List<Point> result22 = sphere.findIntersections(new Ray(new Vector(0, -1, 0), new Point(-1, 1, 0)));
//        assertNull(result22, "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
//    }
//}
//
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author michal slutzkin & sheina korem
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
        try {
            new Sphere(0, new Point(1, 2, 3));
            fail("Constructed a sphere while the radius is 0");
        } catch (IllegalArgumentException ignored) {
        }
        // TC03: Test when the radius is negative,-1.
        try {
            new Sphere(-1, new Point(1, 2, 3));
            fail("Constructed a sphere while the radius is negative");
        } catch (IllegalArgumentException ignored) {
        }
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
    @Test
    public void findIntsersectionsTest() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> exp = List.of(p1, p2);

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point(-1, 0, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Point> result = sphere.findIntersections(new Ray(new Vector(3, 1, 0), new Point(-1, 0, 0)));

        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getD1() > result.get(1).getD1()) {
            result = List.of(result.get(1), result.get(0)); }
        assertEquals(exp, result,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals( List.of(p2),
                List.of( sphere.findIntersections(new Ray(new Vector(3, 1, 0), new Point(0.5, 0.5, 0))).get(0)),
                "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Vector(3, 1, 0), new Point(2, 1, 0))),
                "Sphere behind Ray");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        assertEquals( List.of(new Point(2, 0, 0)),
                List.of(sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point(1, -1, 0))).get(0)),
                "Ray from sphere inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point(2, 0, 0))),
                "Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        assertEquals( 2, result.size(),"Wrong number of points");
        result = sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, -2, 0)));

        if (result.get(0).getD2() > result.get(1).getD2()) {
            result = List.of(result.get(1), result.get(0)); }

        Point p3 = new Point(1, -1, 0);
        Point p4 = new Point(1, 1, 0);
        assertEquals(List.of(p3,p4), result,
                "Line through O, ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        assertEquals( List.of(new Point(1, 1, 0)),
                List.of(   sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, -1, 0))).get(0)),
                "Line through O, ray from and crosses sphere");

        // TC15: Ray starts inside (1 point)
        assertEquals( List.of(new Point(1, 1, 0)),
                List.of( sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 0.5, 0))).get(0)),
                "Line through O, ray from inside sphere");

        // TC16: Ray starts at the center (1 point)
        assertEquals( List.of(new Point(1, 1, 0)),
                List.of( sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 0, 0))).get(0)),
                "Line through O, ray from O");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 1, 0))),
                "Line through O, ray from sphere outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 2, 0))),
                "Line through O, ray outside sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        List<Point> result19 = sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(0, 1, 0)));
        assertEquals(1, result19.size(), "Tangent line, ray before sphere");
        assertEquals(new Point(1, 1, 0), result19.get(0), "Tangent line, ray before sphere");




        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray( new Vector(1, 0, 0), new Point(1, 1, 0))),
                "Tangent line, ray at sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray( new Vector(1, 0, 0), new Point(2, 1, 0))),
                "Tangent line, ray after sphere");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull(sphere.findIntersections(new Ray( new Vector(0, 0, 1), new Point(-1, 0, 0))),
                "Ray orthogonal to ray head -> O line");

    }
}