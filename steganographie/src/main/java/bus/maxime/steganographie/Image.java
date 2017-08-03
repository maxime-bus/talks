package bus.maxime.steganographie;

import java.util.List;

public class Image {

    private final int width;
    private final int height;
    private final List<RgbPixel> rgbPixels;

    public Image(int width, int height, List<RgbPixel> rgbPixels) {
        this.width = width;
        this.height = height;
        this.rgbPixels = rgbPixels;
    }
}
