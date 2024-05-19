package primitives;//package primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for primitives.Vector class
 */
public class VectorTests {
    private static final double DELTA = 0.000001;

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the scale function returns the correct result
        Vector v = new Vector(1, 2, 3);
        double scalar = 2.5;
        Vector result = v.scale(scalar);
        assertEquals(new Vector(2.5, 5, 7.5), result, "Scale function does not return the correct result");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the Length function returns the correct Length
        Vector v = new Vector(1, 2, 2);
        assertEquals(Math.sqrt(9), v.length(), DELTA, "Length function does not return the correct result");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the LengthSquared function returns the correct result
        Vector v = new Vector(1, 2, 3);
        assertEquals(14, v.lengthSquared(), DELTA, "LengthSquared function does not return the correct result");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the DotProduct function returns the correct result
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        assertEquals(32, v1.dotProduct(v2), DELTA, "DotProduct function does not return the correct result");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the Normalize function returns the correct result
        Vector v = new Vector(1, 2, 2);
        assertEquals(1, v.normalize().length(), DELTA, "Normalize function does not return a unit vector");
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the Add function returns the correct result
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector result = v1.add(v2);
        assertEquals(new Vector(5, 7, 9), result, "Add function does not return the correct result");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v123 = new Vector(1, 2, 3);
        Vector v03M2 = new Vector(0, 3, -2);
        Vector vM2M4M6 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v123.crossProduct(v03M2);
        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v123.length() * v03M2.length(), vr.length(), DELTA, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v123), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v03M2), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(vM2M4M6), "crossProduct() for parallel vectors does not throw an exception");
    }
}
