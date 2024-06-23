package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface for geometric objects.
 * Provides method to get the normal to the surface at a specific point.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

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
     * returns the material
     * @return material
     */
    public Material getMaterial(){
        return material;
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

    /**
     * set material
     * @param material input
     * @return this
     */
    public Geometry setMaterial(Material material){
        this.material = material;
        return this;
    }


//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        return super.findIntersections(ray);
//    }
}
