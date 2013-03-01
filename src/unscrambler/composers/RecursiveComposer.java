package unscrambler.composers;

import unscrambler.ConfigValues;
import unscrambler.domain.ImageTile;
import unscrambler.domain.SubImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * User: oliverradwan
 * first cut of a recursive composer
 */
public class RecursiveComposer implements CompositionAlgorithm {
    @Override
    public BufferedImage ComposeSlicedImage(Set<SubImage> subImages) {
        return outputImageTiles(composeImage(subImages), ConfigValues.IMAGE_DIMENSION);
    }

    private ImageTile composeImage(Set<SubImage> subImages) {
        SubImage firstImage = subImages.iterator().next();
        subImages.remove(firstImage);

        ImageTile start = new ImageTile(firstImage);

        while (subImages.size() > 0) {
            ImageTile imageTile = start.attachBestFit(subImages);
            start = imageTile;
            subImages.remove(imageTile.getImage());
        }
        return start;
    }

    private BufferedImage outputImageTiles(ImageTile tile, Dimension originalDimension) {
        BufferedImage image = new BufferedImage((int) originalDimension.getWidth() * 4,
                (int) originalDimension.getHeight() * 4, BufferedImage.TYPE_INT_RGB);
        return recursiveTileDraw(tile, image, (int) originalDimension.getWidth() * 2, (int) originalDimension.getHeight() * 2,
                (int) originalDimension.getWidth(), (int) originalDimension.getHeight());
    }

    private BufferedImage recursiveTileDraw(ImageTile image, BufferedImage drawOnMe, int x, int y, int width, int height) {
        if (image.hasBeenDrawn()) {
            return drawOnMe;
        }

        drawOnMe.getGraphics().drawImage(image.getImage().getImage(), x, y, null);
        image.setHasBeenDrawn(true);
        //recursive calls
        if (image.getTopTile() != null) {
            drawOnMe = recursiveTileDraw(image.getTopTile(), drawOnMe, x, y - (height / ConfigValues.NUM_ROWS_IN_IMAGE), width, height);
        }
        if (image.getBottomTile() != null) {
            drawOnMe = recursiveTileDraw(image.getBottomTile(), drawOnMe, x, y + (height / ConfigValues.NUM_ROWS_IN_IMAGE), width, height);
        }
        if (image.getLeftTile() != null) {
            drawOnMe = recursiveTileDraw(image.getLeftTile(), drawOnMe, x - (width / ConfigValues.NUM_COLS_IN_IMAGE), y, width, height);
        }
        if (image.getRightTile() != null) {
            drawOnMe = recursiveTileDraw(image.getRightTile(), drawOnMe, x + (width / ConfigValues.NUM_COLS_IN_IMAGE), y, width, height);
        }
        return drawOnMe;
    }
}
