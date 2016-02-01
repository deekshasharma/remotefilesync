package rsync;

import jdk.nashorn.internal.ir.Block;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class refers to the source that has the most recently updated copy of the file. It sends the delta to the receiver,
 * using which it re-constructs the new file.
 */
public class Sender {

    private static final int MIN_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 1000;

    public void sendDelta(){

    }

    /**
     *
     * @param allBlockSignatures
     * @return
     */
    public Map<Integer,Integer> buildIndexTable(List<BlockSignature> allBlockSignatures){
        Map<Integer,Integer> indexTable = new HashMap<Integer, Integer>();
        for (int i = 0; i < allBlockSignatures.size(); i++){
            int weakSignature = allBlockSignatures.get(i).weakSignature;
            if (!indexTable.containsKey(weakSignature)){
                indexTable.put(weakSignature,i);
            }
        }return indexTable;
    }

    /**
     * Search for the matching block.
     * @param filepath
     * @param indexTable
     * @param allBlockSignatures
     * @throws FileNotFoundException
     */
    List<Byte> search(String filepath,Map<Integer,Integer> indexTable,List<BlockSignature> allBlockSignatures) throws FileNotFoundException {
        // Convert the file at the given path to stream of bytes
        FileStreamToBytes fileStreamToBytes = new FileStreamToBytes();
        byte[] byteStream = fileStreamToBytes.convertToBytes(filepath);
        List<Byte> matched = new ArrayList<Byte>();

        for (int i = 0; i < byteStream.length; i++){
            int byteOffset = i;
            int length = byteOffset + MIN_BLOCK_SIZE - 1;
            byte[] block = extractBlock(byteOffset,length,byteStream);
            int initialWeakHash = getWeakHash(byteOffset,block.length-1,block);
            if (indexTable.containsKey(initialWeakHash)){
                int signatureIndex = indexTable.get(initialWeakHash); // if weak signature match, compute strong signature
                if (Arrays.equals(allBlockSignatures.get(signatureIndex).strongSignature,getStrongHash(block))){
                    // continue to search in the allBlockSignatures linearly to find the next match;
                }

            }else { // Add the delta to the matched.
                    matched.add(byteStream[i]);
            }

        }return matched;

    }

    /**
     * Extract a block from the byte stream given the start and end indexes
     * @param start
     * @param end
     * @param byteStream
     * @return
     */
    byte[] extractBlock(int start, int end, byte[] byteStream){
        byte[] block = new byte[end - start+1];
        int index = 0;
        for (int i = start; i<= end; i++){
            block[index] = byteStream[i];
            index++;
        }return block;
    }


    /**
     *
     * @param k
     * @param l
     * @param block
     * @return
     */
    int getWeakHash(int k, int l, byte[] block){
        Signatures signatures = new Signatures();
        return signatures.getRollingChecksum(k,l,block);
    }

    /**
     *
     * @param block
     * @return
     */
    byte[] getStrongHash(byte[] block){
        Signatures signatures = new Signatures();
        return signatures.getMd5Checksum(block);
    }



    /**
     *
     * @param byteStream
     */
    private static void displayByteToString(byte[] byteStream){
        String s = new String(byteStream);
        System.out.println("Decrypted Text = "+s);
        System.out.println("Size in bytes: "+byteStream.length);
    }
}
