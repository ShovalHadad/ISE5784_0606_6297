package geometries;

/**
 * RadialGeometry class -> helps to make rounded shapes
 */
public abstract class RadialGeometry extends Geometry {
    protected double radius;

    /**
     * constructor
     * @param radius
     */
    public RadialGeometry(double radius){
        if(radius <= 0)
            throw new IllegalArgumentException("radius can not be 0 or less");
        this.radius = radius;
    }
}
