package geometries;

import primitives.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTests {
    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        Point p0 = new Point(0, 0, 0);
        Vector dir = new Vector(0, 0, 1);
        Ray axisRay = new Ray(dir, p0);
        double radius = 1.0;
        Tube tube = new Tube(radius, axisRay);

        // ============ Equivalence Partitions Tests ==============
        // test point on the tube
        Point point = new Point(1, 0, 1);
        Vector expectedNormal = new Vector(0, 1, 0);
        assertEquals(expectedNormal, tube.getNormal(point), "getNormal() for Tube did not return the expected normal");
    }
}

