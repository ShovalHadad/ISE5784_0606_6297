package lighting;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * spot light class
 * son of point light
 * like spot-light light
 */
public class SpotLight extends PointLight {
    private final Vector direction;
    private double narrowBeam;

    /**
     * constructor with parameters
     *
     * @param direction vector
     * @param intensity color
     * @param position  point
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * set the NarrowBeam
     *
     * @param beamAngle double
     * @return this
     */
    public SpotLight setNarrowBeam(double beamAngle) {
        this.narrowBeam = beamAngle;
        return this;
    }

    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        double cos = this.direction.dotProduct(getL(p));
        if (Util.isZero(cos)) {
            return Color.BLACK;
        }
        Color pointLightIntensity = (super.getIntensity(p)).scale(Math.max(0, cos));
        return pointLightIntensity;
    }

}
