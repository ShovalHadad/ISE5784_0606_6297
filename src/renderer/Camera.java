package renderer;

import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera implements Cloneable {
    // fields:
    private  Point p0;
    private  Vector vTo, vUp, vRight;
    private  double width = 0, height = 0, distance = 0;

    private  ImageWriter imageWriter;  // added in stage 5
    private  RayTracerBase rayTracer;  // added in stage 5

    private int numSamples = 1; // for anti aliasing

    private int maxSamples = 1; // maximum samples for adaptive super-sampling
    private boolean adaptiveSuperSamplingEnabled = true; // flag to enable/disable adaptive super-sampling

    // ExecutorService for parallel processing
    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    // camera functions:
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

    // change for AntiAliasing
    /**
     * Calculation of the pixel point in the image plane
     *
     * @param nX number of columns
     * @param nY number of lines
     * @param i  y of view plane
     * @param j  x of view plane
     * @return ray
     */
    public Ray constructRay(int nX, int nY, double i, double j) {
        double rx = width / nX;
        double ry = height / nY;

        Point pij = p0.add(vTo.scale(distance));

        double xj = (j - (nX - 1) / 2d) * rx;
        double yi = -(i - (nY - 1) / 2d) * ry;
        if (!isZero(xj)) {
            pij = pij.add(vRight.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.add(vUp.scale(yi));
        }
        Vector Vij = pij.subtract(p0).normalize();
        return new Ray(p0, Vij);
    }

    // functions we added in stage 5:
    /**
     * this function cast Ray on every pixel in the view plane
     * @return camera
     */
    public Camera renderImage() {
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        int cores = Runtime.getRuntime().availableProcessors();
        int chunkSize = (nY + cores - 1) / cores;

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < nY; i += chunkSize) {
            int startRow = i;
            int endRow = Math.min(i + chunkSize, nY);
            futures.add(executor.submit(() -> {
                for (int row = startRow; row < endRow; row++) {
                    for (int col = 0; col < nX; col++) {
                        castRay(nX, nY, row, col);
                    }
                }
            }));
        }
        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
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
        double nX = this.imageWriter.getNx();
        double nY = this.imageWriter.getNy();
        for (int xIndex = 0; xIndex < nX; xIndex++) {
            for (int yIndex = 0; yIndex < nY; yIndex++) {
                //create the net of the grid
                if (xIndex % interval == 0 || yIndex % interval == 0) {
                    imageWriter.writePixel(xIndex, yIndex, color);
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

    //change for AntiAliasing
    /**
     * this function colors the j pixels
     *
     * @param i resolution
     * @param j number of pixels
     */
    private void castRay(int nX, int nY, int i, int j) {
        Color color;
        if (adaptiveSuperSamplingEnabled) {
            color = adaptiveSuperSampling(nX, nY, i, j, numSamples, maxSamples);
        } else {
            Ray ray = constructRay(nX, nY, i, j);
            color = rayTracer.traceRay(ray);
        }
        this.imageWriter.writePixel(j, i, color);
    }

//add for AntiAliasing
    /**
     * set the number of samples for anti-aliasing
     * @param numSamples the number of samples per pixel
     */
    public void setNumSamples(int numSamples) {
        if (numSamples < 1) {
            throw new IllegalArgumentException("Number of samples must be at least 1");
        }
        this.numSamples = numSamples;
    }

    // add for adaptive super-sampling
    /**
     * private function for adaptive super-sampling
     * @param nX input
     * @param nY input
     * @param i input
     * @param j input
     * @param currentSamples input
     * @param maxSamples input
     * @return color
     */
    private Color adaptiveSuperSampling(int nX, int nY, int i, int j, int currentSamples, int maxSamples) {
        Color color = Color.BLACK;
        List<Color> colors = new ArrayList<>();

        for (int k = 0; k < currentSamples; k++) {
            double offsetX = Math.random() - 0.5;
            double offsetY = Math.random() - 0.5;
            Ray ray = constructRay(nX, nY, i + offsetY, j + offsetX);
            Color sampleColor = rayTracer.traceRay(ray);
            colors.add(sampleColor);
            color = color.add(sampleColor);
        }

        color = color.reduce(currentSamples);

        if (currentSamples < maxSamples) {
            double maxDifference = 0;
            for (int m = 0; m < colors.size(); m++) {
                for (int n = m + 1; n < colors.size(); n++) {
                    double difference = colors.get(m).difference(colors.get(n));
                    if (difference > maxDifference) {
                        maxDifference = difference;
                    }
                }
            }

            if (maxDifference > 0.1) {
                Color additionalColor = adaptiveSuperSampling(nX, nY, i, j, currentSamples * 2, maxSamples);
                color = color.add(additionalColor).reduce(2);
            }
        }

        return color;
    }

    //add for adaptive super-sampling
    /**
     * set the number of samples for adaptive super-sampling
     * @param maxSamples the number of max samples per pixel
     */
    public void setMaxSamples(int maxSamples) {
        if (maxSamples < 1) {
            throw new IllegalArgumentException("Max samples must be at least 1");
        }
        this.maxSamples = maxSamples;
    }

    //add for adaptive super-sampling
    /**
     * for adaptive super-sampling
     * @param enabled input
     */
    public void setAdaptiveSuperSamplingEnabled(boolean enabled) {
        this.adaptiveSuperSamplingEnabled = enabled;
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

//add for AntiAliasing
        /**
         * set the number of samples for anti-aliasing
         * @param numSamples the number of samples per pixel
         * @return this
         */
        public Builder setNumSamples(int numSamples) {
            camera.setNumSamples(numSamples);
            return this;
        }

        //add for adaptive super-sampling
        /**
         * set MaxSamples
         * @param maxSamples input
         * @return this
         */
        public Builder setMaxSamples(int maxSamples) {
            camera.setMaxSamples(maxSamples);
            return this;
        }

        //add for adaptive super-sampling
        /**
         * set enabled
         * @param enabled input
         * @return this
         */
        public Builder setAdaptiveSuperSamplingEnabled(boolean enabled) {
            camera.setAdaptiveSuperSamplingEnabled(enabled);
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