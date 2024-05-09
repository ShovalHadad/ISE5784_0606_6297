package geometries;

import primitives.*;

/**
 * Interface for geometric objects.
 * Provides method to get the normal to the surface at a specific point.
 */
public interface Geometry {
    /**
     * return a normal vector from point
     *  this function has to be implemented in the sons of Geometry
     * @param point
     * @return vector
     */
    Vector getNormal(Point point);
}
