package lighting;

import primitives.*;

/**
 * Ambient Light Class
 * background light
 * for all object in 3D space
 */
public class AmbientLight extends Light {

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
        super(Ia.scale(Ka));
    }

    /**
     * constructor with parameters
     * calculate the intensity after the light factor
     *
     * @param Ia  - Light illumination (RGB)
     * @param Kad - Light factor in Double type
     */
    public AmbientLight(Color Ia, double Kad) {
        super(Ia.scale(Kad));
    }
}
