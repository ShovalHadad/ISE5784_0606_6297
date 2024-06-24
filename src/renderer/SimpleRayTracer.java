package renderer;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * stop conditions
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * constructor with parameters
     *
     * @param scene A scene where the department is initialized
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Calculates the transparency factor for a point with respect to a light source.
     *
     * @param gp The point for which transparency is calculated.
     * @param light    The light source.
     * @param l        The vector from the light source to the point.
     * @param n        The normal vector at the point.
     * @return The transparency factor as a Double3 representing (r, g, b) values.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(lightDirection, gp.point, n); //build ray with delta
        double lightDistance = light.getDistance(gp.point);

        var intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return Double3.ONE; //no intersections
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geoPoint : intersections) {
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0) {
                ktr = ktr.product(geoPoint.geometry.getMaterial().kT); //the more transparency the less shadow
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }
        return ktr;
    }


    /**
     * Get the color of an intersection point
     *
     * @param point of intersection
     * @param ray for the ray
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)
                .add(this.scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given intersection point.
     *
     * @param geoPoint The intersection point.
     * @param ray      The ray that intersected with the geometry.
     * @param level    The recursion level.
     * @param k        The attenuation factor.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = geoPoint.geometry.getEmission()
                .add(calcLocalEffects(geoPoint, ray, k));

        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculates the global effects (reflection and refraction) at a given intersection point.
     *
     * @param gp    The intersection point.
     * @param ray   The ray that intersected with the geometry.
     * @param level The recursion level.
     * @param k     The attenuation factor.
     * @return The color contribution from global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();

        color = color.add(calcRayEffect(gp, ray, level, k, material.kR, true));
        color = color.add(calcRayEffect(gp, ray, level, k, material.kT, false));

        return color;
    }

    /**
     * Calculates the color effect for a given ray (either reflection or refraction) at a given geo-point.
     *
     * @param gp the geo-point where the effect is calculated
     * @param ray the original ray that intersects with the geo-point
     * @param level the recursion depth level
     * @param k the attenuation coefficient from previous recursions
     * @param kEffect the reflection (kR) or refraction (kT) coefficient of the material at the geo-point
     * @param isReflection true if calculating reflection effect, false if calculating refraction effect
     * @return the color effect due to reflection or refraction
     */
    private Color calcRayEffect(GeoPoint gp, Ray ray, int level, Double3 k, Double3 kEffect, boolean isReflection) {
        if (kEffect.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        Double3 kkEffect = k.product(kEffect);
        if (kkEffect.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        Ray effectRay = isReflection ? constructReflected(gp, ray) : constructRefracted(gp, ray);
        GeoPoint effectPoint = findClosestIntersection(effectRay);
        if (effectPoint == null)
            return this.scene.background;

        return calcColor(effectPoint, effectRay, level - 1, kkEffect).scale(kEffect);
    }

    /**
     * Constructs a reflected ray at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The incident ray.
     * @return The reflected ray.
     */
    private Ray constructReflected(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(v.dotProduct(n));
        // r = v - 2 * (v * n) * n
        Vector r = v.subtract(n.scale(2d * nv)).normalize();
        return new Ray(r, gp.point, n); //use the constructor with the normal for moving the head
    }

    /**
     * Constructs a refracted ray at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The incident ray.
     * @return The refracted ray.
     */
    private Ray constructRefracted(GeoPoint gp, Ray ray) {
        return new Ray(ray.getDirection(), gp.point, gp.geometry.getNormal(gp.point));
    }


    @Override
    public Color traceRay(Ray ray) {
        var point = findClosestIntersection(ray);
        if (point == null) {
            return scene.background;
        }
        return calcColor(point, ray);
    }

    /**
     * This method calculates the local effects (diffuse and specular) of lighting at a given intersection point.
     *
     * @param gp The intersection geoPoint.
     * @param ray The ray that intersects with the geometry.
     * @return The color result of local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n); //intensity of shadow
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, v)));
                }
            }
        }
        return color;
    }
   /* private Color calcLocalEffects(GeoPoint intersection, Ray ray) {

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

    */

    /**
     * checks if the point is shaded or not
     * @param geoPoint The intersection geoPoint.
     * @param l vector
     * @param n vector
     * @return true / false
     */
    private boolean unshaded(GeoPoint geoPoint, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(lightDirection, geoPoint.point, n);
        double lightDistance = light.getDistance(geoPoint.point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                if (gp.geometry.getMaterial().kT.equals(new Double3(0))) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * function that returns the closest geoPoint in the Intersections
     * @param ray ray
     * @return geoPoint of geoPoints that closer to head then distance
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

    /**
     * calculates the diffuse component of lighting at a given point.
     * @param material material
     * @param nl double
     * @return The diffusion
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * calculates the specular component of lighting at a given point.
     *
     * @param material The material.
     * @param l The direction vector from the light source to the point.
     * @param n The normal vector at the point.
     * @param v The direction vector of the viewer (or camera).
     * @return The specular
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, Vector v) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
        double minusVR = -v.dotProduct(r);
        return alignZero(minusVR) <= 0 ? Double3.ZERO
                : material.kS.scale(Math.pow(minusVR, material.nShininess));
    }

}

