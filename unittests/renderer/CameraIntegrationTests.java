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
    private Camera setupCamera() {
        return Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();
    }

    // function to count the Intersections with the camera
    private int countIntersections(Camera camera, Intersectable intersectable, int nX, int nY) {
        int count = 0;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                var intersections = intersectable.findIntersections(ray);
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
        Camera camera = setupCamera();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));

        assertEquals(2,
                countIntersections(camera, sphere, 3, 3),
                "Wrong number of intersections with the sphere");
    }

    /**
     * Test integration of ray construction and intersection finding with a plane.
     */
    @Test
    void testPlaneIntersections() {
        Camera camera = setupCamera();
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));

        assertEquals(9,
                countIntersections(camera, plane, 3, 3),
                "Wrong number of intersections with the plane");
    }

    /**
     * Test integration of ray construction and intersection finding with a triangle.
     */
    @Test
    void testTriangleIntersections() {
        Camera camera = setupCamera();
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));

        assertEquals(1,
                countIntersections(camera, triangle, 3, 3),
                "Wrong number of intersections with the triangle");
    }
}
