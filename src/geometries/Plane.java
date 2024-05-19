package geometries;

import primitives.*;
/**
 * Plan class -> Represents a plane in 3D space.
 */
 public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * returns the point q of the plane
     * @return point q
     */
    public Point getQ(){
        return q;
    }

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

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     *  returns the normal vector of the span
     * @return vector
     */
    public Vector getNormal(){
        return normal;
    }
}
