package lighting;

import primitives.*;

/**
 * Ambient Light Class
 * for all object in 3D space
 */
public class AmbientLight {
    private final Color intensity;

    /**
     * Field that initialized to the color black
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * constructor with parameters
     * calculate the intensity after the light factor
     *
     * @param Ia - Light illumination (RGB)
     * @param Ka - Light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * constructor with parameters
     * calculate the intensity after the light factor
     *
     * @param Ia  - Light illumination (RGB)
     * @param Kad - Light factor in Double type
     */
    public AmbientLight(Color Ia, double Kad) {
        this.intensity = Ia.scale(Kad);
    }

    /**
     * returns the intensity
     * @return Color
     */
    public Color getIntensity() {
        return intensity;
    }
}
