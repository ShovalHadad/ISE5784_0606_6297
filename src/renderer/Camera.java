//package renderer;
//
//import primitives.*;
//
//import java.util.MissingResourceException;
//
//public class Camera implements Cloneable {
//    private Point p0;
//    private Vector vTo, vUp, vRight;
//    private double width = 0, height = 0, distance = 0;
//
//    Camera() {
//    }
//
//    public static Builder getBuilder() {
//        return new Builder();
//    }
//
//    public Ray constructRay(int nX, int nY, int j, int i) {
//        double rY = height / nY;
//        double rX = width / nX;
//        double xJ = (j - (nX - 1) / 2.0) * rX;
//        double yI = -(i - (nY - 1) / 2.0) * rY;
//        Point pIJ = p0.add(vTo.scale(distance))
//                .add(vRight.scale(xJ))
//                .add(vUp.scale(yI));
//        Vector rayDirection = pIJ.subtract(p0);
//        return new Ray(rayDirection, p0);
//    }
//
//    @Override
//    public Camera clone() {
//        try {
//            return (Camera) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError(); // Shouldn't happen
//        }
//    }
//
//    public static class Builder {
//        private final Camera camera;
//
//        public Builder() {
//            this.camera = new Camera();
//        }
//
//        public Builder(Camera camera) {
//            this.camera = camera;
//        }
//
//        public Builder setLocation(Point location) {
//            if (location == null) {
//                throw new IllegalArgumentException("Location cannot be null");
//            }
//            camera.p0 = location;
//            return this;
//        }
//
//        public Builder setDirection(Vector vTo, Vector vUp) {
//            if (vTo == null || vUp == null) {
//                throw new IllegalArgumentException("Vectors cannot be null");
//            }
//            if (vTo.dotProduct(vUp) != 0) {
//                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
//            }
//            camera.vTo = vTo.normalize();
//            camera.vUp = vUp.normalize();
//            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
//            return this;
//        }
//
//        public Builder setVpSize(double width, double height) {
//            if (width <= 0 || height <= 0) {
//                throw new IllegalArgumentException("Width and height must be greater than 0");
//            }
//            camera.width = width;
//            camera.height = height;
//            return this;
//        }
//
//        public Builder setVpDistance(double vpDistance) {
//            if (vpDistance <= 0) {
//                throw new IllegalArgumentException("Distance must be greater than 0");
//            }
//            camera.distance = vpDistance;
//            return this;
//        }
//
//        public Camera build() {
//            if (camera.p0 == null) {
//                throw new MissingResourceException("Missing rendering data", "Camera", "location");
//            }
//            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
//                throw new MissingResourceException("Missing rendering data", "Camera", "direction vectors");
//            }
//            if (camera.width == 0 || camera.height == 0) {
//                throw new MissingResourceException("Missing rendering data", "Camera", "viewport size");
//            }
//            if (camera.distance == 0) {
//                throw new MissingResourceException("Missing rendering data", "Camera", "viewport distance");
//            }
//            return camera.clone();
//        }
//    }
//}




package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.nio.channels.NotYetBoundException;

import static primitives.Util.isZero;

public class Camera {

    //eye of the pinhole Camera
    private Point p0;

    //3D positional Vector towards the view plane
    private Vector vTo;

    //3D positional Vector upwards
    private Vector vUp;

    // calculated 3D positional Vector to the right
    private Vector vRight;

    // physical width of the viewplane
    private double width=0;

    // physical height of the viewplane
    private double height=0;

    //distance from the eye of the camera to the view plane
    private double distance=0;

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("the vectors should be perpendicular");
        }

        // position of the camera in 3D space
        this.p0 = p0;

        //normalizing vTo and vUp
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        //no need to normalize
        this.vRight = this.vTo.crossProduct(this.vUp);
    }


    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        //view plane Center
        Point Pc = p0.add(vTo.scale(distance));

        //pixel ratios
        double Ry = height / nY;
        double Rx = width / nX;

        //Point of the center pixel in Nx,Ny coordinates
        // starting from view plane center
        Point Pij = Pc;

        //offsets for Pij
        double Yi = -(i - (nY - 1) / 2.0) * Ry;
        double Xj = (j - (nX - 1) / 2.0) * Rx;

        //adding opffsts if necessary
        if(!isZero(Xj)){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if(!isZero(Yi)){
            Pij = Pij.add(vUp.scale(Yi));
        }

        // return ray from camera through view plane Pij
        return new Ray(Pij.subtract(p0),p0);
    }




}