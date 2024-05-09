package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects.
 * Provides method to get the normal to the surface at a specific point.
 */
public interface Geometry {
    Vector getNormal(Point point);
}
