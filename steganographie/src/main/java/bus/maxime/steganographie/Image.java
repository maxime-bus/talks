package bus.maxime.steganographie;

import java.util.List;

public class Image {

    private final int width;
    private final int height;
    private final List<Pixel> pixels;

    public Image(int width, int height, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }
}
