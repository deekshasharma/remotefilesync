package rsync;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    /**
     * Computes the 128 bit strong hash signature using MD5 for a given block
     * @param block an array containing few hundred bytes
     * @return an array of bytes for the resulting hash value
     */
    byte[] getMd5Checksum(byte[] block) {
        byte[] hashSignature;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hashSignature = md.digest(block);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hashSignature;
    }

}
