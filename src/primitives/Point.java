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


    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public double distanceSquared(Point p1) {
        Point p3 = new Point(xyz.subtract(p1.xyz));
        Double3 DistanceAsPoint = p3.xyz.product(p3.xyz);
        return (DistanceAsPoint.d1 + DistanceAsPoint.d2 + DistanceAsPoint.d3);
        //return 14;
    }


    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }


    // Assuming existing correct implementation of constructor and xyz field
    public Vector subtract(Point other) {
        Double3 result = this.xyz.subtract(other.xyz);
        if (result.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Resulting vector cannot be zero");
        }
        return new Vector(result);
    }

    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return this.xyz.equals(point.xyz);
    }

    @Override
    public String toString() {
        return "(" + xyz.d1 + "," + xyz.d2 + "," + xyz.d3 + ")";
    }
}