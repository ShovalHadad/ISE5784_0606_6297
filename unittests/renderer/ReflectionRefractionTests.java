package renderer;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import lighting.*;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
      .setDirection(new Vector(0,0,-1),new Vector(0,1,0))
      .setRayTracer(new SimpleRayTracer(scene));

   /**
    * Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      scene.geometries.add(
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                          .setkL(0.0004).setkQ(0.0000006));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(150, 150)
         .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /**
    * Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      scene.geometries.add(
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKr(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
         .setkL(0.00001).setkQ(0.000005));

      cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
         .setVpSize(2500, 2500)
         .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setkL(4E-5).setkQ(2E-7));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(200, 200)
         .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
         .build()
         .renderImage()
         .writeToImage();
   }




/**
 * Christmas Tree Photo
 */
   @Test
   public void christmasTreeWithDecorations() {
      // The vertices for a star
      Point p1 = new Point(0, 190, 0); //top
      Point p2 = new Point(5, 175, 0);
      Point p3 = new Point(20, 175, 0);
      Point p4 = new Point(9, 167, 0);
      Point p5 = new Point(15, 150, 0);
      Point p6 = new Point(0, 165, 4); //center point
      Point p7 = new Point(-15, 150, 0);
      Point p8 = new Point(-9, 167, 0);
      Point p9 = new Point(-20, 175, 0);
      Point p10 = new Point(-5, 175, 0);
      Point p11 = new Point(0, 160, 0); //bottom


      // Define the geometries for the Christmas tree
      scene.setBackground(new Color(lightGray));
      scene.geometries.add(
              //The Christmas Tree:
              //first tree level
              new Triangle(new Point(-50, 80, 0), new Point(50, 80, 0), new Point(0, 160, 0))
                      .setEmission(new Color(4, 207, 78))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),

              //second tree level
              new Triangle(new Point(-80, 0, -10), new Point(80, 0, -10), new Point(0, 160, -10))
                      .setEmission(new Color(34, 186, 67))
                     .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),

              //third tree level
              new Triangle(new Point(-110, -100, -20), new Point(110, -100, -20), new Point(0, 160, -20))
                      .setEmission(new Color(18, 138, 45))
                      .setMaterial(new Material().setKd(0.6).setKs(0.1).setShininess(100).setKr(0)),

              new Cylinder(10, new Ray(new Vector(0,-1,0),new Point(0, -90, -30)),100)
                      .setEmission(new Color(112, 76, 4))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),



              //The Christmas decorations:

              //The Star stand:
              new Triangle(new Point(-7, 150, 1), new Point(7, 150, 1), new Point(0, 160, 1))
                      .setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
              //The Star:
              new Triangle(p1, p2, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p2, p3, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p3, p4, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p4, p5, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p5, p11, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p11, p7, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p7, p8, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p8, p9, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p9, p10, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Triangle(p10, p1, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),

              //The Ornaments:
              new Sphere(5.5, new Point(21, 127, 1))
                      .setEmission(new Color(RED))  // Red  ornament
                      .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(10)),
              new Sphere(5.5, new Point(5, 115, 1))
                      .setEmission(new Color(190, 20, 100)) // Dark Pink  ornament
                      .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(10)),
              new Sphere(5.5, new Point(-16, 105, 1))
                      .setEmission(new Color(235, 35, 168)) // Pink  ornament
                      .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(10)),
              new Sphere(5.5, new Point(-38, 95, 1))
                      .setEmission(new Color(192, 3, 255))  // Light Purple  ornament
                      .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(10)),

              new Sphere(6, new Point(45, 70, -9))
                      .setEmission(new Color(161, 9, 217))  // Purple  ornament
                      .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10)),
              new Sphere(6, new Point(22, 55, -10))
                      .setEmission(new Color(14, 5, 179)) // Dark Blue  ornament
                      .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10)),
              new Sphere(6, new Point(-8, 40, -10))
                      .setEmission(new Color(12, 48, 245)) // Blue  ornament
                      .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10)),
              new Sphere(6, new Point(-38, 26, -10))
                      .setEmission(new Color(50, 160, 280)) // Turquoise  ornament
                      .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(10)),
              new Sphere(6, new Point(-70, 15, -10))
                      .setEmission(new Color(42, 177, 191)) // Light Turquoise  ornament
                      .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(10)),

              new Sphere(6.5, new Point(73, -15, -19))
                      .setEmission(new Color(130, 245, 120))  // Light Green  ornament
                      .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
              new Sphere(6.5, new Point(47, -30, -19))
                      .setEmission(new Color(232, 245, 120)) // Light Yellow ornament
                      .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10)),
              new Sphere(6.5, new Point(15, -45, -19))
                      .setEmission(new Color(231, 240, 50)) // Yellow  ornament
                      .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10)),
              new Sphere(6.5, new Point(-20, -60, -19))
                      .setEmission(new Color(235, 179, 12)) // Orange  ornament
                      .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(10)),
              new Sphere(6.5, new Point(-60, -73, -19))
                      .setEmission(new Color(209, 101, 13)) // Dark Orange  ornament
                      .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(10)),
              new Sphere(6.5, new Point(-98, -85, -19))
                      .setEmission(new Color(RED)) // Blue  ornament
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))


      );

      // Add lights and ambient light
         scene.setAmbientLight(new AmbientLight(new Color(YELLOW), 0));
      scene.lights.add(new SpotLight(new Color(800, 500, 300), new Point(0, 300, 150), new Vector(-2, -3, -4))
      //scene.lights.add(new SpotLight(new Color(800, 500, 300), new Point(200, 200, 100), new Vector(-2, -2, -3))
              .setkL(0.0001).setkQ(0.000005));
      // Camera settings
      cameraBuilder.setLocation(new Point(0, 0, 500)).setVpDistance(500)
              .setVpSize(400, 400)
              .setImageWriter(new ImageWriter("christmasTreeWithDecorations", 800, 800))
              .build()
              .renderImage()
              .writeToImage();
   }

   /**
    * ugly whanna be clown photo
    */
   @Test
   public void clownScene() {
      scene.setBackground(new Color(lightGray));
      // Clown body (spheres)
      scene.geometries.add(
              //head
              new Sphere(50d, new Point(0, 0, -100)).setEmission(new Color(225, 163, 127))
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80)),
              //body
              new Sphere(20d, new Point(0, -60, -105)).setEmission(new Color(0, 255, 0))
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80)),
             //left eye
              new Sphere(10d, new Point(-15.5, 10, -60)).setEmission(new Color(WHITE))
                      .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
              //right eye
              new Sphere(10d, new Point(15.5, 10, -60)).setEmission(new Color(WHITE))
                      .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
              //left Pupil
              new Sphere(5d, new Point(-17.5, 10, -50)).setEmission(new Color(BLACK))
                      .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
              //right Pupil
              new Sphere(5d, new Point(17.5, 10, -50)).setEmission(new Color(BLACK))
                      .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
              // Nose
              new Sphere(10d, new Point(0, -10, -45)).setEmission(new Color(RED))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
              // Smile
              new Polygon(new Point(-10, -30, -45), new Point(10, -30, -45), new Point(20, -25, -40), new Point(-20, -25, -40))
                      .setEmission(new Color(RED))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
      );

      // Hat (using triangles)
      scene.geometries.add(
              new Triangle(new Point(-50, 40, -70), new Point(50, 40, -70), new Point(0, 100, -70))
                      .setEmission(new Color(BLUE))  // Hat
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
      );

      // Arms connected to body
      scene.geometries.add(
              new Polygon(new Point(-20, -40, 0), new Point(-30, -40, 0), new Point(-30, -20, 0), new Point(-20, -20, 0))
                      .setEmission(new Color(255, 165, 0))  // Left Arm
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80)),
              new Polygon(new Point(20, -40, 0), new Point(30, -40, 0), new Point(30, -20, 0), new Point(20, -20, 0))
                      .setEmission(new Color(255, 165, 0))  // Right Arm
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80))
      );

      // Balloons with strings
      scene.geometries.add(
              new Sphere(10d, new Point(-70, 50, -100)).setEmission(new Color(RED))  // Balloon 1
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80)),
              new Sphere(10d, new Point(70, 50, -100)).setEmission(new Color(BLUE))  // Balloon 2
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80)),
              new Sphere(10d, new Point(-90, 70, -100)).setEmission(new Color(YELLOW))  // Balloon 3
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80)),
              new Sphere(10d, new Point(90, 70, -100)).setEmission(new Color(GREEN))  // Balloon 4
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(80))
      );

      // Adding lights
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0));
      scene.lights.add(new SpotLight(new Color(WHITE), new Point(0, 300, 200), new Vector(-1, -1, -2))
              .setkL(0.0001).setkQ(0.000005));

      // Camera settings
      cameraBuilder.setLocation(new Point(0, 0, 500)).setVpDistance(500)
              .setVpSize(200, 200)
              .setImageWriter(new ImageWriter("clownScene", 600, 600))
              .build()
              .renderImage()
              .writeToImage();
   }

   /**
    * some geometries shapes un a photo
    */
   @Test
   public void customScene() {
      // Adding geometries
      scene.geometries.add(
              new Sphere(50d, new Point(100, -50, -100)).setEmission(new Color(RED))
                      .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(80).setKt(0.2)),
              new Triangle(new Point(-50, -20, -100), new Point(50, -20, -100), new Point(0, 80, -100))
                      .setEmission(new Color(GREEN))
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(100).setKt(0.2).setKr(0.3)),
              new Plane(new Point(0, 0, -150), new Vector(0, 0, 1))
                      .setEmission(new Color(GRAY))
                      .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(30)),
              new Sphere(30d, new Point(-80, 50, -50)).setEmission(new Color(BLUE))
                      .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(70).setKt(0.5))
      );

      // Adding lights
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));
      scene.lights.add(new SpotLight(new Color(800, 500, 300), new Point(200, 200, 100), new Vector(-2, -2, -3))
              .setkL(0.0001).setkQ(0.000005));

      // Camera settings
      cameraBuilder.setLocation(new Point(0, 0, 500)).setVpDistance(500)
              .setVpSize(200, 200)
              .setImageWriter(new ImageWriter("customSceneImage", 600, 600))
              .build()
              .renderImage()
              .writeToImage();
   }
}
