package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.isZero;

/**
 * Triangle class -> Represents a triangle in 3D space.
 * which is a polygon with three vertices.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with three points.
     *
     * @param p1 First vertex of the triangle.
     * @param p2 Second vertex of the triangle.
     * @param p3 Third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        //there are no intersection points
        if (intersections == null)
            return null;

        Point head = ray.getHead();//the start ray point
        Vector dir = ray.getDirection();

        Vector v1 = vertices.get(0).subtract(head);
        Vector v2 = vertices.get(1).subtract(head);
        Vector v3 = vertices.get(2).subtract(head);

        double s1 = dir.dotProduct(v1.crossProduct(v2));
        //checks the point is out of triangle
        if (isZero(s1))
            return null;

        double s2 = dir.dotProduct(v2.crossProduct(v3));
        //checks the point is out of triangle
        if (isZero(s2))
            return null;

        double s3 = dir.dotProduct(v3.crossProduct(v1));
        //checks the point is out of triangle
        if (isZero(s3)) return null;

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;
    }
}

