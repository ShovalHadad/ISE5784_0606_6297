package renderer;

import primitives.*;
import java.util.MissingResourceException;

/**
 * Camera class representing a camera in a 3D space.
 */
public class Camera implements Cloneable {

    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double vpWidth = 0;
    private double vpHeight = 0;
    private double vpDistance = 0;

    /**
     * Default private constructor to prevent direct instantiation.
     */
    private Camera() {
    }

    /**
     * Static method to get a new Builder instance.
     * @return a new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel on the view plane.
     *
     * @param nX Number of columns (width) in the view plane.
     * @param nY Number of rows (height) in the view plane.
     * @param j Column index of the pixel.
     * @param i Row index of the pixel.
     * @return The constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // To be implemented in the future
        return null;
    }

    @Override
    protected Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    /**
     * Builder class for Camera.
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Default constructor initializing a new Camera object.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Sets the location of the camera.
         *
         * @param location The location point.
         * @return The current Builder instance.
         */
        public Builder setLocation(Point location) {
            if (location == null) throw new IllegalArgumentException("Location cannot be null");
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
         * @param vTo The "forward" direction vector.
         * @param vUp The "upward" direction vector.
         * @return The current Builder instance.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) throw new IllegalArgumentException("Direction vectors cannot be null");
            if (!Util.isZero(vTo.dotProduct(vUp))) throw new IllegalArgumentException("vTo and vUp are not orthogonal");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the view plane size.
         *
         * @param width  The width of the view plane.
         * @param height The height of the view plane.
         * @return The current Builder instance.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) throw new IllegalArgumentException("Width and height must be positive");
            camera.vpWidth = width;
            camera.vpHeight = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         *
         * @param distance The distance value.
         * @return The current Builder instance.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) throw new IllegalArgumentException("Distance must be positive");
            camera.vpDistance = distance;
            return this;
        }

        /**
         * Builds and returns the Camera object.
         *
         * @return The constructed Camera object.
         */
        public Camera build() {
            if (camera.location == null) throw new MissingResourceException("Missing render data", "Camera", "location");
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) throw new MissingResourceException("Missing render data", "Camera", "direction");
            if (camera.vpWidth == 0 || camera.vpHeight == 0) throw new MissingResourceException("Missing render data", "Camera", "view plane size");
            if (camera.vpDistance == 0) throw new MissingResourceException("Missing render data", "Camera", "view plane distance");

            return camera.clone();
        }
    }
}
