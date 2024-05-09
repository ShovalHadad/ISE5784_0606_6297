package geometries;

/**
 * RadialGeometry class -> helps to make rounded shapes
 */
public abstract class RadialGeometry implements Geometry {
    protected double radius;
    // constructor
    public RadialGeometry(double radius){
        this.radius = radius;
    }
}
