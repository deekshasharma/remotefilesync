package rsync;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ClientMachine {

    private static final int MIN_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 1000;


    /**
     * Equally divides the file bytes to blocks of size MIN_BLOCK_SIZE
     * @return
     * @throws FileNotFoundException
     */
    private List<byte[]> splitBytes() throws FileNotFoundException {
        FileStreamToBytes fileStreamToBytes = new FileStreamToBytes();
        byte[] byteStream = fileStreamToBytes.convertToBytes();
        List<byte[]> bytesBlocks = new ArrayList<byte[]>();

        int count = 0;
        byte[] byteBlock = new byte[MIN_BLOCK_SIZE];
        for (int i = 0; i < byteStream.length; i++){
            if (count == MAX_BLOCK_SIZE - 1){
                bytesBlocks.add(byteBlock);
                count = 0;
                byteBlock = new byte[MIN_BLOCK_SIZE];
            }
            byteBlock[count] = byteStream[i];
            count++;
        }
        return bytesBlocks;
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

    private void buildSearchIndex(){

    }

    private void getIndexOfSortedSignatures(){

    }


    private static void displayByteToString(byte[] byteStream){
        String s = new String(byteStream);
        System.out.println("Decrypted Text = "+s);
        System.out.println("Size in bytes: "+byteStream.length);
    }
}
