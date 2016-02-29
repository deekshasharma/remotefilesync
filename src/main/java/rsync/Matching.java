package rsync;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Matching {


    /**
     *
     * @param rollingChecksums
     * @param sortedChecksumPairs
     * @param indexTable Map containing key(weak checksum) and value
     * @return String containing the sequence of delta
     */
    public String calculateDelta(List<RollingChecksum> rollingChecksums,List<ChecksumPair> sortedChecksumPairs,Map<Integer,Integer> indexTable){
        StringBuilder result = new StringBuilder();
        for (RollingChecksum rollingChecksum:rollingChecksums){
            if(indexTable.containsKey(rollingChecksum.getCheckSumValue())){
                int index = indexTable.get(rollingChecksum.getCheckSumValue());
                byte[] strongHash = MD5.getMd5Checksum(rollingChecksum.getBlock());
                String token = checkMatch(sortedChecksumPairs,rollingChecksum.getCheckSumValue(),index,strongHash);
                if (token != null){
                    result.append(token).append(",");
                }
        }result.append(rollingChecksum.getBlock()[0]).append(",");
    }return result.toString();
    }

    /**
     * Check for the second level match of the strongChecksum
     * @param sortedChecksumPairs Sorted signatures from the receiver
     * @param rollingChecksum weak checksum of the sender's block
     * @param index index of the first matching weak checksum
     * @param strongHash strong checksum of the sender's block
     * @return a token(B1, B2) indicating the block sequence number of the receiver
     * if match is found or null if no match found
     */
    private String checkMatch(List<ChecksumPair> sortedChecksumPairs, int rollingChecksum,int index, byte[] strongHash){
        while (sortedChecksumPairs.get(index).getWeakChecksum() != rollingChecksum){
            if(Arrays.equals(sortedChecksumPairs.get(index).getStrongChecksum(), strongHash)){
                return "B"+sortedChecksumPairs.get(index).getBlockSequenceNumber();
            }else index += 1;
        }
        return null;
    }

}

