package lighting;

import primitives.*;

/**
 * Point Light class
 * like a lamp light
 */
public class PointLight extends Light implements LightSource {
    protected Point position;
    private double kC = 1, kL = 0, kQ = 0;  //attenuation coefficients

    /**
     * set the kc
     * @param kC double
     * @return this
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;

    }

    /**
     * set the kl
     * @param kL double
     * @return this
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    /**
     * set the kq
     * @param kQ double
     * @return this
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * constructor with parameters
     * @param intensity intensity
     * @param position vector
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1/(kC+kL*d+kQ*d*d));
    }

    @Override
    public Vector getL(Point p) {
        // if the point is the same as the light source, return null
        if (p.equals(position))
            return null;
        // otherwise, return the normalized vector from the light source to the point
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point p){
        return position.distance(p);
    }
}
