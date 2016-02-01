package rsync;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Signatures {

        private final int M = 65536;
//    private final int M = 10; /// FOR TESTING PURPOSES

    /**
     * Calculate the rolling checksum of a given block
     *
     * @param k start index of a block
     * @param l end index of a block
     * @param block Array of bytes of a specific size
     * @return the weak/rolling checksum of the given block
     */
    int getRollingChecksum(int k, int l, byte[] block) {
        int a = 0;
        for (int i = k; i <= l; i++) {
            a += block[i];
        }
        int b = 0;
        for (int j = k; j <= l; j++) {
            b += ((l - j + 1) * block[j]);
        }
        return ((a % M) + (M * (b % M)));
    }


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


//    /**
//     * Computes the 128 bit hash signature for each of the given blocks
//     * @param byteBlocks List of the blocks of data. Each block is a byte array of size MIN_BLOCK_SIZE
//     * @return List of 128-bit hash signature for each block
//     */
//    private List<byte[]> getHashSignature(List<byte[]> byteBlocks){
//        List<byte[]> hashSignatures = new ArrayList<byte[]>();
//        for (byte[] block: byteBlocks){
//            try{
//                MessageDigest md = MessageDigest.getInstance("MD5");
//                byte[] hashSignature = md.digest(block);
//                hashSignatures.add(hashSignature);
//            }catch (NoSuchAlgorithmException e){
//                throw new RuntimeException(e);
//            }
//        }
//        return hashSignatures;
//    }

