package renderer;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Ray head offset size for shading rays
     */
    private static final double DELTA = 0.1;

    /**
     * constructor with parameters
     *
     * @param scene A scene where the department is initialized
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Get the color of an intersection point
     *
     * @param point point of intersection
     * @param ray for the ray
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return this.scene.ambientLight.getIntensity()
                .add(point.geometry.getEmission())
                .add(calcLocalEffects(point, ray));
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = this.scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? this.scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * This method calculates the local effects (diffuse and specular) of lighting at a given intersection point.
     *
     * @param intersection The intersection geoPoint.
     * @param ray The ray that intersects with the geometry.
     * @return The color result of local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {

        Vector v = ray.getDirection();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));

        if (nv == 0) return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;

        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;

        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {

            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0  && unshaded(intersection,lightSource, l, n, nl)) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * checks if the point is shaded or not
     * @param gp The intersection geoPoint.
     * @param l vector
     * @param n vector
     * @return true / false
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray ray = new Ray(lightDirection, point);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return true;

        double lightDistance = light.getDistance(gp.point);

        intersections = filterIntersections(intersections, ray.getHead(), lightDistance);
        return intersections.isEmpty();
    }

    private List<GeoPoint> filterIntersections(List<GeoPoint> intersections, Point head, double distance) {
        List<GeoPoint> filtered = new LinkedList<>();
        for (GeoPoint intersection : intersections) {
            if (intersection.point.distance(head) < distance) {
                filtered.add(intersection);
            }
        }
        return filtered;
    }

    /**
     * This method calculates the diffuse component of lighting at a given point.
     *
     * @param kd The diffuse reflection coefficient.
     * @param l The direction vector from the light source to the point.
     * @param n The normal vector at the point.
     * @param lightIntensity The intensity of the light at the point.
     * @return The color result of the diffuse component.
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double lN = l.normalize().dotProduct(n.normalize());
        return lightIntensity.scale(kd.scale(Math.abs(lN)));
    }

    /**
     * This method calculates the specular component of lighting at a given point.
     *
     * @param ks The specular reflection coefficient.
     * @param l The direction vector from the light source to the point.
     * @param n The normal vector at the point.
     * @param v The direction vector of the viewer (or camera).
     * @param nShininess The shininess factor of the material.
     * @param lightIntensity The intensity of the light at the point.
     * @return The color result of the specular component.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {

        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
        double max = Math.max(0, -v.dotProduct(r));

        double maxNs = Math.pow(max, nShininess);
        Double3 ksMaxNs = ks.scale(maxNs);

        return lightIntensity.scale(ksMaxNs);
    }


}

