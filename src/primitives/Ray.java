package primitives;

/**
 * Ray class
 */
public class Ray {
    final Vector direction;
    final Point head;

    /**
     * constructor
     * @param v vector
     * @param p point
     */
    public Ray(Vector v, Point p) {
        if((v.xyz.d1 + v.xyz.d2 +v.xyz.d3) != 1){
            direction = v.normalize();
        }
        else direction = v;
        head = p;
    }

    /**
     * get function for head
     * @return head
     */
    public Point getHead(){
        return head;
    }

    /**
     * get function for direction
     * @return direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Gets a point on the ray
     * by calculating head + t*direction.
     *
     * @param t A scalar to calculate the point.
     * @return A point on the ray.
     */
    public Point getPoint(double t) {
        return Util.isZero(t) ? head : head.add(direction.scale(t));
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