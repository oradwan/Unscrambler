package unscrambler.composers;

import unscrambler.domain.SubImage;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * User: oliverradwan
 * Date: 3/1/13
 */
public interface CompositionAlgorithm {
    public BufferedImage ComposeSlicedImage(Set<SubImage> imagesToCompose);
}
