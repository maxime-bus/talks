package bus.maxime.steganographie;


import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class BmpLoaderTest {

    @Test
    public void name() throws Exception {
        BmpLoader.loadBmpFile("salut.bmp");
    }

    @Test
    public void test() throws Exception {
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("salut.bmp");

        BufferedImage img = ImageIO.read(is);

        int rgb = img.getRGB(128, 64);

        System.out.format("%1$x\n", rgb);

        RgbPixel rgbPixel = RgbPixel.fromInteger(rgb);
        System.out.println(rgbPixel);

        System.out.println(rgb);
        System.out.println(rgbPixel.toInteger());
    }
}