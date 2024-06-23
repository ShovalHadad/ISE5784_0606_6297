package lighting;

import primitives.*;

/**
 * Directional Light class
 * light of the sun
 */
public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    /**
     * constructor with parameters
     * @param intensity Il = I0
     * @param direction vector
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p){
        return super.getIntensity(); // Il = I0
    }

    @Override
    public Vector getL(Point p){
        return direction;
    }

    @Override
    public double getDistance(Point p){
        return direction.distance(p);
    }
}
