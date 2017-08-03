package bus.maxime.steganographie;

import java.util.List;

public class BmpFile {

    private byte[] type;
    private byte[] size;
    private byte[] offset;
    private byte[] imageHeaderSize;
    private byte[] widthInPixels;
    private byte[] heightInPixels;
    private byte[] colorPlanes;
    private byte[] depth;
    private byte[] compressionMethod;
    private byte[] rawDataSize;
    private byte[] horizontalPixelsPerMeter;
    private byte[] verticalPixelsPerMeter;
    private byte[] colorMapEntries;
    private byte[] importantColors;
    private List<RgbPixel> pixels;

    public void setType(byte[] type) {
        this.type = type;
    }

    public byte[] getType() {
        return type;
    }

    public void setSize(byte[] size) {
        this.size = size;
    }

    public byte[] getSize() {
        return size;
    }

    public void setOffset(byte[] offset) {
        this.offset = offset;
    }

    public byte[] getOffset() {
        return offset;
    }

    public void setImageHeaderSize(byte[] imageHeaderSize) {
        this.imageHeaderSize = imageHeaderSize;
    }

    public byte[] getImageHeaderSize() {
        return imageHeaderSize;
    }

    public void setWidthInPixels(byte[] widthInPixels) {
        this.widthInPixels = widthInPixels;
    }

    public byte[] getWidthInPixels() {
        return widthInPixels;
    }

    public void setHeightInPixels(byte[] heightInPixels) {
        this.heightInPixels = heightInPixels;
    }

    public byte[] getHeightInPixels() {
        return heightInPixels;
    }

    public void setColorPlanes(byte[] colorPlanes) {
        this.colorPlanes = colorPlanes;
    }

    public byte[] getColorPlanes() {
        return colorPlanes;
    }

    public void setDepth(byte[] depth) {
        this.depth = depth;
    }

    public byte[] getDepth() {
        return depth;
    }

    public void setCompressionMethod(byte[] compressionMethod) {
        this.compressionMethod = compressionMethod;
    }

    public byte[] getCompressionMethod() {
        return compressionMethod;
    }

    public void setRawDataSize(byte[] rawDataSize) {
        this.rawDataSize = rawDataSize;
    }

    public byte[] getRawDataSize() {
        return rawDataSize;
    }

    public void setHorizontalPixelsPerMeter(byte[] horizontalPixelsPerMeter) {
        this.horizontalPixelsPerMeter = horizontalPixelsPerMeter;
    }

    public byte[] getHorizontalPixelsPerMeter() {
        return horizontalPixelsPerMeter;
    }

    public void setVerticalPixelsPerMeter(byte[] verticalPixelsPerMeter) {
        this.verticalPixelsPerMeter = verticalPixelsPerMeter;
    }

    public byte[] getVerticalPixelsPerMeter() {
        return verticalPixelsPerMeter;
    }

    public void setColorMapEntries(byte[] colorMapEntries) {
        this.colorMapEntries = colorMapEntries;
    }

    public byte[] getColorMapEntries() {
        return colorMapEntries;
    }

    public void setImportantColors(byte[] importantColors) {
        this.importantColors = importantColors;
    }

    public byte[] getImportantColors() {
        return importantColors;
    }

    public void setPixels(List<RgbPixel> pixels) {
        this.pixels = pixels;
    }

    public List<RgbPixel> getPixels() {
        return pixels;
    }
}
