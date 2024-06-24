package primitives;


import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Ray class
 */
public class Ray {
    private static final double DELTA = 0.1;

    final Vector direction;
    final Point head;

    /**
     * constructor
     *
     * @param v vector
     * @param p point
     */
    public Ray(Vector v, Point p) {
        direction = v.normalize();
        head = p;
    }

    /**
     * Constructor to initialize ray
     *
     * @param p  point of the ray
     * @param n   normal vector
     * @param dir direction vector of the ray
     */
    public Ray(Vector dir, Point p ,Vector n) {
        double delta = dir.dotProduct(n) >= 0 ? DELTA : -DELTA;
        head = p.add(n.scale(delta));
        direction = dir;
    }

    /**
     * get function for head
     *
     * @return head
     */
    public Point getHead() {
        return head;
    }

    /**
     * get function for direction
     *
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

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }
    //    /**
//     * returns the closest point to the head of ray
//     * @param points list of points
//     * @return point
//     */
//   public Point findClosestPoint(List<Point> points){
//       if (points == null || points.isEmpty()) return null;
//       Point closestPoint = null;
//       double minDistance = Double.MAX_VALUE;
//       for (var pointInPoints : points) {
//           double pointDistance = this.head.distance(pointInPoints);
//           if (pointDistance < minDistance) {
//               minDistance = pointDistance;
//               closestPoint = pointInPoints;
//           }
//       }
//       return closestPoint;
//   }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * returns the closest geoPoint to the head of ray
     *
     * @param geoPoints list of geoPoints
     * @return geoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null || geoPoints.isEmpty()) return null;
        GeoPoint closestGeoPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (var GeoPointInGeoPoints : geoPoints) {
            double pointDistance = this.head.distance(GeoPointInGeoPoints.point);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestGeoPoint = GeoPointInGeoPoints;
            }
        }
        return closestGeoPoint;
    }
}