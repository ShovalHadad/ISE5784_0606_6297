package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTests {
    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        Ray ray1 = new Ray(new Vector(1, 0, 0), new Point(0, 0, 0));
        Ray ray2 = new Ray(new Vector(1, 0, 0), new Point(0, 0, 0));
        Ray ray3 = new Ray(new Vector(1, 0, 0), new Point(0, 0, 0));

        // =============== Boundary Values Tests ==================
        // Test Negative distance
        assertEquals(new Point(-1, 0, 0),
                ray1.getPoint(-1),
                "Negative distance");

        // Test Zero distance
        assertEquals(new Point(0, 0, 0),
                ray2.getPoint(0),
                "Zero distance");

        // Test Positive distance
        assertEquals(new Point(2, 0, 0),
                ray3.getPoint(2),
                "Positive distance");
    }
}