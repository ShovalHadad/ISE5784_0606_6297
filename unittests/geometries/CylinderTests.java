package geometries;

import primitives.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(c.getNormal(new Point(0,-5,2)),new Vector(0,-1,0),"ERROR: Cylinder.getNormal() does not work correctly(point on the side)");
        // test if the point is on the top
        assertEquals(new Vector(0,0,1),c.getNormal(new Point(0,2,5)),"ERROR: Cylinder.getNormal() does not work correctly(point on top)");
        // test point is on the bottom
        assertEquals(c.getNormal(new Point(0,2,0)),new Vector(0,0,1),"ERROR: Cylinder.getNormal() does not work correctly(point is on the bottom)");

        // =============== Boundary Values Tests ==================
        // test if the point is on the edge between the side and the top
        assertEquals(c.getNormal(new Point(0,5,5)),new Vector(0,0,1), "ERROR: Cylinder.getNormal() does not work correctly");
        // test point is on the edge between the side and the bottom
        assertEquals(c.getNormal(new Point(-5,0,0)),new Vector(0,0,1), "ERROR: Cylinder.getNormal() does not work correctly");
    }
}

