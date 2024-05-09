package geometries;

import primitives.*;
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
        return super.getNormal(point);
    }
}
