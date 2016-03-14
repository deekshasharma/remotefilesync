package rsync;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Matching {

    /**
     *
     * @param byteStream
     * @param sortedChecksumPairs
     * @param indexTable
     * @return
     */
    public String calculateDelta(byte[] byteStream, List<ChecksumPair> sortedChecksumPairs,
                                 Map<Integer, Integer> indexTable) {
        StringBuilder result = new StringBuilder();
        int start = 0;
        int end = Constants.MIN_BLOCK_SIZE_TEST;
        byte[] block = Arrays.copyOfRange(byteStream,start,end);
        GenerateChecksum generateChecksum = new GenerateChecksum();
        RollingChecksum checksum = generateChecksum.getFirstWeakChecksum(block);
        while (start <= byteStream.length-1){
            String token = computeToken(checksum,sortedChecksumPairs,indexTable);
            if (token != null){
                result.append(token).append(",");
                start += Constants.MIN_BLOCK_SIZE_TEST;
                end = start + Constants.MIN_BLOCK_SIZE_TEST;
                block = Arrays.copyOfRange(byteStream,start,end);
                checksum = generateChecksum.getFirstWeakChecksum(block);
            }else{
                result.append(checksum.getBlock()[0]).append(",");
                start += 1;
                end = start + Constants.MIN_BLOCK_SIZE_TEST;
                block = Arrays.copyOfRange(byteStream,start,end);
                RollingChecksum previous = checksum;
                checksum = generateChecksum.getRollingChecksum1(previous,block);
            }
        }
        return result.substring(0,result.length()-1);
    }

    /**
     *
     * @param checksum
     * @param sortedChecksumPairs
     * @param indexTable
     * @return
     */
    public String computeToken(RollingChecksum checksum,List<ChecksumPair> sortedChecksumPairs,Map<Integer, Integer> indexTable){
        if (indexTable.containsKey(checksum.getCheckSumValue())) {
                int index = indexTable.get(checksum.getCheckSumValue());
                byte[] strongHash = MD5.getMd5Checksum(checksum.getBlock());
                return checkStrongHash(sortedChecksumPairs, checksum.getCheckSumValue(), index, strongHash);
    } else return null;
    }
    /**
     * Check for the second level match of the strongChecksum
     *
     * @param sortedChecksumPairs Sorted signatures from the receiver
     * @param rollingChecksum     weak checksum of the sender's block
     * @param index               index of the first matching weak checksum
     * @param strongHash          strong checksum of the sender's block
     * @return a token(B1, B2) indicating the block sequence number of the receiver
     * if match is found or null if no match found
     */
    private String checkStrongHash(List<ChecksumPair> sortedChecksumPairs, int rollingChecksum, int index, byte[] strongHash) {
        while (sortedChecksumPairs.get(index).getWeakChecksum() == rollingChecksum) {
            if (Arrays.equals(sortedChecksumPairs.get(index).getStrongChecksum(), strongHash)) {
                return "B" + sortedChecksumPairs.get(index).getBlockSequenceNumber();
            } else index += 1;
        }
        return null;
    }

}

