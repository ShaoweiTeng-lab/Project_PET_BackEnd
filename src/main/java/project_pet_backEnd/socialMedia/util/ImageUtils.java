package project_pet_backEnd.socialMedia.util;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    //壓縮圖片
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(temp);
            outputStream.write(temp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    //解壓縮圖片

    public static byte[] deCompressFile(byte[] file) {
        Inflater inflater = new Inflater();
        inflater.setInput(file);

        ByteArrayOutputStream stream = new ByteArrayOutputStream(file.length);
        byte[] dataHolder = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int size = inflater.inflate(dataHolder);
                stream.write(dataHolder, 0, size);
            }

            stream.close();
        } catch (Exception e) {
            // do something
        }
        return stream.toByteArray();
    }


    /**
     * byte[]轉base 64
     */

    public static String base64Encode(byte[] byteArray) {
        if (byteArray == null)
            return null;
        return Base64.getEncoder().encodeToString(byteArray);
    }

    /**
     * base 64轉byte[]
     */
    public static byte[] base64Decode(String base64String) {
        if (base64String == null)
            return null;
        return Base64.getDecoder().decode(base64String);
    }


}
