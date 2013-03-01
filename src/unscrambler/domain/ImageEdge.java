package unscrambler.domain;

import java.awt.image.BufferedImage;

/**
 * User: oliverradwan
 * Represent the edge of an image, can compare to other image edges.
 */
public abstract class ImageEdge {
    protected final Edge edge;
    protected final BufferedImage image;

    protected ImageEdge(Edge edge, BufferedImage image) {
        this.edge = edge;
        this.image = image;
    }

    public double compareEdge(ImageEdge other) {
        //First validate comparable edge
        if ((getEdge() == Edge.TOP && other.getEdge() != Edge.BOTTOM) ||
                (getEdge() == Edge.BOTTOM && other.getEdge() != Edge.TOP) ||
                (getEdge() == Edge.LEFT && other.getEdge() != Edge.RIGHT) ||
                (getEdge() == Edge.RIGHT && other.getEdge() != Edge.LEFT)) {
            throw new IllegalStateException("Cannot compare these two edges!");
        }

        return compareEdgeImpl(other);
    }

    abstract public double compareEdgeImpl(ImageEdge other);

    public BufferedImage getImage() {
        return image;
    }

    public Edge getEdge() {
        return edge;
    }
}
