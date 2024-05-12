package primitives;

/**
 * Point class
 */
public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);

    final Double3 xyz;

    /**
     * constructor that gets 3 numbers and set the point
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * constructor that get a Double3 type and set the point
     * @param xyz
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Calculates the distance squared -> |ba|^2 = (a1-b1)+(a2-b2)+(a3-b3)
     * @param point
     * @return double
     */
    public double distanceSquared(Point point) {
        return (((this.xyz.d1 - point.xyz.d1)*(this.xyz.d1 - point.xyz.d1)) + ((this.xyz.d2 - point.xyz.d2)*(this.xyz.d2 - point.xyz.d2)) + ((this.xyz.d3 - point.xyz.d3)*(this.xyz.d3 - point.xyz.d3)));
    }

    /**
     * Calculates the distance between 2 points
     * @param point
     * @return double
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Calculates the vector -> ab = (b1-a1, b2-a2, b3-a3)
     * @param point
     * @return vector
     */
    public Vector subtract(Point point) {
        if ((this.xyz.subtract(point.xyz)).equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Resulting vector cannot be zero");
        }
        return new Vector(this.xyz.subtract(point.xyz));
    }

    /**
     * add a point to a vector
     * @param vector
     * @return point
     */
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
        return "\nPoint " + xyz + "= (" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
    }
}