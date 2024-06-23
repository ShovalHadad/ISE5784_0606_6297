package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Tube class
 */
public class Tube extends RadialGeometry {
    protected final Ray axis;

    /**
     * constructor
     * @param radius
     * @param axis
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        Point head = axis.getHead();
        Vector dir = axis.getDirection();
        Vector head_p = point.subtract(head);
        double t = alignZero(head_p.dotProduct(dir));

        if (isZero(t)) {
            return head_p.normalize();
        }

        Point axisPoint = axis.getPoint(t);
        Vector axisPoint_p = point.subtract(axisPoint);
        return axisPoint_p.normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector ray_dir = ray.getDirection();
        Vector axis_dir = axis.getDirection();

        // Calculating v_rat_dir = ray_dir - axis_dir * (ray_dir,axis_dir)
        Vector v_ray_dir = ray_dir;
        double d = ray_dir.dotProduct(axis_dir);
        if (!isZero(d)) {
            Vector axis_dir_d = axis_dir.scale(d);
            if (ray_dir.equals(axis_dir_d)) {
                return null;
            }
            v_ray_dir = ray_dir.subtract(axis_dir_d);
        }

        double d1 = 0;
        double d2 = 0;
        if (!ray.getHead().equals(axis.getHead())) {
            Vector dp = ray.getHead().subtract(axis.getHead());
            Vector tempV = dp;
            double dpv0 = dp.dotProduct(axis_dir);
            if (isZero(dpv0)) {
                d1 = v_ray_dir.dotProduct(tempV);
                d2 = tempV.lengthSquared();
            } else {
                Vector v0dpv0 = axis_dir.scale(dpv0);
                if (!dp.equals(v0dpv0)) {
                    tempV = dp.subtract(v0dpv0);
                    d1 = v_ray_dir.dotProduct(tempV);
                    d2 = tempV.lengthSquared();
                }
            }
        }

        // Getting the quadratic equation: a(v_ray_dir)^2 +b(v_ray_dir) + c = 0
        double a = v_ray_dir.lengthSquared();
        double b = 2 * d1;
        double c = alignZero(d2 - radius * radius);

        double squaredDelta = alignZero(b * b - 4 * a * c);
        if (squaredDelta <= 0) {
            return null;
        }

        double delta = Math.sqrt(squaredDelta);
        double t1 = alignZero((-b + delta) / (2 * a));
        double t2 = alignZero((-b - delta) / (2 * a));

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
//        Vector ray_dir = ray.getDirection();
//        Vector axis_dir = axis.getDirection();
//
//        // Calculating v_rat_dir = ray_dir - axis_dir * (ray_dir,axis_dir)
//        Vector v_ray_dir = ray_dir;
//        double d = ray_dir.dotProduct(axis_dir);
//        if (!isZero(d)) {
//            Vector axis_dir_d = axis_dir.scale(d);
//            if (ray_dir.equals(axis_dir_d)) {
//                return null;
//            }
//            v_ray_dir = ray_dir.subtract(axis_dir_d);
//        }
//
//        double d1 = 0;
//        double d2 = 0;
//        if (!ray.getHead().equals(axis.getHead())) {
//            Vector dp = ray.getHead().subtract(axis.getHead());
//            Vector tempV = dp;
//            double dpv0 = dp.dotProduct(axis_dir);
//            if (isZero(dpv0)) {
//                d1 = v_ray_dir.dotProduct(tempV);
//                d2 = tempV.lengthSquared();
//            } else {
//                Vector v0dpv0 = axis_dir.scale(dpv0);
//                if (!dp.equals(v0dpv0)) {
//                    tempV = dp.subtract(v0dpv0);
//                    d1 = v_ray_dir.dotProduct(tempV);
//                    d2 = tempV.lengthSquared();
//                }
//            }
//        }
//
//        // Getting the quadratic equation: a(v_ray_dir)^2 +b(v_ray_dir) + c = 0
//        double a = v_ray_dir.lengthSquared();
//        double b = 2 * d1;
//        double c = alignZero(d2 - radius * radius);
//
//        double squaredDelta = alignZero(b * b - 4 * a * c);
//        if (squaredDelta <= 0) {
//            return null;
//        }
//
//        double delta = Math.sqrt(squaredDelta);
//        double t1 = alignZero((-b + delta) / (2 * a));
//        double t2 = alignZero((-b - delta) / (2 * a));
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
//
//        return null;
//    }
}
