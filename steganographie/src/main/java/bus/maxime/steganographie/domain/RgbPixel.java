package bus.maxime.steganographie.domain;

public class RgbPixel {

    private int alpha;
    private int red;
    private int green;
    private int blue;

    public RgbPixel(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static RgbPixel fromInteger(int color) {
        int blue = color & 0xFF;
        int green = (color & 0xFF00) >> 8;
        int red = (color & 0xFF0000) >> 16;
        int alpha = (color & 0xFF000000) >>> 24;

        return new RgbPixel(alpha, red, green, blue);
    }

    public RgbPixel hidePixel(RgbPixel rgbPixelToHide, int depth) {
        int red = (this.red >> depth) << depth | (rgbPixelToHide.red >> (8 - depth));
        int green = (this.green >> depth) << depth | (rgbPixelToHide.green >> (8 - depth));
        int blue = (this.blue >> depth) << depth | (rgbPixelToHide.blue >> (8 - depth));

        return new RgbPixel(alpha, red, green, blue);
    }

    public RgbPixel extract(int depth) {
        int mask = getMask(depth);
        int red = (this.red & mask) << (8 - depth);
        int green = (this.green & mask) << (8 - depth);
        int blue = (this.blue & mask) << (8 - depth);

        return new RgbPixel(alpha, red, green, blue);
    }

    public int toInteger() {
        return blue + (green << 8) + (red << 16) + (alpha << 24);
    }

    public int getAlpha() {
        return alpha;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    private int getMask(int numberOfBitsSet) {
        int result = 1;

        for (int i = 1; i < numberOfBitsSet; i++) {
            result = (result << 1) | 1;
        }

        return result;
    }

    @Override
    public String toString() {
        return "RgbPixel{" +
                "red=" + String.format("0x%02x", red) +
                ", green=" + String.format("0x%02x", green) +
                ", blue=" + String.format("0x%02x", blue) +
                '}';
    }
}
