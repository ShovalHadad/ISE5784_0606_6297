package geometries;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import geometries.Intersectable.GeoPoint;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() {
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the triangle
        List<GeoPoint> result = triangle.findGeoIntersections(new Ray( new Vector(1, 1, 2),new Point(-1, -1, -2)));

        assertEquals(1,
                result.size(),
                "Wrong number of points");
       // assertEquals(new GeoPoint(triangle, new Point(0.25d, 0.25d, 0.5d)), result.getFirst(),
        //        "Ray doesn't intersect the triangle");

        //TC02:Ray outside against vertex
        assertNull(triangle.findGeoIntersectionsHelper(new Ray( new Vector(1, 1, 2),new Point(-2, -2, -2))),
                "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(triangle.findGeoIntersectionsHelper(new Ray(new Vector(1, 1, 2),new Point(-1, -2, -2))),
                "Ray isn't outside against edge");

        //TC04:Ray inside the triangle
        assertNull(triangle.findGeoIntersections(new Ray( new Vector(0.5, 0.5, 1.8d),new Point(0.5, 0.5, 0.2))),
                "Ray  isn't inside the triangle");

        // ============ Boundary Values Tests =============
        //TC05: Ray On edge
        assertNull(triangle.findGeoIntersections(new Ray(new Vector(-2.9d,0.85d,-0.5d),new Point(0,0.5d,0.5d))),
                "Ray On edge");

        //TC06: Ray in vertex
        assertNull(triangle.findGeoIntersections(new Ray(new Vector(0.32d,-0.09d,0),new Point(1,0,0))),
                "Ray On vertex");

        //TC07: Ray On edge's continuation
        assertNull(triangle.findGeoIntersections(new Ray(new Vector(-2.31d,-1d,-1.5d),new Point(0,-0.5d,1.5d))),
                "Ray On edge's continuation");
    }
}