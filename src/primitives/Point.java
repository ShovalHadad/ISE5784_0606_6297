package primitives;

/**
 * Point class
 */
public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);

    final Double3 xyz;

    /**
     * constructor that gets 3 numbers and set the point
     * @param x of point
     * @param y of point
     * @param z of point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * constructor that get a Double3 type and set the point
     * @param xyz double3
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Calculates the distance squared -> |ba|^2 = (a1-b1)^2+(a2-b2)^2+(a3-b3)^2
     * @param point input
     * @return double
     */
    public double distanceSquared(Point point) {
        return (((this.xyz.d1 - point.xyz.d1)*(this.xyz.d1 - point.xyz.d1))
                + ((this.xyz.d2 - point.xyz.d2)*(this.xyz.d2 - point.xyz.d2))
                + ((this.xyz.d3 - point.xyz.d3)*(this.xyz.d3 - point.xyz.d3)));
    }

    /**
     * Calculates the distance between 2 points
     * @param point input
     * @return double
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Calculates the vector -> ab = (b1-a1, b2-a2, b3-a3)
     * @param point input
     * @return vector
     */
    public Vector subtract(Point point) {
        return new Vector(this.xyz.subtract(point.xyz));
    }

    /**
     * add a point to a vector
     * @param vector input
     * @return point
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * returns x
     * @return double
     */
    public double getX() {
        return this.xyz.d1;
    }

    /**
     * returns y
     * @return double
     */
    public double getY() {
        return this.xyz.d2;
    }

    /**
     * returns z
     * @return double
     */
    public double getZ() {
        return this.xyz.d3;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return this.xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "\nPoint " + xyz + "= (" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
    }
}