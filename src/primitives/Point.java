package primitives;

import static primitives.Util.isZero;

/**
 *
 */
public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);

    final Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    //public boolean equals(Point point) {
        //if (this == point) return true;
        //return xyz.equals(point.xyz);
    //}
    /**
     *
     * @param xyz
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     *
     * @param p1
     * @return
     */
    public Vector subtract(Point p1){
        Point point = new Point(xyz.subtract(p1.xyz));
        if (point.equals(ZERO))
            throw new IllegalArgumentException("can not be zero");
        return new Vector(point.xyz);
    }

    /**
     *
     * @param v1
     * @return
     */
    public Point add(Vector v1) {
        Point point = new Point(xyz.add(v1.xyz));
        if (point.equals(ZERO))
            throw new IllegalArgumentException("can not be zero");
        return point;
    }

    /**
     * x^2 + y^2 + z^2
     * @param p1
     * @return x^2 + y^2 + z^2
     */
    public double distanceSquared(Point p1) {
        Point p3 =new Point(xyz.subtract(p1.xyz));
        Double3 DistanceAsPoint = p3.xyz.product(p3.xyz);
        return  (DistanceAsPoint.d1 + DistanceAsPoint.d2 + DistanceAsPoint.d3);
        //return 14;
    }

    /**
     *
     * @param p1
     * @return
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}
