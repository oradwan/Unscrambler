package unscrambler;

import unscrambler.composers.CompositionAlgorithm;
import unscrambler.composers.RecursiveComposer;
import unscrambler.domain.SubImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * User: oliverradwan
 * Main entrance point for unscrambling
 */
public final class Unscrambler {
    private static final CompositionAlgorithm innerAlgorithm = new RecursiveComposer();

    private static final String BASE_FILE_PATH = "/Users/oliverradwan/Desktop/";

    private static final String[] INPUT_IMAGE_NAMES =  {"fcc097e74e5918387b11","492e3a174081f2d6c1da","1bb5e47e4110ba9c5f2a"};

    public static void main(String[] args) {
        for(String fileName : INPUT_IMAGE_NAMES){
            BufferedImage image = readImage(BASE_FILE_PATH + fileName + ".jpg");
            BufferedImage composedImage = innerAlgorithm.ComposeSlicedImage(sliceImage(image));
            writeImage(BASE_FILE_PATH + fileName + "_unscrambled.png", composedImage);
        }
    }

    public static Set<SubImage> sliceImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int xOffset = (width / ConfigValues.NUM_COLS_IN_IMAGE);
        int yOffset = (height / ConfigValues.NUM_ROWS_IN_IMAGE);

        Set<SubImage> sliced = new HashSet<SubImage>();

        for (int i = 0; i < ConfigValues.NUM_COLS_IN_IMAGE; i++) {
            for (int j = 0; j < ConfigValues.NUM_ROWS_IN_IMAGE; j++) {
                sliced.add(new SubImage(image.getSubimage(i * xOffset, j * yOffset, xOffset, yOffset)));
            }
        }
        return sliced;
    }

    private static BufferedImage readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeImage(String filePath, BufferedImage image){
        try{
            ImageIO.write(image, "PNG", new File(filePath));
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

}
