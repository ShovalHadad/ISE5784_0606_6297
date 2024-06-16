package geometries;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Geometries class.
 * Class representing a collection of intersectable geometries.
 */
public class Geometries implements Intersectable {
    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * empty constructor
     */
    public Geometries() { }

    /**
     * Constructor with geometries
     * @param geometries one or more geometries to be added to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds geometries to the collection.
     * @param geometries one or more geometries to be added to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // returns the list of the geometries that intersect with the ray (input)
        List<Point> resultOfIntersections = null;
        for (Intersectable geometry : geometries) {
            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {
                if (resultOfIntersections == null) {
                    resultOfIntersections = new LinkedList<Point>();
                }
                resultOfIntersections.addAll(geometryIntersections);
            }
        }
        return resultOfIntersections;
    }
}

