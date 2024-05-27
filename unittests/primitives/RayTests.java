package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {
    @Test
    void testGetPoint() {
        // TC1: Negative distance
        Ray ray1 = new Ray(new Vector(1, 0, 0), new Point(0, 0, 0));
        assertEquals(new Point(-1, 0, 0), ray1.getPoint(-1), "Negative distance");

        // TC2: Zero distance
        Ray ray2 = new Ray(new Vector(1, 0, 0), new Point(0, 0, 0));
        assertEquals(new Point(0, 0, 0), ray2.getPoint(0), "Zero distance");

        // TC3: Positive distance
        Ray ray3 = new Ray(new Vector(1, 0, 0), new Point(0, 0, 0));
        assertEquals(new Point(2, 0, 0), ray3.getPoint(2), "Positive distance");
    }
}