package lighting;

import primitives.*;

/**
 * LightSource interface
 * base for point light and spot light
 */
public interface LightSource {
    /**
     * returns the color intensity
     * @param p point
     * @return intensity
     */
    public Color getIntensity(Point p);

    /**
     * returns the direction
     * @param p point
     * @return direction vector
     */
    public Vector getL(Point p);

    /**
     * returns the Distance with point
     * @param point input
     * @return double
     */
    public double getDistance(Point point);
}
