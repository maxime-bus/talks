package bus.maxime.steganographie;


import org.junit.Test;

/**
 * Created by maxime on 02/08/17.
 */
public class BMPLoaderTest {

    @Test
    public void name() throws Exception {
        BMPLoader.loadBmpFile("salut.bmp");
    }
}