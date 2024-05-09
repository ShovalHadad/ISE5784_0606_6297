package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in 3D space defined by a point and a normal vector.
 */
public class Plane implements Geometry {
    protected final Point point;
    protected final Vector normal;

    /**
     * Constructs a plane with three points lying on it.
     * Calculates the normal to the plane based on the three points.
     * @param p1 First point on the plane.
     * @param p2 Second point on the plane.
     * @param p3 Third point on the plane.
     * @throws IllegalArgumentException if the points do not form a valid plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.point = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Constructs a plane given a point on the plane and a normal vector.
     * @param point A point on the plane.
     * @param normal A vector normal to the plane.
     */
    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
