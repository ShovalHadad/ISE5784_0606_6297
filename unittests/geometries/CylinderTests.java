package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;


/**
 * Unit tests for geometries.Cylinder class
 */
public class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Simple test for normal of an infinite cylinder
//        Vector direction = new Vector(0, 1, 0); // יצירת Vector מנקודת התחלה של הקרן
//        Ray ray = new Ray(direction, new Point(0, 0, 0)); // יצירת קרן
//        Cylinder cylinder = new Cylinder(1, ray, 1);
//        Vector normal = cylinder.getNormal(new Point(1, 0, 0));
//
//        // Ensure the normal is of unit length
//        assertEquals(1, normal.length(), "Cylinder getNormal() wrong normal length");
//
//        // Ensure the normal is correct
//        assertEquals(new Vector(1, 0, 0), normal, "Cylinder getNormal() wrong normal direction");
    }
}
