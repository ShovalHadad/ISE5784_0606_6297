package renderer;

import primitives.*;
import scene.Scene;

/**
 * RayTracerBase Class
 * An abstract class for combining a scene and its color
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * abstract method
     * get a ray and calculates the rey's intersection point color
     * and any other object (or the background if the rey's intersection point
     * doesn't exist)
     * @param ray for the ray that intersects the point
     * @return Color for the pixel
     */
    abstract public Color traceRay(Ray ray);

    /**
     * constructor with parameter (scene)
     * @param scene A scene where the department is initialized
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
}
