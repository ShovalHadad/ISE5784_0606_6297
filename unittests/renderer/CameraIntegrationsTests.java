////////////////////////////////////
package renderer;

import org.junit.jupiter.api.*;
import primitives.*;
import geometries.*;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing constructRay(int, int, int, int)
 * and findIntersections(Ray) of Sphere, Plane, and Triangle.
 */
public class CameraIntegrationsTests {

    /**
     * Return the number of intersections points of all geometries.
     *
     * @param geometry to intersect object
     * @param camera   the camera
     * @param nX       number of rows (int)
     * @param nY       number of columns (int)
     * @return the number of intersections
     */
    private int countIntersectionsCameraGeometry(Camera camera, int nX, int nY, Intersectable geometry) {
        int count = 0;
        List<Point> intersections;

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                intersections = geometry.findIntersections(camera.constructRay(nX, nY, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }
        return count; //Return the number of points of intersection between the geometries and a ray from the camera.
    }

    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    public void CameraRaySphereIntegration() {

        Camera camera1 = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .build();

        Camera camera2 = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .build();

        //TC01: Sphere r=1 (2 intersections)
        assertEquals(2, countIntersectionsCameraGeometry(camera1, 3, 3,
                new Sphere( 1d,new Point(0, 0, -3))), "Bad number of intersections");

        //TC02: Sphere r=2.5 (18 intersections)
        assertEquals(18, countIntersectionsCameraGeometry(camera2, 3, 3,
                new Sphere( 2.5,new Point(0, 0, -2.5))), "Bad number of intersections");

        //TC03: Sphere r=2 (10 intersections)
        assertEquals(10, countIntersectionsCameraGeometry(camera2, 3, 3,
                new Sphere( 2d,new Point(0, 0, -2))), "Bad number of intersections");

        //TC04: Sphere r=4 (9 intersections)
        assertEquals(9, countIntersectionsCameraGeometry(camera2, 3, 3,
                new Sphere( 4d,new Point(0, 0, 1))), "Bad number of intersections");

        //TC05: Sphere r=0.5 (0 intersections)
        assertEquals(0, countIntersectionsCameraGeometry(camera1, 3, 3,
                new Sphere( 0.5,new Point(0, 0, 1))), "Bad number of intersections");
    }

    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    public void CameraRayTriangleIntegration() {

        Camera camera = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .build();

        //TC01: Small triangle (1 intersection)
        assertEquals(1, countIntersectionsCameraGeometry(camera, 3, 3, new Triangle(new Point(1, -1, -2),
                new Point(-1, -1, -2), new Point(0, 1, -2))), "Bad number of intersections");

        //TC02: Large triangle (2 intersection)
        assertEquals(2, countIntersectionsCameraGeometry(camera, 3, 3, new Triangle(new Point(1, -1, -2),
                new Point(-1, -1, -2), new Point(0, 20, -2))), "Bad number of intersections");
    }


    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    public void CameraRayPlaneIntegration() {

        Camera camera = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .build();

        //TC01: The plane parallel to the View Plane (9 intersections)
        assertEquals(countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, 5),
                new Vector(0, 0, 1))), 9, "Bad number of intersections");

        //TC02: Diagonal plane to the View Plane (9 intersections)
        assertEquals(countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, 5),
                new Vector(0, -1, 2))), 9, "Bad number of intersections");

        ////TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)
        assertEquals(6, countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, 2),
                new Vector(1, 1, 1))), "Bad number of intersections");

        // TC04:The plane behind the view plane (0 intersections)
        assertEquals(0, countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, -4),
                new Vector(0, 0, 1))), "Bad number of intersections");
    }
}


