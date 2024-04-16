package com.ninetyninepercentcasino.game;

import java.util.Base64;

public class Base64Utils {
    public static String byteArrayTobase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }
    public static byte[] base64ToByteArray(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}