package de.deftk.lonet.api;

import java.util.Formatter;

public class JavaUtil {

    //TODO rewrite in kotlin
    public static String byteToHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        Formatter formatter = new Formatter();
        for (byte valueOf : bArr) {
            formatter.format("%02x", new Object[]{Byte.valueOf(valueOf)});
        }
        String formatter2 = formatter.toString();
        formatter.close();
        return formatter2;
    }

}
