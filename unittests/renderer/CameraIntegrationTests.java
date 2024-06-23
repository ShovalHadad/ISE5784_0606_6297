package renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import scene.Scene;

/**
 * Camera Integration tests for ray tracing
 */
class CameraIntegrationTests {
    private final Scene scene  = new Scene("Test scene");
    // camera to check on
    private Camera setupCamera(Point location) {
        return Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setImageWriter(new ImageWriter("base test", 3, 3))
                .setLocation(location)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
    }

    // function to count the Intersections with the camera
    private int countIntersections(Camera camera, Intersectable geometry, int nX, int nY) {
        int count = 0;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                var intersections = geometry.findGeoIntersections(ray);
                if (intersections != null) {
                    count += intersections.size();
                }
            }
        }
        return count;
    }

    /**
     * Test integration of ray construction and intersection finding with a sphere.
     */
    @Test
    void testSphereIntersections() {
        Camera camera = setupCamera(Point.ZERO);

        //TC01: Sphere r=1 (2 intersections)
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        //TC02: Sphere r=0.5 (0 intersections)
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        //TC03: Sphere r=2.5 (18 intersections)
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        camera = setupCamera(new Point(0, 0, 0.5));
        assertEquals(18,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        //TC04: Sphere r=2 (10 intersections)
        sphere = new Sphere(2, new Point(0, 0, -2));
        camera = setupCamera(new Point(0, 0, 0.5));
        assertEquals(10,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        //TC05: Sphere r=4 (9 intersections)
        sphere = new Sphere(4, new Point(0, 0, 1));
        assertEquals(9,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");
    }

    /**
     * Test integration of ray construction and intersection finding with a plane.
     */
    @Test
    void testPlaneIntersections() {
     Camera camera = setupCamera(Point.ZERO);

        //TC01: The plane parallel to the View Plane (9 intersections)
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        assertEquals(9,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");

        //TC02: Diagonal plane to the View Plane (9 intersections)
        plane = new Plane(new Point(0, 0, -0.5), new Vector(0, 0, 1));
        assertEquals(9,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");

        //TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)
        plane = new Plane(new Point(0, 0, -2), new Vector(1, 1, 1));
        assertEquals(6,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");

        // TC04:The plane behind the view plane (0 intersections)
        plane = new Plane(new Point(0, 0, 4), new Vector(0, 0, -1));
        assertEquals(0,
                countIntersections(camera, plane, 3, 3),
                "Bad number of intersections");

    }

    /**
     * Test integration of ray construction and intersection finding with a triangle.
     */
    @Test
    void testTriangleIntersections() {
        Camera camera = setupCamera(Point.ZERO);

        //TC01: Small triangle (1 intersection)
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertEquals(1,
                countIntersections(camera, triangle, 3, 3),
                "Wrong number of intersections with the triangle");

        //TC02: Large triangle (2 intersection)
        triangle = new Triangle(
                new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertEquals(2,
                countIntersections(camera, triangle, 3, 3),
                "Wrong number of intersections with the triangle");
    }
}
