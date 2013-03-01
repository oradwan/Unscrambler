package unscrambler.domain;

/**
 * Created with IntelliJ IDEA.
 * User: oliverradwan
 */
public final class PixelColor {
    private final int r;
    private final int g;
    private final int b;

    public PixelColor(int rgb){
        r = (rgb >> 16) & 0xFF;
        g = (rgb >> 8) & 0xFF;
        b = rgb & 0xFF;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public double euclideanDistanceTo(PixelColor other){
         return Math.sqrt(Math.pow(r - other.getR(), 2) + Math.pow(g - other.getG(), 2) + Math.pow(b - other.getB(), 2));
    }
}
