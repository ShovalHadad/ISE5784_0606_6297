package primitives;

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
        Vector v = new Vector(1, 2, 3);
        Vector result = new Vector(2.5, 5, 7.5);
        double scalar = 2.5;
        double s0 = 0.0;
        // ============ Equivalence Partitions Tests ==============
        // test scale function
        assertEquals(result, v.scale(scalar), "Scale function does not return the correct result");
        // =============== Boundary Values Tests ==================
        // test if exception is thrown
        assertThrows(IllegalArgumentException.class, () -> v.scale(s0), "Scale function does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        Vector v = new Vector(0, 0, 2);
        // ============ Equivalence Partitions Tests ==============
        // Test the Length function
        assertEquals(2, v.length(), DELTA, "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // Test the LengthSquared function
        Vector v = new Vector(1, 2, 3);
        assertEquals(14, v.lengthSquared(), DELTA, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(2, 2, 2);
        Vector v2 = new Vector(1, 1, 1);
        Vector v3 = new Vector(0, -1, 1);
        // ============ Equivalence Partitions Tests ==============
        // test the DotProduct function
        assertEquals(6, v1.dotProduct(v2), DELTA, "ERROR: dotProduct() wrong value");
        // test if dotProduct function can be equal to 0.0
        assertEquals(0.0, v1.dotProduct(v3), DELTA, "ERROR: dotProduct() for orthogonal vectors is not zero");

    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 2);
        Vector normalized_v = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // test vector normalization
        assertEquals(1, normalized_v.length(), DELTA, "ERROR: the normalized vector is not a unit vector");
        // =============== Boundary Values Tests ==================
        // test if the normalized vector is opposite to the original one
        assertFalse(v.dotProduct(normalized_v) < 0 , "ERROR: the normalized vector is opposite to the original one" );
        // test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(normalized_v), "ERROR: the normalized vector is not parallel to the original one");

    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(2, 2, 2);
        Vector v2 = new Vector(1, 1, 1);
        Vector v3 = new Vector(-2, -2, -2);
        // ============ Equivalence Partitions Tests ==============
        // Test the Add function
        assertEquals(new Vector(3, 3, 3), v1.add(v2), "ERROR: Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // test if exception is thrown
        assertThrows(IllegalArgumentException.class, () -> v1.add(v3), "ERROR: Vector + -itself does not throw an exception");
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
