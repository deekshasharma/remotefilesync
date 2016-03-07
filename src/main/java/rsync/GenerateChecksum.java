package rsync;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateChecksum {

    /**
     * Get the check sum pairs of all the non-overlapping blocks.This would be mostly called by the receiver.
     * @param byteStream the file converted into an array of bytes.
     * @return List of checkSumPair for all blocks.
     */
    public List<ChecksumPair> getCheckSumPairs(byte[] byteStream) {
        List<byte[]> blocks = Util.splitIntoBlocks(byteStream);
        MD5 md5 = new MD5();
        List<byte[]> md5CheckSums = md5.getMd5Checksums(blocks);

        RollingChecksum rollingChecksum = new RollingChecksum();
        List<Integer> weakCheckSums = rollingChecksum.getWeakChecksums(blocks);
        List<ChecksumPair> checksumPairs = new ArrayList<ChecksumPair>();
        for (int i = 0; i < weakCheckSums.size(); i++) {
            checksumPairs.add(new ChecksumPair(weakCheckSums.get(i), md5CheckSums.get(i), i + 1));
        }
        return checksumPairs;
    }

    /**
     * Get the roll checksum for whole byteStream
     * @param byteStream the file converted into an array of bytes.
     * @return List of RollingChecksum for each overlapping block.
     */
    List<RollingChecksum> getRollingChecksum(byte[] byteStream) {
        List<RollingChecksum> rollingChecksums = new ArrayList<RollingChecksum>();
        int startIndex = 0;
        int endIndex = Constants.MIN_BLOCK_SIZE;

        byte[] block = Arrays.copyOfRange(byteStream, startIndex, endIndex);
        RollingChecksum rollingChecksum = new RollingChecksum();
        rollingChecksum.update(block);
        rollingChecksum.getValue();
        rollingChecksums.add(rollingChecksum);

        RollingChecksum previous = rollingChecksum;
        int remainingBytes = byteStream.length-1 - 1;
        for (int i = 1; i < byteStream.length; i++){
            byte[] rollingBlock;
            if (remainingBytes >= endIndex){
                rollingBlock = Arrays.copyOfRange(byteStream,i,i + endIndex - 1);
            }else{
                rollingBlock = Arrays.copyOfRange(byteStream,i,byteStream.length);
            }
            if (rollingBlock.length == 0) {
                break;
            } else {
                RollingChecksum checksum = new RollingChecksum(previous);
                checksum.roll(byteStream[i]);
                checksum.getValue();
                rollingChecksums.add(checksum);
                previous = checksum;
            }
        }
        return rollingChecksums;
    }

    /**
     * @param bytes
     * @param byteOffset
     * @return
     */
    byte[] getStrongChecksum(byte[] bytes, int byteOffset) {
        return new byte[20];
    }



}
