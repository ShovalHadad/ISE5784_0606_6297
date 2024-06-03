package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface for geometric objects.
 * Provides method to get the normal to the surface at a specific point.
 */
public interface Geometry extends Intersectable {

    /**
     * return a normal vector from point
     *  this function has to be implemented in the sons of Geometry
     * @param point
     * @return vector
     */
    Vector getNormal(Point point);

    @Override
    List<Point> findIntersections(Ray ray);
}
