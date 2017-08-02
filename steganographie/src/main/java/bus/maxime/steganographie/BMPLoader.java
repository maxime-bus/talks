package bus.maxime.steganographie;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maxime on 02/08/17.
 */
public class BMPLoader {

    public static void loadBmpFile(String classpath) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpath);

        readFileHeader(is);
        readImageHeader(is);
    }

    private static void readFileHeader(InputStream is) throws IOException {
        byte[] type = new byte[2];
        is.read(type);

        byte[] size = new byte[4];
        is.read(size);

        //skip unused fields
        is.skip(4);

        byte[] offset = new byte[4];
        is.read(offset);

        System.out.println("type : " + new String(type));
        System.out.println("size : " + fromFourUnsignedBytesToInt(size));
    }

    private static void readImageHeader(InputStream is) {

    }

    private static int fromFourUnsignedBytesToInt(byte[] bytes) {
        return (bytes[3] << 24 & 0xFFFFFFFF)
                | (bytes[2] << 16 & 0xFFFFFF)
                | (bytes[1] << 8 & 0xFFFF)
                | bytes[0] & 0xFF;
    }
}
