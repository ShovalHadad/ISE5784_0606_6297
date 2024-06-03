package geometries;
import primitives.*;
import java.util.LinkedList;
import java.util.List;

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
        this.height = height;
    }

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
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new LinkedList<>();

        // Find intersections with the tube part
        List<Point> tubeIntersections = super.findIntersections(ray);
        if (tubeIntersections != null) {
            intersections.addAll(tubeIntersections);
        }

        // Find intersections with the bottom and top caps
        Point rayHead = ray.getHead();
        Vector rayDirection = ray.getDirection();

        // Calculate the distance to the bottom cap
        double t1 = (height - rayHead.getD3()) / rayDirection.getD3();

        // Check if the intersection point with the bottom cap is within the cylinder's bounds
        if (t1 >= 0) {
            Point bottomCapIntersection = ray.getPoint(t1);
            if (bottomCapIntersection.distanceSquared(axis.getHead()) <= radius * radius) {
                intersections.add(bottomCapIntersection);
            }
        }

        // Calculate the distance to the top cap
        double t2 = (-rayHead.getD3()) / rayDirection.getD3();

        // Check if the intersection point with the top cap is within the cylinder's bounds
        if (t2 >= 0 && t2 <= height) {
            Point topCapIntersection = ray.getPoint(t2);
            if (topCapIntersection.distanceSquared(axis.getHead().add(axis.getDirection().scale(height))) <= radius * radius) {
                intersections.add(topCapIntersection);
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }

}
