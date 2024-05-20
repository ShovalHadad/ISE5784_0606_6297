package primitives;

/**
 * Ray class
 */
public class Ray {
    final Vector direction;
    final Point head;
    //constructor
    public Ray(Vector v, Point p) {
        if((v.xyz.d1 + v.xyz.d2 +v.xyz.d3) != 1){
            direction = v.normalize();
        }
        else direction = v;
        head = p;
    }

    //getters
    public Point getHead(){
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return obj instanceof Ray;
    }

    @Override
    public String toString() {
        return "\nRay = Head - " + head.toString() + "Direction - " + direction.toString();
    }
}