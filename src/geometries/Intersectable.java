package geometries;
import primitives.*;
import java.util.List;

public abstract class Intersectable {

    //    /**
//     * function to find intersections points with the ray
//     * @param ray to check on
//     * @return list of intersections points
//     */
//    public List<Point> findIntersections(Ray ray) {
//        return null;
//    }
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * function to find Intersections GeoPoints with ray
     *
     * @param ray input
     * @return list of GeoPoints
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * function to help find Intersections GeoPoints with ray
     * .
     * for NVI - non-virtual interface
     *
     * @param ray input
     * @return list of GeoPoints
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }


    /**
     * GeoPoint class
     * connects between geometry and point
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * constructor with parameters
         *
         * @param geometry input
         * @param point    input
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GeoPoint other = (GeoPoint) obj;
            return this.geometry == other.geometry && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "\nPoint of  GeoPoint" + point.toString() + "\nGeometry " + geometry.getEmission();
        }
    }
}
