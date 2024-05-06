package primitives;

public class Vector extends Point{
    public Vector(double x, double y, double z){
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("can not be zero vector (0,0,0)");
        super(x, y, z);
    }

    public Vector(Double3 double3) {
        if (double3 == Double3.ZERO)
            throw new IllegalArgumentException("can not be zero vector (0,0,0)");
        super(double3);
    }

    /**
     * the function multiplied the vector by number (scalar)
     * @param scalar
     * @return vector
     */
    public Vector scale(double scalar){
        return new Vector(xyz.scale(scalar));
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return (xyz.d1* xyz.d1)+(xyz.d2* xyz.d2)+(xyz.d3* xyz.d3);
    }

    /**
     * scalar multiplication => (x1 * x2)+(y1 * y2)+(z1 * z2)
     * @param v is the input vector
     * @return double
     */
    public double dotProduct(Vector v) {
        return ((xyz.d1*v.xyz.d1)+(xyz.d2*v.xyz.d2)+(xyz.d3*v.xyz.d3));
    }

    /**
     * Vector multiplication:
     * Returns a new vector perpendicular to both vectors (current and incoming)
     * @param v is the input vector
     * @return vector
     */
    public Vector crossProduct(Vector v) {
        return new Vector(((xyz.d2*v.xyz.d3)-(xyz.d3*v.xyz.d2)),
                ((xyz.d3*v.xyz.d1)-(xyz.d1*v.xyz.d3)),
                ((xyz.d1*v.xyz.d2)-(xyz.d2*v.xyz.d1)));
    }

    public Vector normalize() {
        double length = length();
        return new Vector(((xyz.d1)/length),((xyz.d2)/length),((xyz.d3)/length));
    }
}
