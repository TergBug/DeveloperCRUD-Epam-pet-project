package org.mycode.testutil;

import org.apache.commons.codec.binary.Hex;

public abstract class EncryptionFunctionsToH2DB {
    public static String hex(byte[] text) {
        return Hex.encodeHexString(text);
    }

    public static byte[] unhex(String text) throws Exception {
        return Hex.decodeHex(text);
    }
}
