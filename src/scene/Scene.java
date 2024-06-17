package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Scene Class
 * A class that will realize the scene we want to build
 */
public class Scene {
    public String name;  // name of scene
    public Color background = Color.BLACK;  // background color
    public AmbientLight ambientLight = AmbientLight.NONE; // ambient lighting
    public Geometries geometries = new Geometries(); //the 3D model

    /**
     * Constructor for initializing the name
     * @param name The name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * set the background color
     *
     * @param color the background color
     * @return the object for the scene
     */
    public Scene setBackground(Color color) {
        this.background = color;
        return this;
    }

    /**
     * set of the ambient lighting
     *
     * @param ambientLight the ambient lighting
     * @return the object for the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * set the geometries
     *
     * @param geometries the 3D model
     * @return the object for the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}
