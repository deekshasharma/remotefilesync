package rsync;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateChecksum extends Object{

    /**
     * Get the check sum pairs of all the non-overlapping blocks.This would be mostly called by the receiver.
     * @param byteStream the file converted into an array of bytes.
     * @return List of checkSumPair for all blocks.
     */
    List<ChecksumPair> getCheckSumPairs(byte[] byteStream){
        List<byte[]> blocks = Util.splitIntoBlocks(byteStream);
        MD5 md5 = new MD5();
        RollingChecksum rollingChecksum = new RollingChecksum();

        List<byte[]> md5CheckSums = md5.getMd5Checksums(blocks);
        List<Integer> weakCheckSums = rollingChecksum.getWeakChecksums(blocks);
        List<ChecksumPair> checksumPairs = new ArrayList<ChecksumPair>();
        for (int i = 0; i < weakCheckSums.size(); i++){
            checksumPairs.add(new ChecksumPair(weakCheckSums.get(i),md5CheckSums.get(i),i+1));
        }
        return checksumPairs;
    }

    /**
     * Get the roll checksum for whole byteStream
     * @param byteStream the file converted into an array of bytes.
     * @return List of RollingChecksum for each overlapping block.
     */
    List<RollingChecksum> getRollingChecksum(byte[] byteStream){
        List<RollingChecksum> checksumArrayList = new ArrayList<RollingChecksum>();
        int startIndex = 0;
        int endIndex = Constants.MIN_BLOCK_SIZE;

        byte[] block = Arrays.copyOfRange(byteStream, startIndex, endIndex);
        RollingChecksum rollingChecksum = new RollingChecksum();
        rollingChecksum.update(block);
        rollingChecksum.getValue();
        checksumArrayList.add(rollingChecksum);

        RollingChecksum previous = rollingChecksum;
        for(int i = 1; i < byteStream.length; i++){
            byte[] rollingBlock = Arrays.copyOfRange(byteStream, i, i+Constants.MIN_BLOCK_SIZE-1);
            if(rollingBlock.length == 0){
                break;
            }else{
                RollingChecksum checksum = new RollingChecksum(previous);
                checksum.roll(byteStream[i]);
                checksum.getValue();
                checksumArrayList.add(checksum);
                previous = checksum;
            }
        }
        return new ArrayList<RollingChecksum>();
    }

    /**
     *
     * @param bytes
     * @param byteOffset
     * @return
     */
    byte[] getStrongChecksum(byte[] bytes,int byteOffset){

        return new byte[20];
    }


}
