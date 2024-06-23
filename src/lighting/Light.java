package lighting;

import primitives.Color;

/**
 * Light class
 */
 abstract class Light {
     protected Color intensity;

    /**
     * constructor with parameters
     * @param intensity of the color by light
     */
     protected Light(Color intensity){
         this.intensity = intensity;
     }

    /**
     * returns the intensity = original light
     * @return color
     */
     public Color getIntensity() {
         return intensity;
     }
 }
