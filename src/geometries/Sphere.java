package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * class Sphere
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor
     * @param radius
     * @param center
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    public Point getCenter(){
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        Point head = ray.getHead();
        Vector dir = ray.getDirection();

        // If ray starts at sphere center
        if (head.equals(center)) {
            return List.of(ray.getPoint(radius));
        }

        Vector v = center.subtract(head);
        double tm = dir.dotProduct(v);
        double dSquared = v.lengthSquared() - tm * tm;
        double radiusSquared = radius * radius;

        // If the distance from the ray to the sphere's center is greater than the radius
        if (dSquared > radiusSquared) {
            return null;
        }

        double thSquared = radiusSquared - dSquared;
        double th = Math.sqrt(thSquared);

        double t1 = tm - th;
        double t2 = tm + th;

        // If both intersections are behind the ray's start point
        if (t1 <= 0 && t2 <= 0) {
            return null;
        }

        List<Point> intersections = new LinkedList<>();
        if (t1 > 0) {
            intersections.add(ray.getPoint(t1));
        }
        if (t2 > 0) {
            intersections.add(ray.getPoint(t2));
        }

        return intersections.isEmpty() ? null : intersections;
    }
}

