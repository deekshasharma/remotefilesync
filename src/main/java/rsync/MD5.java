package rsync;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *Calculate the Strong checksum of all blocks, each block is a byte[]
     * @param blocks List of byte[] containing all the blocks of a file
     * @return List of byte[] where each byte[] is an MD5 checksum of a block
     */
    List<byte[]> getMd5Checksums(List<byte[]> blocks){
        List<byte[]> md5Checksums = new ArrayList<byte[]>();
        for(byte[] block: blocks){
            md5Checksums.add(getMd5Checksum(block));
        }return md5Checksums;
    }
}
