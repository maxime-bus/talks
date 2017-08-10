package bus.maxime.steganographie;

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

    public RgbPixel hidePixel(RgbPixel rgbPixelToHide) {
        int red = (this.red >> 1) << 1 | (rgbPixelToHide.red >> 7);
        int green = (this.green >> 1) << 1 | (rgbPixelToHide.green >> 7);
        int blue = (this.blue >> 1) << 1 | (rgbPixelToHide.blue >> 7);

        return new RgbPixel(alpha, red, green, blue);
    }

    public RgbPixel unhide() {
        int red = (this.red & 0x01) << 7;
        int green = (this.green & 0x01) << 7;
        int blue = (this.blue & 0x01) << 7;

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

    @Override
    public String toString() {
        return "RgbPixel{" +
                "red=" + String.format("0x%02x", red) +
                ", green=" + String.format("0x%02x", green) +
                ", blue=" + String.format("0x%02x", blue) +
                '}';
    }
}
