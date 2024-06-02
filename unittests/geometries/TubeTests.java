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
        Tube tube = new Tube(1.0, new Ray(new Vector(0, 0, 1), new Point(0, 0, 0)));

        // ============ Equivalence Partitions Tests ==============
        // test point on the tube
        assertEquals(new Vector(0, 1, 0),
                tube.getNormal(new Point(1, 0, 1)),
                "getNormal() for Tube did not return the expected normal");
    }

    @Test
    public void testFindIntsersections(){

    }
}

