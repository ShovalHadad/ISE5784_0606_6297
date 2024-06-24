package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera implements Cloneable {
    // fields:
    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width = 0, height = 0, distance = 0;

    private ImageWriter imageWriter;  // added in stage 5
    private RayTracerBase rayTracer;  // added in stage 5

    /**
     * empty constructor
     */
    private Camera() { }

    /**
     * returns a new Builder
     * @return Builder
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Calculation of the pixel point in the image plane
     * @param nX number of columns
     * @param nY number of lines
     * @param j x of view plane
     * @param i y of view plane
     * @return ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //pixels width and height
        double rx = width / nX;
        double ry = height / nY;

        //point[i,j] in view-plane coordinates (center Point)
        Point pij = p0.add(vTo.scale(distance));

        //delta values for moving on the view plane
        double xj = (j - (nX - 1) / 2d) * rx;
        double yi = -(i - (nY - 1) / 2d) * ry;

        if (!isZero(xj)) {
            //add the delta distance to the center of point (i,j)
            pij = pij.add(vRight.scale(xj));
        }
        if (!isZero(yi)) {
            //add the delta distance to the center of point (i,j)
            pij = pij.add(vUp.scale(yi));
        }
        Vector Vij = pij.subtract(p0); // vector from camera's place

        return new Ray(Vij, p0);
    }

    // functions we added in stage 5:
    /**
     * this function cast Ray on every pixel in the view plane
     * @return camera
     */
    public Camera renderImage() {
        if (this.imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        if (this.rayTracer == null)
            throw new UnsupportedOperationException("Missing rayTracer");
        int x = this.imageWriter.getNx();
        int y = this.imageWriter.getNy();
        for (int i = 0; i < this.imageWriter.getNx(); i++) {
            for (int j = 0; j < this.imageWriter.getNy(); j++) {
                castRay(i, j, x, y);
            }
        }
        return this;
    }

    /**
     * this function prints a grid net on the image
     * @param interval number of height and width of the grid boxes
     * @param color for the grid
     * @return camera
     */
    public Camera printGrid(int interval, Color color) {
        //running on the view plane
        double x = this.imageWriter.getNx();
        double y = this.imageWriter.getNy();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //create the net of the grid
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;
    }

    /**
     * This function starts the method to create the image
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * this function colors the j pixels
     * @param i  resolution
     * @param j number of pixels
     */
    private void castRay(int i,int j, int x, int y) {
        Ray ray = constructRay(x, y, j, i);
        this.imageWriter.writePixel(j, i, this.rayTracer.traceRay(ray));
    }


    //Builder:
    /**
     * Builder class in camera class
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Builder empty constructor
         * acts like empty constructor for camera
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Builder constructor with parameters
         * acts like copy constructor for camera
         * @param camera camera
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        //setters:
        /**
         * set Location
         * @param location of camera
         * @return Builder
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.p0 = location;
            return this;
        }

        /**
         * set Direction
         * @param vTo vector direction
         * @param vUp vector direction
         * @return Builder
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Vectors cannot be null");
            }
            if (vTo.dotProduct(vUp) != 0) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return this;
        }

        /**
         * set VpSize => sets height and width
         * @param width double
         * @param height double
         * @return this
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be greater than 0");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * set VpDistance => distance of camera
         * @param vpDistance vector distance
         * @return this
         */
        public Builder setVpDistance(double vpDistance) {
            if (vpDistance <= 0) {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.distance = vpDistance;
            return this;
        }

        // functions we added in stage 5:
        /**
         *set ImageWriter
         * @param imageWriter to make an image
         * @return this
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null) {
                throw new IllegalArgumentException("imageWriter cannot be null");
            }
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * set RayTracer
         * @param rayTracer to know the color in a point
         * @return this
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            if (rayTracer == null) {
                throw new IllegalArgumentException("rayTracer cannot be null");
            }
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * return the camera
         * after the function checks the parameters in the camera
         * @return Camera
         */
        public Camera build() {
            if (camera.p0 == null) {
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "location");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "direction vectors");
            }
            if (camera.width == 0 || camera.height == 0) {
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "viewport size");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "viewport distance");
            }
            if(camera.imageWriter == null){
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "imageWriter");
            }
            if(camera.rayTracer == null){
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "rayTracer");
            }
            try{
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw null;
                //return null;
            }
        }
    }
}
