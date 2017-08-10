package bus.maxime.steganographie;


import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class BmpLoaderTest {

    @Test
    public void hide() throws Exception {
        InputStream isTarget = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("source.bmp");

        InputStream isToHide = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("image_to_hide.bmp");

        BufferedImage imgTarget = ImageIO.read(isTarget);
        BufferedImage imgToHide = ImageIO.read(isToHide);

        BufferedImage outputImg = new BufferedImage(imgTarget.getWidth(), imgTarget.getHeight(), imgTarget.getType());

//        hide(imgTarget, imgToHide, outputImg);

        ImageIO.write(outputImg, "bmp", new File("/home/engineer/result.bmp"));
    }

    private void hide(Image imgTarget, Image imgToHide, WritableImage outputImg) {
        for (int i = 0; i < imgTarget.getWidth(); i++) {
            for (int j = 0; j < imgTarget.getHeight(); j++) {
                RgbPixel targetPixel = RgbPixel.fromInteger(imgTarget.getPixelReader().getArgb(i, j));
                RgbPixel pixelToHide = RgbPixel.fromInteger(imgToHide.getPixelReader().getArgb(i, j));
                RgbPixel finalPixel = targetPixel.hidePixel(pixelToHide, 1);

                outputImg.getPixelWriter().setArgb(i, j, finalPixel.toInteger());
            }
        }
    }

    @Test
    public void extract() throws Exception {
        BufferedImage image = ImageIO.read(new File("/home/engineer/result.bmp"));

        BufferedImage extracted = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        extract(image, extracted);

        ImageIO.write(extracted, "bmp", new File("/home/engineer/extracted.bmp"));
    }

    private void extract(BufferedImage image, BufferedImage extracted) {
        for (int i = 0; i < extracted.getWidth(); i++) {
            for (int j = 0; j < extracted.getHeight(); j++) {
                RgbPixel targetPixel = RgbPixel.fromInteger(image.getRGB(i, j));
                RgbPixel unhide = targetPixel.extract(1);

                extracted.setRGB(i, j, unhide.toInteger());
            }
        }
    }
}