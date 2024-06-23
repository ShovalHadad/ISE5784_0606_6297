package lighting;

import primitives.*;


public class PointLight extends Light implements LightSource{
    protected Point position;
    private double kC=1,kL=0,kQ=0;

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;

    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

//    public PointLight setPosition(Point position) {
//        this.position = position;
//        return this;
//    }

 public PointLight(Color color, Point position,Vector direction){
        this.position = position;
        super(color);
 }

}
