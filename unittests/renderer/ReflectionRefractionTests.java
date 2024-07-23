package renderer;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import lighting.*;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
           .setDirection(new Vector(0,0,-1),new Vector(0,1,0))
           .setRayTracer(new SimpleRayTracer(scene));

   /**
    * Produce a picture of a sphere lighted by a spot light
    */
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
    * Produce a picture of a sphere lighted by a spot light
    */
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

   /**
    * Produce a picture of two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow
    */
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
    * Christmas Tree Photo - stage 7
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

              new Cylinder(10, new Ray(new Point(0, -90, -30), new Vector(0, -1, 0)), 100)
                      .setEmission(new Color(112, 76, 4))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),


              //The Christmas decorations:

              //The Star stand:
              new Triangle(new Point(-7, 150, 1), new Point(7, 150, 1), new Point(0, 160, 1))
                      .setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
              //The Star:
              // star(0, 190, 0,30,40),


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
    * some geometries shapes in a photo
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
      cameraBuilder.setLocation(new Point(30, -300, 500)).setVpDistance(80)
              .setVpSize(200, 200)
              .setImageWriter(new ImageWriter("customScene", 600, 600))
              .build()
              .renderImage()
              .writeToImage();

   }

   /**
    * some fare geometries shapes in a photo
    */
   @Test
   public void customScene1() {
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
      cameraBuilder.setLocation(new Point(0, 0, 500))
              .setVpDistance(500)
              .setVpSize(400, 400)
              .setNumSamples(4)
              .setImageWriter(new ImageWriter("custom", 1000, 1000))
              .build()
              .renderImage()
              .writeToImage();
   }

   /**
    * private function to make a star
    */
   private Intersectable star(double x, double y, double z, double height, double width) {
      // The vertices for a star
      double reflaction = 0.2;
      Point p1 = new Point(x, y, z); // top
      Point p2 = new Point(x + (width * 0.125), y - (height * 0.5), z);
      Point p3 = new Point(x + (width * 0.5), y - (height * 0.5), z);
      Point p4 = new Point(x + (width * 0.225), y - (height * 0.77), z);
      Point p5 = new Point(x + (width * 0.375), y - (height * 1.33), z);
      Point p6 = new Point(x, y - (height * 0.83), z + 5); // center point
      Point p7 = new Point(x - (width * 0.375), y - (height * 1.33), z);
      Point p8 = new Point(x - (width * 0.225), y - (height * 0.77), z);
      Point p9 = new Point(x - (width * 0.5), y - (height * 0.5), z);
      Point p10 = new Point(x - (width * 0.125), y - (height * 0.5), z);
      Point p11 = new Point(x, y - height, z); // bottom

      return new Geometries(
              new Triangle(p1, p2, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p2, p3, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p3, p4, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p4, p5, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p5, p11, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p11, p7, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p7, p8, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p8, p9, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p9, p10, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction)),
              new Triangle(p10, p1, p6).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(reflaction))
      );
   }

   /**
    * private function to make stars
    * uses the star function
    */
   private Geometries createStars() {
      double x = -200;
      double y = 230;
      double z = -400;
      double h = 20;
      Geometries geometries = new Geometries();
      for (int j = 0; j <= 2; j++) {  // number of rows of stars
         for (int i = 0; i < 8; i++) {  // number of stars in a row
            if (j == 1) {
               geometries.add(star(x + 35 + (74 * i), y + (j * 50), z, h, h));
            } else
               geometries.add(star(x + (74 * i), y + (j * 50), z, h, h));
         }
      }
      return geometries;
   }

   /**
    * private function to make trees
    * uses the treeRight function and the treeLeft function
    */
   private Geometries createTrees() {
      double x = 0;
      double y = -10;
      double z = 0;
      double h = 120;
      Geometries geometries = new Geometries();
      for (int j = 0; j <= 5; j++) {  // number of rows of stars
         for (int i = 0; i < 4; i++) {  // number of stars in a row
            if (j != 3) {
               if (j % 2 == 0) { // שורה זוגית  0 2 4
                  if (j == 0 || i != 0) {
                     geometries.add(treeRight(x + 20 + (50 * i), y - (j * 8), z + (j * 20), h));
                     geometries.add(treeLeft(x - 20 - (50 * i), y - (j * 8), z + (j * 20), h));
                  }
               } else {  // 1 3 5 שורה אי-זוגית
                  if (i != 0) {
                     geometries.add(treeRight(x + (50 * i), y - (j * 8), z + (j * 20), h));
                     geometries.add(treeLeft(x - (50 * i), y - (j * 8), z + (j * 20), h));
                  }
               }
            } else {
               if (i > 1) {
                  geometries.add(treeRight(x + (50 * i), y - (j * 8), z + (j * 20), h));
                  geometries.add(treeLeft(x - (50 * i), y - (j * 8), z + (j * 20), h));
               }
            }
         }
      }

      return geometries;
   }

   /**
    * private function to make a tree n the right side of the photo
    */
   private Geometries treeRight(double x, double y, double z, double height) {
      return new Geometries(
              // Tree levels:
              // first level-
              new Triangle(new Point(x - 12, y - (height * 0.25), z), new Point(x + 18, y - (height * 0.25), z), new Point(x + 2, y, z))
                      .setEmission(new Color(18, 138, 45))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              // second level-
              new Triangle(new Point(x - 20, y - (height * 0.6), z - 5), new Point(x + 30, y - (height * 0.6), z - 5), new Point(x + 3, y - 5, z - 5))
                      .setEmission(new Color(14, 105, 18))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              // third level-
              new Triangle(new Point(x - 20, y - (height * 0.85), z - 10), new Point(x + 35, y - (height * 0.85), z - 10), new Point(x + 5, y - 10, z - 10))
                      .setEmission(new Color(13, 82, 16))
                      .setMaterial(new Material().setKd(0.6).setKs(0.1).setShininess(100).setKr(0)),
              // tree stem-
              new Cylinder(5, new Ray(new Point(x + 7, y - height - 5, z - 20), new Vector(0, 1, 0)), (height * 0.20))
                      .setEmission(new Color(112, 76, 4))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))
      );
   }

   /**
    * private function to make a tree n the right left of the photo
    */
   private Geometries treeLeft(double x, double y, double z, double height) {
      return new Geometries(
              // Tree levels:
              // first level-
              new Triangle(new Point(x + 12, y - (height * 0.25), z), new Point(x - 18, y - (height * 0.25), z), new Point(x - 3, y, z))
                      .setEmission(new Color(18, 138, 45))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              // second level-
              new Triangle(new Point(x + 20, y - (height * 0.6), z - 5), new Point(x - 30, y - (height * 0.6), z - 5), new Point(x - 3, y - 5, z - 5))
                      .setEmission(new Color(14, 105, 18))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              // third level-
              new Triangle(new Point(x + 20, y - (height * 0.85), z - 10), new Point(x - 35, y - (height * 0.85), z - 10), new Point(x - 5, y - 10, z - 10))
                      .setEmission(new Color(13, 82, 16))
                      .setMaterial(new Material().setKd(0.6).setKs(0.1).setShininess(100).setKr(0)),
              // tree stem-
              new Cylinder(5, new Ray(new Point(x - 7, y - height - 5, z - 20), new Vector(0, 1, 0)), (height * 0.20))
                      .setEmission(new Color(112, 76, 4))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))
      );
   }

   /**
    * final photo for mini project 1 and 2
    */
   @Test
   public void starryNight() {
      // Define the geometries for the Christmas tree
      scene.setBackground(new Color(15, 15, 110));
      scene.geometries.add(
              // middle mountain-
              new Triangle(new Point(-200, -200, -200), new Point(210, -200, -200), new Point(0, 160, -200))
                      .setEmission(new Color(GRAY))
                      .setMaterial(new Material()
                       .setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-200, -199, -190), new Point(150, -199, -190), new Point(0, 160, -190))
                      .setEmission(new Color(lightGray))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              // left mountain-
              new Triangle(new Point(-400, -201, -300), new Point(50, -201, -300), new Point(-180, 100, -300))
                      .setEmission(new Color(GRAY))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-400, -200, -290), new Point(0, -200, -290), new Point(-180, 100, -290))
                      .setEmission(new Color(lightGray))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              // right mountain-
              new Triangle(new Point(-50, -201, -280), new Point(400, -201, -280), new Point(180, 100, -280))
                      .setEmission(new Color(GRAY))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-50, -200, -270), new Point(350, -200, -270), new Point(180, 100, -270))
                      .setEmission(new Color(lightGray))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),

              // snow on middle mountain-
              new Triangle(new Point(-43, 80, -180), new Point(32, 80, -180), new Point(0, 157, -180))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-42, 80, -180), new Point(-5, 80, -180), new Point(-30, 30, -180))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-5, 80, -180), new Point(31, 80, -180), new Point(15, 30, -180))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),

              // snow on left mountain-
              new Triangle(new Point(-235, 20, -280), new Point(-130, 20, -280), new Point(-178, 100, -280))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-235, 20, -280), new Point(-180, 20, -280), new Point(-215, -30, -280))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(-180, 20, -280), new Point(-130, 20, -280), new Point(-155, -30, -280))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),

              // snow on right mountain-
              new Triangle(new Point(117, 20, -260), new Point(222, 20, -260), new Point(177, 100, -260))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(117, 20, -260), new Point(170, 20, -260), new Point(135, -30, -260))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),
              new Triangle(new Point(170, 20, -260), new Point(222, 20, -260), new Point(210, -30, -260))
                      .setEmission(new Color(white))
                      .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKr(0)),

              // snow man-
              new Sphere(12, new Point(0, -85, 10)) //175
                      .setEmission(new Color(207, 209, 212))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100)),
              new Sphere(18, new Point(0, -110, 10)) //160
                      .setEmission(new Color(207, 209, 212))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100)),
              new Sphere(25, new Point(0, -140, 10))  //145
                      .setEmission(new Color(207, 209, 212))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100)),

              // snow man hat-
              new Cylinder(8, new Ray(new Point(0, -72, 20), new Vector(0, 1, 0)), 10)
                      .setEmission(new Color(112, 76, 4))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),
              new Cylinder(13, new Ray(new Point(0, -72, 20), new Vector(0, -1, 0)), 1)
                      .setEmission(new Color(112, 76, 4))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10)),

              // snow man eyes, nose and smile -
              // left eye
              new Sphere(1.3, new Point(-3, -80, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),
              // right eye
              new Sphere(1.3, new Point(3, -80, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),

              // nose
              new Sphere(1.5, new Point(0, -83.5, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),

              // smile
              new Sphere(1, new Point(0, -88, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),
              new Sphere(1, new Point(-3, -87, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),
              new Sphere(1, new Point(3, -87, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),
              new Sphere(1, new Point(-5, -85, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),
              new Sphere(1, new Point(5, -85, 25))
                      .setEmission(new Color(59, 60, 61))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKr(0.5)),

              // Buttons
              new Sphere(2, new Point(0, -100, 30))
                      .setEmission(new Color(RED))  // first button
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100)),
              new Sphere(2, new Point(0, -110, 30))
                      .setEmission(new Color(RED))  // second button
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100)),
              new Sphere(2, new Point(0, -120, 30))
                      .setEmission(new Color(RED))  // third button
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100)),
              createTrees(),
              createStars(),

              // ground
              new Triangle(new Point(-1000, -105, -50), new Point(1000, -105, -50), new Point(0, -300, -50))
                      .setEmission(new Color(227, 230, 228)),

              // moon-
              new Sphere(25, new Point(-180, 180, -100))
                      .setEmission(new Color(lightGray))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(100).setKt(0.7).setKr(0.3)),
              // moon shadow-
              new Sphere(18.7, new Point(-160, 164, -50))
                      .setEmission(new Color(5, 5, 100))
                      .setMaterial(new Material().setKt(0.1))

      );
      // Adding lights
      scene.lights.add(new SpotLight(new Color(lightGray), new Point(-180, 1000, -200), new Vector(1, -1, 2))
              .setkL(4E-4).setkQ(2E-5));
      //scene.lights.add(new SpotLight(new Color(blue), new Point(-150, 100, 200), new Vector(1, -2, -1))
      scene.lights.add(new SpotLight(new Color(gray), new Point(-150, 100, 200), new Vector(1, -2, -1))
              .setkL(4E-4).setkQ(2E-5));
      //scene.lights.add(new SpotLight(new Color(gray), new Point(150, 100, 200), new Vector(-1, -2, -1)).setkL(4E-4).setkQ(2E-5));  // <= to add another light
      scene.lights.add(new SpotLight(new Color(blue), new Point(150, 100, 200), new Vector(-1, -2, -1)).setkL(4E-4).setkQ(2E-5));  // <= to add another light
      cameraBuilder.setLocation(new Point(0, 0, 500))
              .setVpDistance(500)
              .setVpSize(400, 400)
              .setNumSamples(8)
              .setImageWriter(new ImageWriter("starryNight_888", 1000, 1000))
              .build()
              .renderImage()
              .writeToImage();
   }
}