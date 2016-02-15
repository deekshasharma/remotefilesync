package rsync;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class refers to the source that has the most recently updated copy of the file. It sends the delta to the receiver,
 * using which it re-constructs the new file.
 */
public class Sender {

    RollingChecksum rollingChecksum = new RollingChecksum();
    List<Byte> matched = new ArrayList<Byte>();

    public void sendDelta(){

    }


    public void sendData(){

    }

    /**
     *
     * @param checksumPairs
     * @return
     */
    public Map<Integer,Integer> buildIndexTable(List<ChecksumPair> checksumPairs){
        Map<Integer,Integer> indexTable = new HashMap<Integer, Integer>();
        WeakChecksumComparator comparator = new WeakChecksumComparator();
        Collections.sort(checksumPairs,comparator);
        for (int i = 0; i < checksumPairs.size(); i++){
            int weakSignature = checksumPairs.get(i).getWeakChecksum();
            if (!indexTable.containsKey(weakSignature)){
                indexTable.put(weakSignature,i);
            }
        }return indexTable;
    }

    /**
     * Search for the matching block.
     * @param filepath
     * @param indexTable
     * @param checksumPairs
     * @throws FileNotFoundException
     */
    List<Delta> search(String filepath,Map<Integer,Integer> indexTable,List<ChecksumPair> checksumPairs) throws FileNotFoundException {
        // Convert the file at the given path to stream of bytes
        byte[] byteStream = Util.convertToBytes(filepath);
        List<Delta> deltas = new ArrayList<Delta>();



        for (int i = 0; i < byteStream.length; i++){
            int byteOffset = i;
            int length = byteOffset + Constants.MIN_BLOCK_SIZE - 1;
            byte[] block = extractBlock(byteOffset,length,byteStream);
            int initialWeakHash = getWeakHash(byteOffset,block.length-1,block);
            if (indexTable.containsKey(initialWeakHash)){
                int signatureIndex = indexTable.get(initialWeakHash); // if weak signature match, compute strong signature
                if (Arrays.equals(checksumPairs.get(signatureIndex).getStrongChecksum(),getStrongHash(block))){
                    deltas.add(new Delta(byteOffset,length));
                }
            }else {
                    matched.add(byteStream[i]);
            }

        }return deltas;

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


//    /**
//     *
//     * @param k
//     * @param l
//     * @param block
//     * @return
//     */
//    int getWeakHash(int k, int l, byte[] block){
//        rollingChecksum.update(block, k, l);
//        return rollingChecksum.getValue();
//    }

    /**
     *
     * @param block
     * @return
     */
    byte[] getStrongHash(byte[] block){
        MD5 md5 = new MD5();
        return md5.getMd5Checksum(block);
    }
}
