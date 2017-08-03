package bus.maxime.steganographie;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class only works for BMP files with color depth 24bits.
 */
public class BmpLoader {

    public static void loadBmpFile(String classpath) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpath);

        BmpFile bmpFile = new BmpFile();

        readFileHeader(is, bmpFile);
        readImageHeader(is, bmpFile);
        readPixelData(is, bmpFile);

        System.out.println("type : " + new String(bmpFile.getType()));
        System.out.println("size : " + fromFourUnsignedBytesToInt(bmpFile.getSize()));
        System.out.println("address : " + fromFourUnsignedBytesToInt(bmpFile.getOffset()));

        System.out.println("Image header size : " + fromFourUnsignedBytesToInt(bmpFile.getImageHeaderSize()));
        System.out.println("width : " + fromFourUnsignedBytesToInt(bmpFile.getWidthInPixels()));
        System.out.println("height : " + fromFourUnsignedBytesToInt(bmpFile.getHeightInPixels()));
        System.out.println("color planes : " + fromTwoUnsignedBytesToInt(bmpFile.getColorPlanes()));
        System.out.println("color depth : " + fromTwoUnsignedBytesToInt(bmpFile.getDepth()));
        System.out.println("compression method: " + deduceCompressionMethod(bmpFile.getCompressionMethod()));
        System.out.println("horizontal ppm: " + fromFourUnsignedBytesToInt(bmpFile.getHorizontalPixelsPerMeter()));
        System.out.println("vertical ppm: " + fromFourUnsignedBytesToInt(bmpFile.getVerticalPixelsPerMeter()));
        System.out.println("colorMap entries : " + fromFourUnsignedBytesToInt(bmpFile.getColorMapEntries()));
        System.out.println("important colors number: " + fromFourUnsignedBytesToInt(bmpFile.getImportantColors()));

        List<RgbPixel> pixels = bmpFile.getPixels();
        for (RgbPixel pixel : pixels) {

            System.out.println("pixels: " + pixel);
        }

        is.close();
    }

    private static void readFileHeader(InputStream is, BmpFile bmpFile) throws IOException {
        byte[] type = new byte[2];
        is.read(type);

        byte[] size = new byte[4];
        is.read(size);

        // skip unused fields
        is.skip(4);

        byte[] offset = new byte[4];
        is.read(offset);

        bmpFile.setType(type);
        bmpFile.setSize(size);
        bmpFile.setOffset(offset);
    }

    private static void readImageHeader(InputStream is, BmpFile bmpFile) throws IOException {
        byte[] imageHeaderSize = new byte[4];
        is.read(imageHeaderSize);

        byte[] widthInPixels = new byte[4];
        is.read(widthInPixels);

        byte[] heightInPixels = new byte[4];
        is.read(heightInPixels);

        byte[] colorPlanes = new byte[2];
        is.read(colorPlanes);

        byte[] depth = new byte[2];
        is.read(depth);

        byte[] compressionMethod = new byte[4];
        is.read(compressionMethod);

        byte[] rawDataSize = new byte[4];
        is.read(rawDataSize);

        byte[] horizontalPpm = new byte[4];
        is.read(horizontalPpm);

        byte[] verticalPpm = new byte[4];
        is.read(verticalPpm);

        byte[] colorMapEntries = new byte[4];
        is.read(colorMapEntries);

        byte[] importantColors = new byte[4];
        is.read(importantColors);

        bmpFile.setImageHeaderSize(imageHeaderSize);
        bmpFile.setWidthInPixels(widthInPixels);
        bmpFile.setHeightInPixels(heightInPixels);
        bmpFile.setColorPlanes(colorPlanes);
        bmpFile.setDepth(depth);
        bmpFile.setCompressionMethod(compressionMethod);
        bmpFile.setRawDataSize(rawDataSize);
        bmpFile.setHorizontalPixelsPerMeter(horizontalPpm);
        bmpFile.setVerticalPixelsPerMeter(verticalPpm);
        bmpFile.setColorMapEntries(colorMapEntries);
        bmpFile.setImportantColors(importantColors);
    }

    private static void readPixelData(InputStream is, BmpFile bmpFile) throws IOException {
        byte[] widthInPixels = bmpFile.getWidthInPixels();
        byte[] heightInPixels = bmpFile.getHeightInPixels();

        int pixelsNumber = fromTwoUnsignedBytesToInt(widthInPixels) * fromTwoUnsignedBytesToInt(heightInPixels);

        List<RgbPixel> pixels = new ArrayList<>();

        for (int i = 0; i < pixelsNumber; i++) {
            byte[] colors = new byte[3];
            is.read(colors);

            pixels.add(new RgbPixel(colors[0], colors[1], colors[2]));
        }

        bmpFile.setPixels(pixels);
    }

    private static int fromFourUnsignedBytesToInt(byte[] bytes) {
        return (bytes[3] << 24 & 0xFFFFFFFF) | (bytes[2] << 16 & 0xFFFFFF) | (bytes[1] << 8 & 0xFFFF) | bytes[0] & 0xFF;
    }

    private static int fromTwoUnsignedBytesToInt(byte[] bytes) {
        return (bytes[1] << 8 & 0xFFFF) | bytes[0] & 0xFF;
    }

    private static String deduceCompressionMethod(byte[] bytes) {
        int i = fromFourUnsignedBytesToInt(bytes);

        if (i == 0) {
            return "Uncompressed";
        }
        if (i == 1) {
            return "RLE-8";
        }
        if (i == 2) {
            return "RLE-4";
        }
        if (i == 3) {
            return "Bitfields";
        }

        return "unknown";
    }
}
