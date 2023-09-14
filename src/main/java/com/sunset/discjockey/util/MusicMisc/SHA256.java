package com.sunset.discjockey.util.MusicMisc;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class SHA256
{

    /**
     * @param array byte[],InputStream,String,File
     * @return String
     */
    public static <T> String calculateSHA256(T array) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash;

            if (array instanceof byte[]) {
                hash = messageDigest.digest((byte[]) array);
            }
            else if (array instanceof InputStream) {
                hash = messageDigest.digest(array.toString().getBytes());
            }
            else if (array instanceof String) {
                hash = messageDigest.digest(((String) array).getBytes());
            }
            else if (array instanceof File) {
                hash = messageDigest.digest(Files.readAllBytes(Paths.get(((File) array).getPath())));
            }
            else {
                throw new Exception("Unsupported type");
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hash) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
