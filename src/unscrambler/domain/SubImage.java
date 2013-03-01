package unscrambler.domain;

import unscrambler.implementations.ImageEdgeCollapsingImpl;
import unscrambler.ConfigValues;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: oliverradwan
 */
public final class SubImage {
    private final BufferedImage image;

    public ImageEdge leftEdge, rightEdge, topEdge, bottomEdge;

    private final UUID id;

    public SubImage(BufferedImage image) {
        this.image = image;
        initEdges();
        id = UUID.randomUUID();
    }

    private void initEdges() {
        //TODO : factor this out into some factory or something
        leftEdge = new ImageEdgeCollapsingImpl(image, Edge.LEFT, ConfigValues.AGG_METHOD);
        rightEdge = new ImageEdgeCollapsingImpl(image, Edge.RIGHT, ConfigValues.AGG_METHOD);
        topEdge = new ImageEdgeCollapsingImpl(image, Edge.TOP, ConfigValues.AGG_METHOD);
        bottomEdge = new ImageEdgeCollapsingImpl(image, Edge.BOTTOM, ConfigValues.AGG_METHOD);
    }

    public BufferedImage getImage() {
        return image;
    }

    public UUID getId() {
        return id;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubImage subImage = (SubImage) o;

        if (id != null ? !id.equals(subImage.id) : subImage.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
