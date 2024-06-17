package renderer;

import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.util.List;

/**
 * SimpleRayTracer Class
 *A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * constructor with parameter
     *
     * @param scene A scene where the department is initialized
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * returns the color of an intersection point
     * @param geoPoint point of intersection
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint) {
        return scene.ambientLight.getIntensity() //ka*Ia
                .add(geoPoint.geometry.getEmission());//+Ie
                   //     calcLocalEffect(intersection, ray)); //+calculated light contribution from all light sources
        //return geoPoint.geometry.getEmission();
       // return this.scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
    }

    @Override
    public Color traceRay(Ray ray) {
        //Searched for intersections with the 3D model
        List<GeoPoint> intersections  = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return this.scene.background;
        }
        return calcColor(ray.findClosestGeoPoint(intersections));
    }
}
