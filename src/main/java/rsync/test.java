package rsync;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class test {

    private static final int MAXSIZE = 5;

    public static void main(String[] args) {
//        Signatures checksumSignature = new Signatures();
//        byte[] block = new byte[]{127,127,127,109,32,104,97,112,112,121,32,104};
//        String binaryHash = Integer.toBinaryString(checksumSignature.getRollingChecksum(0, 3, block));
//        System.out.println(binaryHash);
//        System.out.println(binaryHash.length());
    }

//    public static void main(String[] args) {
//        byte[] block1 = new byte[]{105,32,97,109,32,104,97,112,112,121,32,104};
//        byte[] md1 = (getMd5Checksum(block1));
//
//        byte[] block2 = new byte[]{105,32,97,109,32,104,97,112,112,121,32,104};
//        byte[] md2 = (getMd5Checksum(block2));
//
//        System.out.println(Arrays.equals(md1,md2));
//
//    }



//    public static void main(String[] args) {
//        String s = "i am happy human being";
//        byte[] bytes = s.getBytes();
//        System.out.println("length: "+bytes.length);
//        for (byte b: bytes){
//            System.out.println(b);
//        }
//    }


//    public static void main(String[] args) {
//        int[] integers = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
//        List<int[]> allBlocks = (split(integers));
//        for (int[] block: allBlocks){
//            System.out.println(Arrays.toString(block));
//        }
//    }

    static byte[] getMd5Checksum(byte[] block) {
        byte[] hashSignature;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hashSignature = md.digest(block);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hashSignature;
    }

    private static List<int[]> split(int[] integers){
        List<int[]> allBlocks = new ArrayList<int[]>();
        int len = integers.length;
        if (len <= MAXSIZE){
            allBlocks.add(integers);
            return allBlocks;
        }
        int j = 0;
        int[] block = new int[MAXSIZE];
        for(int i = 0; i< integers.length; i++){
           if (j < block.length){
               block[j] = integers[i];
               j++;
           }
            else {
               allBlocks.add(block);
               int size = (((len - i) <= MAXSIZE) ?(len-i):MAXSIZE);
               block = new int[size];
               j = 0;
               block[j] = integers[i];
               j++;
           }
        }
        allBlocks.add(block);
        return allBlocks;
    }
    private static final int MIN_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 1000;

    /**
     * Equally divides the file bytes to blocks of size MIN_BLOCK_SIZE
     * @return
     * @throws FileNotFoundException
     */
//    private List<byte[]> splitIntoBlocks() throws FileNotFoundException {
//        FileStreamToBytes fileStreamToBytes = new FileStreamToBytes();
//        byte[] byteStream = fileStreamToBytes.convertToBytes();
//        List<byte[]> bytesBlocks = new ArrayList<byte[]>();
//
//        int count = 0;
//        byte[] byteBlock = new byte[MIN_BLOCK_SIZE];
//        for (int i = 0; i < byteStream.length; i++){
//            if (count == MAX_BLOCK_SIZE - 1){
//                bytesBlocks.add(byteBlock);
//                count = 0;
//                byteBlock = new byte[MIN_BLOCK_SIZE];
//            }
//            byteBlock[count] = byteStream[i];
//            count++;
//        }
//        return bytesBlocks;
//    }
}
