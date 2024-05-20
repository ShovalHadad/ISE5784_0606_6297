package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
public class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        Sphere sphere = new Sphere(1.0, new Point(0,0,0));
        Point p = new Point(1, 0, 0);
        Vector expect = new Vector(1, 0, 0).normalize();
        // ============ Equivalence Partitions Tests ==============
        // test point on the sphere
        assertEquals(expect, sphere.getNormal(p), "getNormal() for Sphere did not return the expected normal");
    }
}

