package bus.maxime.steganographie;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RgbPixelTest {

    @Test
    public void i_should_extract_rgba_channels_from_integer() throws Exception {

        /*
         * BufferedImage.getRGB(int) returns an integer that contains R, G, B and Alpha
         * informations.
         *
         * Because an int is 32 bits (4 bytes) wide, the data is as follow :
         *
         *  MSB                          LSB
         *  | Alpha | Red | Green | Blue |
         *     4th    3rd    2nd    1st
         */

        int color = 0xFF123456;

        RgbPixel rgbPixel = RgbPixel.fromInteger(color);

        assertThat(rgbPixel.getAlpha()).as("alpha channel").isEqualTo(0xFF);
        assertThat(rgbPixel.getRed()).as("red channel").isEqualTo(0x12);
        assertThat(rgbPixel.getGreen()).as("green channel").isEqualTo(0x34);
        assertThat(rgbPixel.getBlue()).as("blue channel").isEqualTo(0x56);
    }

    @Test
    public void i_should_get_an_integer_from_a_rgba_pixel() throws Exception {
        int color = 0xFF123456;

        RgbPixel rgbPixel = RgbPixel.fromInteger(color);

        assertThat(rgbPixel.toInteger()).isEqualTo(color);
    }

    @Test
    public void i_can_hide_a_pixel_into_another() throws Exception {
        RgbPixel source = new RgbPixel(0xFF, 0xFE, 0xA1, 0xE5);
        RgbPixel toHide = new RgbPixel(0xFF, 0x80, 0x00, 0x36);

        RgbPixel finalPixel = source.hidePixel(toHide, 1);

        assertThat(finalPixel.getAlpha()).as("alpha channel").isEqualTo(0xFF);
        assertThat(finalPixel.getRed()).as("red channel").isEqualTo(0xFF);
        assertThat(finalPixel.getGreen()).as("green channel").isEqualTo(0xA0);
        assertThat(finalPixel.getBlue()).as("blue channel").isEqualTo(0xE4);
    }

    @Test
    public void i_can_extract_pixel() throws Exception {
        RgbPixel source = new RgbPixel(0xFF, 0xFF, 0xA0, 0xE4);

        RgbPixel unhidden = source.extract(1);

        assertThat(unhidden.getAlpha()).as("alpha channel").isEqualTo(0xFF);
        assertThat(unhidden.getRed()).as("red channel").isEqualTo(0x80);
        assertThat(unhidden.getGreen()).as("green channel").isEqualTo(0x00);
        assertThat(unhidden.getBlue()).as("blue channel").isEqualTo(0x00);
    }

    @Test
    public void i_can_extract_pixel_with_a_depth_greater_than_1() throws Exception {
        RgbPixel source = new RgbPixel(0xFF, 0xFF, 0xA0, 0xE4);

        RgbPixel unhidden = source.extract(2);

        assertThat(unhidden.getAlpha()).as("alpha channel").isEqualTo(0xFF);
        assertThat(unhidden.getRed()).as("red channel").isEqualTo(0xC0);
        assertThat(unhidden.getGreen()).as("green channel").isEqualTo(0x00);
        assertThat(unhidden.getBlue()).as("blue channel").isEqualTo(0x00);
    }
}
