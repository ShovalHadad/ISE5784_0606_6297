package renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
//import scene.Scene;

/**
 * Camera Integration tests for ray tracing
 */
class CameraIntegrationTests {

    // camera to check on
    private Camera setupCamera(Point location) {
        return Camera.getBuilder()
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
                var intersections = geometry.findIntersections(ray);
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
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));

        assertEquals(2,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        camera = setupCamera(new Point(0, 0, 0.5));
        assertEquals(18,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

        sphere = new Sphere(2, new Point(0, 0, -2));
        camera = setupCamera(new Point(0, 0, 0.5));
        assertEquals(10,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");

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
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        assertEquals(9,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");

        plane = new Plane(new Point(0, 0, -0.5), new Vector(0, 0, 1));
        assertEquals(9,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");

        plane = new Plane(new Point(0, 0, -2), new Vector(1, 1, 1));
        assertEquals(6,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");

    }

    /**
     * Test integration of ray construction and intersection finding with a triangle.
     */
    @Test
    void testTriangleIntersections() {
        Camera camera = setupCamera(Point.ZERO);
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));

        assertEquals(1,
                countIntersections(camera, triangle, 3, 3),
                "Wrong number of intersections with the triangle");

        triangle = new Triangle(
                new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertEquals(2,
                countIntersections(camera, triangle, 3, 3),
                "Wrong number of intersections with the triangle");
    }
}
