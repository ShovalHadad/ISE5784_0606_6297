package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plan class -> Represents a plane in 3D space.
 */
 public class Plane extends Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * Constructs a plane with three points lying on it.
     * Calculates the normal to the plane based on the three points.
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        this.normal = v1.crossProduct(v2).normalize();
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("Two of the points are identical");
    }

    /**
     * Constructs a plane given a point on the plane and a normal vector.
     * @param point A point on the plane.
     * @param vector A vector normal to the plane.
     */
    public Plane(Point point, Vector vector) {
        this.q = point;
        this.normal = vector.normalize();
    }

    /**
     * returns the q point
     * @return Point
     */
    public Point getQ(){
        return q;
    }

    /**
     *  returns the normal vector of the span
     * @return vector
     */
    public Vector getNormal(){
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector dir = ray.getDirection();
        Point head = ray.getHead();
        Vector n = normal;
        if (q.equals(head)) {
            return null;
        }
        Vector head_q = q.subtract((head));
        //numerator
        double nhead_q = alignZero(n.dotProduct(head_q));
        if (isZero(nhead_q)) {
            return null;
        }
        //denominator
        double ndir = alignZero(n.dotProduct(dir));

        //ray is lying in the plane axis
        if (isZero(ndir)) {
            return null;
        }
        double t = alignZero(nhead_q / ndir);
        if (t <= 0) {
            return null;
        }
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector dir = ray.getDirection();
        Point head = ray.getHead();
        Vector n = normal;
        if (q.equals(head)) {
            return null;
        }
        Vector head_q = q.subtract((head));
        //numerator
        double nhead_q = alignZero(n.dotProduct(head_q));
        if (isZero(nhead_q)) {
            return null;
        }
        //denominator
        double ndir = alignZero(n.dotProduct(dir));

        //ray is lying in the plane axis
        if (isZero(ndir)) {
            return null;
        }
        double t = alignZero(nhead_q / ndir);
        if (t <= 0) {
            return null;
        }
        return List.of(ray.getPoint(t));
    }
}
