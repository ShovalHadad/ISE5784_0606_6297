package primitives;//package unittests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for primitives.Point class
 */
public class PointTests {

    /**
     * Test method for {@link primitives.Point#add(Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // Add point and vector
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(4, 5, 6);
        Point expected = new Point(5, 7, 9);
        assertEquals(expected, p.add(v), "Point add() wrong result");
    }
    
    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // Subtract two points
        Point p1 = new Point(5, 6, 7);
        Point p2 = new Point(1, 2, 3);
        Vector expected = new Vector(4, 4, 4);
        assertEquals(expected, p1.subtract(p2), "Point subtract() wrong result");
    }
}
