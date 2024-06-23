package primitives;

public class Material {
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    public int nShininess = 0;

    /**
     * set kD when Double3 input
     * @param kd Double3
     * @return this
     */
    public Material setKd(Double3 kd){
        kD = kd;
        return this;
    }

    /**
     * set kD when Double input
     * @param kd Double
     * @return this
     */
    public Material setKd(double kd){
        kD = new Double3(kd);
        return this;
    }

    /**
     * set kS when Double3 input
     * @param ks Double3
     * @return this
     */
    public Material setKs(Double3 ks){
        kS = ks;
        return this;
    }

    /**
     * set kS when Double input
     * @param ks Double
     * @return this
     */
    public Material setKs(double ks){
        kS = new Double3(ks);
        return this;
    }

    /**
     * set nShininess
     * @param nShininess int
     * @return this
     */
    public Material setShininess(int nShininess){
       this.nShininess = nShininess;
        return this;
    }

}
