package unscrambler.domain;

import java.util.Collection;
import java.util.UUID;

/**
 * User: oliverradwan
 * Date: 2/28/13
 * <p/>
 * Represent a sliced part of the image in the context of where it's being drawn.
 */
public final class ImageTile {
    //TODO: clean up this bad linked implementation

    private SubImage thisImage;

    // The bordering image tiles.
    private ImageTile topTile, leftTile, rightTile, bottomTile;

    private final UUID id;

    public ImageTile(SubImage thisImage) {
        this.thisImage = thisImage;
        id = thisImage.getId();
    }

    private static final class TileAssignationValue {
        final SubImage image;
        final double value;

        private TileAssignationValue(SubImage image, double value) {
            this.image = image;
            this.value = value;
        }

        public static TileAssignationValue tav(SubImage image, double value) {
            return new TileAssignationValue(image, value);
        }

        public SubImage getImage() {
            return image;
        }

        public double getValue() {
            return value;
        }
    }

    //TODO --> this is ugly and bad
    public ImageTile attachBestFit(Collection<SubImage> otherImages) {
        //current winning images
        SubImage bestTop = null, bestBottom = null, bestLeft = null, bestRight = null;

        //current winning fits
        double d_t = Double.MAX_VALUE;
        double d_b = Double.MAX_VALUE;
        double d_l = Double.MAX_VALUE;
        double d_r = Double.MAX_VALUE;

        double c_t, c_b, c_l, c_r;

        for (SubImage img : otherImages) {
            c_b = img.bottomEdge.compareEdge(thisImage.topEdge);
            if (c_b < d_b) {
                bestBottom = img;
                d_b = c_b;
            }
            c_t = img.topEdge.compareEdge(thisImage.bottomEdge);
            if (c_t < d_t) {
                bestTop = img;
                d_t = c_t;
            }
            c_l = img.leftEdge.compareEdge(thisImage.rightEdge);
            if (c_l < d_l) {
                bestLeft = img;
                d_l = c_l;
            }
            c_r = img.rightEdge.compareEdge(thisImage.leftEdge);
            if (c_r < d_r) {
                bestRight = img;
                d_r = c_r;
            }
        }

        ImageTile tile;

        if (d_t < d_b && d_t < d_l && d_t < d_r) {
            tile = new ImageTile(bestTop);
            tile.setBottomTile(this);
            setTopTile(tile);
        } else if (d_b < d_t && d_b < d_l && d_b < d_r) {
            tile = new ImageTile(bestBottom);
            tile.setTopTile(this);
            setBottomTile(tile);
        } else if (d_l < d_b && d_l < d_t && d_l < d_r) {
            tile = new ImageTile(bestLeft);
            tile.setRightTile(this);
            setLeftTile(tile);
        } else {
            tile = new ImageTile(bestRight);
            tile.setLeftTile(this);
            setRightTile(tile);
        }
        return tile;
    }


    // Getters and Setters from here on down.

    public ImageTile getTopTile() {
        return topTile;
    }

    public void setTopTile(ImageTile topTile) {
        this.topTile = topTile;
    }

    public ImageTile getLeftTile() {
        return leftTile;
    }

    public void setLeftTile(ImageTile leftTile) {
        this.leftTile = leftTile;
    }

    public ImageTile getRightTile() {
        return rightTile;
    }

    public void setRightTile(ImageTile rightTile) {
        this.rightTile = rightTile;
    }

    public ImageTile getBottomTile() {
        return bottomTile;
    }

    public void setBottomTile(ImageTile bottomTile) {
        this.bottomTile = bottomTile;
    }

    //this is hacky and used by the RecursiveComposer
    private boolean hasDrawn = false;

    public boolean hasBeenDrawn() {
        return hasDrawn;
    }

    public void setHasBeenDrawn(boolean hasDrawn) {
        this.hasDrawn = hasDrawn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageTile imageTile = (ImageTile) o;

        if (!id.equals(imageTile.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public SubImage getImage() {
        return thisImage;
    }
}
