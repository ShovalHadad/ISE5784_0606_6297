package primitives;

/**
 * Vector class
 */
public class Vector extends Point {
    /**
     * constructor that gets 3 numbers and set the vector
     * @param x of vector
     * @param y of vector
     * @param z of vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (Util.isZero(x) && Util.isZero(y) && Util.isZero(z)) {
            throw new IllegalArgumentException("Cannot be zero vector (0,0,0)");
        }
    }

    /**
     * constructor that get a double3 type and set the vector
     * @param xyz double3
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot be zero vector (0,0,0)");
        }
    }

    /**
     * the function multiplied the vector by number (scalar)
     * @param scalar = double
     * @return Vector
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Calculates the length of the vector
     * @return double
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Calculates the length squared of the vector -> a1^2 + a2^2 + a3^2
     * @return double
     */
    public double lengthSquared() {
        return (xyz.d1 * xyz.d1) + (xyz.d2 * xyz.d2) + (xyz.d3 * xyz.d3);
    }

    /**
     * scalar multiplication => (x1 * x2)+(y1 * y2)+(z1 * z2)
     * @param v is the input Vector
     * @return double
     */
    public double dotProduct(Vector v) {
        return ((xyz.d1 * v.xyz.d1) + (xyz.d2 * v.xyz.d2) + (xyz.d3 * v.xyz.d3));
    }

    /**
     * vector multiplication:
     * Returns a new vector perpendicular to both vectors (current and incoming)
     * @param v is the input Vector
     * @return Vector
     */
    public Vector crossProduct(Vector v) {
        return new Vector(
                ((xyz.d2 * v.xyz.d3) - (xyz.d3 * v.xyz.d2)),
                ((xyz.d3 * v.xyz.d1) - (xyz.d1 * v.xyz.d3)),
                ((xyz.d1 * v.xyz.d2) - (xyz.d2 * v.xyz.d1)));
    }

    /**
     * returns the normalize vector
     * @return Vector
     */
    public Vector normalize() {
        double length = length();
        return new Vector(xyz.reduce(length));
    }

    /**
     * add a vector to a vector
     * @param vector input
     * @return vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector && super.equals(obj);
    }

    @Override
    public String toString() {
        return "\nVector " + xyz + "= (" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
    }
}
