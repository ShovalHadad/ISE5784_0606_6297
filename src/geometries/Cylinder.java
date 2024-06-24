package geometries;
import primitives.*;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * constructor with parameters
     * @param radius
     * @param ray
     * @param height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        if(height <= 0)
            throw new IllegalArgumentException("height can not be 0 or less");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point o = axis.getHead();
        Vector dir = axis.getDirection();

        Vector v = point.subtract(o);
        double t = alignZero(v.dotProduct(dir));

        // if the point is on the bottom base
        if (isZero(t)) {
            return dir.scale(-1);
        }

        // if the point is on the top base
        if (isZero(t - height)) {
            return dir;
        }

        // if the point is on the side surface
        Point o2 = o.add(dir.scale(t));
        return point.subtract(o2).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<>();

        // Find intersections with the tube part
        List<GeoPoint> tubeIntersections = super.findGeoIntersectionsHelper(ray);
        if (tubeIntersections != null) {
            for (GeoPoint gp : tubeIntersections) {
                double t = axis.getDirection().dotProduct(gp.point.subtract(axis.getHead()));
                if (t > 0 && t < height) {
                    intersections.add(gp);
                }
            }
        }

        // Find intersections with the bottom and top caps
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        Point p1 = p0.add(v.scale(height));

        Plane bottomCap = new Plane(p0, v);
        Plane topCap = new Plane(p1, v.scale(-1));  // Note the direction change for the top cap

        // Check intersection with bottom cap
        List<GeoPoint> bottomIntersections = bottomCap.findGeoIntersections(ray);
        if (bottomIntersections != null) {
            for (GeoPoint geoPoint : bottomIntersections) {
                if (geoPoint.point.distanceSquared(p0) <= radius * radius) {
                    intersections.add(new GeoPoint(this, geoPoint.point));
                }
            }
        }

        // Check intersection with top cap
        List<GeoPoint> topIntersections = topCap.findGeoIntersections(ray);
        if (topIntersections != null) {
            for (GeoPoint geoPoint : topIntersections) {
                if (geoPoint.point.distanceSquared(p1) <= radius * radius) {
                    intersections.add(new GeoPoint(this, geoPoint.point));
                }
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }

    /*
    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(this.axis.getHead());
        double t = this.axis.getDirection().dotProduct(v);
        if (v.dotProduct(this.axis.getDirection()) == 0) {
            //the point is on the bottom
            return this.axis.getDirection();
        }
        Point o = this.axis.getHead().add(this.axis.getDirection().scale(t));
        Vector test = o.subtract(this.axis.getHead()).normalize();
        if ((t == this.height) && (test.equals(this.axis.getDirection()))) {
            //the point is on the top - need to check the direction to confirm that it's not on the opposite side
            return this.axis.getDirection();
        }
        return (point.subtract(o)).normalize();
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<>();

        // Find intersections with the tube part
        List<GeoPoint> tubeIntersections = super.findGeoIntersectionsHelper(ray);
        if (tubeIntersections != null) {
            intersections.addAll(tubeIntersections);
        }

        // Find intersections with the bottom and top caps
        Point rayHead = ray.getHead();
        Vector rayDirection = ray.getDirection();

        // Calculate the distance to the bottom cap
        double t1 = (height - rayHead.getZ()) / rayDirection.getZ();

        // Check if the intersection point with the bottom cap is within the cylinder's bounds
        if (t1 >= 0) {
            GeoPoint bottomCapIntersection = new GeoPoint(this, ray.getPoint(t1));
            if (bottomCapIntersection.point.distanceSquared(axis.getHead()) <= radius * radius) {
                intersections.add(bottomCapIntersection);
            }
        }

        // Calculate the distance to the top cap
        double t2 = (-rayHead.getZ()) / rayDirection.getZ();

        // Check if the intersection point with the top cap is within the cylinder's bounds
        if (t2 >= 0 && t2 <= height) {
            GeoPoint topCapIntersection = new GeoPoint(this, ray.getPoint(t2));
            if (topCapIntersection.point.distanceSquared(axis.getHead().add(axis.getDirection().scale(height))) <= radius * radius) {
                intersections.add(topCapIntersection);
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }
*/

// @Override
//    public List<Point> findIntersections(Ray ray) {
//        List<Point> intersections = new LinkedList<>();
//
//        // Find intersections with the tube part
//        List<Point> tubeIntersections = super.findIntersections(ray);
//        if (tubeIntersections != null) {
//            intersections.addAll(tubeIntersections);
//        }
//
//        // Find intersections with the bottom and top caps
//        Point rayHead = ray.getHead();
//        Vector rayDirection = ray.getDirection();
//
//        // Calculate the distance to the bottom cap
//        double t1 = (height - rayHead.getZ()) / rayDirection.getZ();
//
//        // Check if the intersection point with the bottom cap is within the cylinder's bounds
//        if (t1 >= 0) {
//            Point bottomCapIntersection = ray.getPoint(t1);
//            if (bottomCapIntersection.distanceSquared(axis.getHead()) <= radius * radius) {
//                intersections.add(bottomCapIntersection);
//            }
//        }
//
//        // Calculate the distance to the top cap
//        double t2 = (-rayHead.getZ()) / rayDirection.getZ();
//
//        // Check if the intersection point with the top cap is within the cylinder's bounds
//        if (t2 >= 0 && t2 <= height) {
//            Point topCapIntersection = ray.getPoint(t2);
//            if (topCapIntersection.distanceSquared(axis.getHead().add(axis.getDirection().scale(height))) <= radius * radius) {
//                intersections.add(topCapIntersection);
//            }
//        }
//
//        return intersections.isEmpty() ? null : intersections;
//    }

}
