package geometries;

import primitives.*;

/**
 * Tube class
 */
public class Tube extends RadialGeometry {
    protected final Ray axis;
    // constructor
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        return axis.getDirection().crossProduct(point.subtract(axis.getHead())).normalize();
    }
}
