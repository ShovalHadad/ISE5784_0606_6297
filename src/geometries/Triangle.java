package geometries;

import primitives.*;

import java.util.List;

/**
 * Triangle class -> Represents a triangle in 3D space.
 * which is a polygon with three vertices.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with three points.
     * @param p1 First vertex of the triangle.
     * @param p2 Second vertex of the triangle.
     * @param p3 Third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
        //return List.of();
    }
}
