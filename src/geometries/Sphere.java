package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.alignZero;

/**
 * class Sphere
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor
     *
     * @param radius
     * @param center
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * returns the center point
     * @return Point
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point head = ray.getHead();
        Vector dir = ray.getDirection();

        if (head.equals(center)) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        Vector c_head = center.subtract(head);
        double tm = dir.dotProduct(c_head);
        double d = alignZero(Math.sqrt(c_head.lengthSquared() - tm * tm));

        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
        }
        if (t1 > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }
        if (t2 > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }
        return null;
    }
//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        Point head = ray.getHead();
//        Vector dir = ray.getDirection();
//
//        if (head.equals(center)) {
//            return List.of(ray.getPoint(radius));
//        }
//
//        Vector c_head = center.subtract(head);
//        double tm = dir.dotProduct(c_head);
//        double d = alignZero(Math.sqrt(c_head.lengthSquared() - tm * tm));
//
//        if (d >= radius) {
//            return null;
//        }
//
//        double th = alignZero(Math.sqrt(radius * radius - d * d));
//        double t1 = alignZero(tm - th);
//        double t2 = alignZero(tm + th);
//
//        if (t1 > 0 && t2 > 0) {
//            return List.of(ray.getPoint(t1), ray.getPoint(t2));
//        }
//        if (t1 > 0) {
//            return List.of(ray.getPoint(t1));
//        }
//        if (t2 > 0) {
//            return List.of(ray.getPoint(t2));
//        }
//        return null;
//    }
}

