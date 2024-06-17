package primitives;


import java.util.List;

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
        direction = v.normalize();
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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ray ray = (Ray) obj;
        return direction.equals(ray.direction) && head.equals(ray.head);
    }

    @Override
    public String toString() {
        return "\nRay = Head - " + head.toString() + "Direction - " + direction.toString();
    }

    /**
     * returns the closest point to the head of ray
     * @param points list of points
     * @return point
     */
   public Point findClosestPoint(List<Point> points){
       if (points == null || points.isEmpty()) return null;
       Point closestPoint = null;
       double minDistance = Double.MAX_VALUE;
       for (var pointInPoints : points) {
           double pointDistance = this.head.distance(pointInPoints);
           if (pointDistance < minDistance) {
               minDistance = pointDistance;
               closestPoint = pointInPoints;
           }
       }
       return closestPoint;
   }
}