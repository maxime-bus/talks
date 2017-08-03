package bus.maxime.steganographie;

public class RgbPixel {

    private final byte red;
    private final byte green;
    private final byte blue;

    public RgbPixel(byte red, byte green, byte blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static RgbPixel fromInteger(int pixelAsInteger) {
        byte[] channels = intToByteArray(pixelAsInteger);

        return new RgbPixel(channels[0], channels[1], channels[2]);
    }

    public RgbPixel hidePixel(RgbPixel rgbPixelToHide, int depth) {
        byte red = (byte) (this.red & (rgbPixelToHide.red & 0b0000001));
        byte green = (byte) (this.green & (rgbPixelToHide.green & 0b00000001));
        byte blue = (byte) (this.blue & (rgbPixelToHide.blue & 0b00000001));

        return new RgbPixel(red, green, blue);
    }

    public int toInteger() {
        return fromFourUnsignedBytesToInt(new byte[]{this.red, this.green, this.blue, Byte.MAX_VALUE});
    }


    private static int fromFourUnsignedBytesToInt(byte[] bytes) {
        return (bytes[3] << 24 & ) | (bytes[2] << 16 & 0xFFFFFF) | (bytes[1] << 8 & 0xFFFF) | bytes[0] & 0xFF;
    }

    @Override
    public String toString() {
        return "RgbPixel{" +
                "red=" + String.format("0x%02x", red) +
                ", green=" + String.format("0x%02x", green) +
                ", blue=" + String.format("0x%02x", blue) +
                '}';
    }

    private static byte[] intToByteArray(int integer) {
        byte[] bytes = new byte[4];

        bytes[3] = (byte) (integer >>> 24);
        bytes[2] = (byte) (integer >>> 16);
        bytes[1] = (byte) (integer >>> 8);
        bytes[0] = (byte) integer;

        return bytes;
    }
}
