package primitives;

public class Ray {
    public Vector vector;
    public Point point;

    public Ray(Vector v, Point p) {
        if((v.xyz.d1 + v.xyz.d2 +v.xyz.d3) != 1){
            vector = v.normalize();
        }
        else vector = v;
        point = p;
    }
}