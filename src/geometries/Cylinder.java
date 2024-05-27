package geometries;

import primitives.*;

import java.util.List;

/**
 * Cylinder class
 */
public class Cylinder extends Tube {
    private final double height;
    // constructor
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v=point.subtract(this.axis.getHead());
        double t=this.axis.getDirection().dotProduct(v);
        if(v.dotProduct(this.axis.getDirection())==0)//the point is on the bottom
        {
            return this.axis.getDirection();
        }
        Point o=this.axis.getHead().add(this.axis.getDirection().scale(t));
        Vector test=o.subtract(this.axis.getHead()).normalize();
        if((t == this.height) && (test.equals(this.axis.getDirection())))//the point is on the top - need to check the direction to confirm that its not on the opposite side
        {
            return this.axis.getDirection();
        }
        Vector normal=(point.subtract(o)).normalize();
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
        //return List.of();
    }
}
