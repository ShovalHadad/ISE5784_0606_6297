package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface for geometric objects.
 * Provides method to get the normal to the surface at a specific point.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * return a normal vector from point
     *  this function has to be implemented in the sons of Geometry
     * @param point input
     * @return vector
     */
    public abstract Vector getNormal(Point point);

    /**
     * returns the emission
     * @return Color
     */
    public Color getEmission(){
        return emission;
    }

    /**
     * set emission
     * @param emission input
     * @return this Geometry
     */
    public Geometry setEmission(Color emission){
        this.emission = emission;
        return this;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
