package rsync;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Sender {

    private static final int MIN_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 1000;




    /**
     * Split the file data into blocks of fixed size
     * @param data
     * @return List of blocks with same size except the last block.
     */
    private static List<byte[]> splitBytes(byte[] data){
        List<byte[]> allBlocks = new ArrayList<byte[]>();
        int len = data.length;
        if (len <= MIN_BLOCK_SIZE){
            allBlocks.add(data);
            return allBlocks;
        }
        int j = 0;
        byte[] block = new byte[MIN_BLOCK_SIZE];
        for(int i = 0; i< data.length; i++){
            if (j < block.length){
                block[j] = data[i];
                j++;
            }
            else {
                allBlocks.add(block);
                int size = (((len - i) <= MIN_BLOCK_SIZE) ?(len-i):MIN_BLOCK_SIZE);
                block = new byte[size];
                j = 0;
                block[j] = data[i];
                j++;
            }
        }
        allBlocks.add(block);
        return allBlocks;
    }

    /**
     * Computes the 128 bit hash signature for each of the given blocks
     * @param byteBlocks List of the blocks of data. Each block is a byte array of size MIN_BLOCK_SIZE
     * @return List of 128-bit hash signature for each block
     */
    private List<byte[]> getHashSignature(List<byte[]> byteBlocks){
        List<byte[]> hashSignatures = new ArrayList<byte[]>();
        for (byte[] block: byteBlocks){
            try{
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] hashSignature = md.digest(block);
                hashSignatures.add(hashSignature);
            }catch (NoSuchAlgorithmException e){
                throw new RuntimeException(e);
            }
        }
        return hashSignatures;
    }


    private static void displayByteToString(byte[] byteStream){
        String s = new String(byteStream);
        System.out.println("Decrypted Text = "+s);
        System.out.println("Size in bytes: "+byteStream.length);
    }
}
