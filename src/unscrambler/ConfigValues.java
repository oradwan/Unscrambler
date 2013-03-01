package unscrambler;

import unscrambler.implementations.ImageEdgeCollapsingImpl;

import java.awt.*;


public class ConfigValues {
    public static final ImageEdgeCollapsingImpl.AggregationMethod AGG_METHOD = ImageEdgeCollapsingImpl.AggregationMethod.GEO_MEAN;
    public static final int NUM_ROWS_IN_IMAGE = 4;
    public static final int NUM_COLS_IN_IMAGE = 4;
    public static final Dimension IMAGE_DIMENSION = new Dimension(240,240);
}
