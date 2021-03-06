package rsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Util {

    /**
     * Split the file data into blocks of fixed size
     * @param data
     * @return List of blocks with same size except the last block.
     */
    static List<byte[]> splitIntoBlocks(byte[] data) {
        List<byte[]> allBlocks = new ArrayList<byte[]>();
        int len = data.length;
        if (len <= Constants.MIN_BLOCK_SIZE_TEST) {
            allBlocks.add(data);
            return allBlocks;
        }
        int j = 0;
        byte[] block = new byte[Constants.MIN_BLOCK_SIZE_TEST];
        for (int i = 0; i < data.length; i++) {
            if (j < block.length) {
                block[j] = data[i];
                j++;
            } else {
                allBlocks.add(block);
                int size = (((len - i) <= Constants.MIN_BLOCK_SIZE_TEST) ? (len - i) : Constants.MIN_BLOCK_SIZE_TEST);
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
     * Convert the file contents to a byte array
     * @throws FileNotFoundException
     */
    public static byte[] convertToBytes(String filepath) throws FileNotFoundException {

        File dataToTransmit = new File(filepath);
        FileInputStream fileInputStream = new FileInputStream(dataToTransmit);
        byte[] byteStream = new byte[(int) dataToTransmit.length()];

        try {
            fileInputStream.read(byteStream);
            fileInputStream.close();
            displayByteStream(byteStream);

        } catch (IOException e) {
            System.out.println("ByteStream is empty");
        }
        return byteStream;
    }

    /**
     * Display the file data into characters & bytes
     *
     * @param byteStream
     */
    static void displayByteStream(byte[] byteStream) {
        for (byte b : byteStream) {
            System.out.println(b);
        }
        for (int i = 0; i < byteStream.length; i++) {
            System.out.println((char) byteStream[i]);
        }
        System.out.println("Size in bytes: " + byteStream.length);
    }

    /**
     * Build the Index table for each checkSumPair of the receiver
     * @param sortedChecksumPairs Pair containing weakChecksum and strongChecksum
     * @return HashMap containing the key as weak hash and value as first occurrence of the weak hash in
     * the sorted Signatures from the receiver.
     */
    public static Map<Integer, Integer> buildIndexTable(List<ChecksumPair> sortedChecksumPairs) {
        Map<Integer, Integer> indexTable = new HashMap<Integer, Integer>();
//        WeakChecksumComparator comparator = new WeakChecksumComparator();
//        Collections.sort(checksumPairs, comparator);
        for (int i = 0; i < sortedChecksumPairs.size(); i++){
            int weakSignature = sortedChecksumPairs.get(i).getWeakChecksum();
            if(!indexTable.containsKey(weakSignature)){
                indexTable.put(weakSignature, i);
            }
        }
        return indexTable;
    }

    /**
     * Sort the checkSumPairs according to weak checksum
     * @param checksumPairs List of unsorted checkSumPairs
     * @return list of sorted checkSumPairs
     */
    public static List<ChecksumPair> sortSignaturesFromReceiver(List<ChecksumPair> checksumPairs){
        WeakChecksumComparator comparator = new WeakChecksumComparator();
        Collections.sort(checksumPairs, comparator);
        return checksumPairs;
    }


    /**
     * Decode the file data into String
     * @param byteStream
     */
    static void displayByteToString(byte[] byteStream) {
        String s = new String(byteStream);
        System.out.println("Decrypted Text = " + s);

        System.out.println("Size in bytes: " + byteStream.length);
    }
}
