package unscrambler.implementations;

import unscrambler.domain.Edge;
import unscrambler.domain.ImageEdge;
import unscrambler.domain.PixelColor;

import java.awt.image.BufferedImage;

/**
 * User: oliverradwan
 */
public final class ImageEdgeCollapsingImpl extends ImageEdge {

    private PixelColor[] edgeColors;

    public enum AggregationMethod {
        SUM,
        GEO_MEAN
    }

    private final AggregationMethod aggregationMethod;

    public ImageEdgeCollapsingImpl(BufferedImage image, Edge edge, AggregationMethod aggregationMethod) {
        super(edge, image);

        this.aggregationMethod = aggregationMethod;

        int distanceToCover;
        int yInc = 0;
        int xInc = 0;

        int yStart = 0;
        int xStart = 0;

        if (edge == Edge.BOTTOM || edge == Edge.TOP) {
            distanceToCover = image.getWidth();
            xInc = 1;
        } else {
            distanceToCover = image.getHeight();
            yInc = 1;
        }
        if (edge == Edge.BOTTOM) {
            yStart = image.getHeight() - 1;
        } else if (edge == Edge.RIGHT) {
            xStart = image.getWidth() - 1;
        }

        int x = xStart;
        int y = yStart;

        PixelColor[] ec = new PixelColor[distanceToCover];

        for (int i = 0; i < distanceToCover; i++) {
            int pixelRGB = image.getRGB(x, y);
            ec[i] = new PixelColor(pixelRGB);
            x += xInc;
            y += yInc;
        }
        edgeColors = ec;
    }

    public PixelColor[] getEdgeColors() {
        return edgeColors;
    }

    @Override
    public double compareEdgeImpl(ImageEdge other) {
        if (other instanceof ImageEdgeCollapsingImpl) {
            if (aggregationMethod == AggregationMethod.GEO_MEAN) {
                return geometricMeanMethod((ImageEdgeCollapsingImpl) other);
            } else if (aggregationMethod == AggregationMethod.SUM) {
                return sumMethod((ImageEdgeCollapsingImpl) other);
            }
        }
        throw new IllegalStateException("Cannot compare non edge collapsing unscrambler.implementations...");
    }

    private double sumMethod(ImageEdgeCollapsingImpl other) {
        double totalDistance = 0;
        for (int i = 0; i < getEdgeColors().length; i++) {
            totalDistance += getEdgeColors()[i].euclideanDistanceTo(other.getEdgeColors()[i]);
        }
        return totalDistance;
    }

    private double geometricMeanMethod(ImageEdgeCollapsingImpl other) {
        int n = getEdgeColors().length;

        double product = 1.;
        int count = 0;

        for (int i = 0; i < n; i++) {
            double d = getEdgeColors()[i].euclideanDistanceTo(other.getEdgeColors()[i]);
            product = product * d;
            count++;
        }

        return Math.pow(product, 1. / (double) count);
    }
}
