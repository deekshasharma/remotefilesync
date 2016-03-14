package rsync;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateChecksum {

    /**
     * Get the check sum pairs of all the non-overlapping blocks.
     * This would be mostly called by the receiver.
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
     * Get the roll checksum for whole byteStream.
     * @param byteStream the file converted into an array of bytes.
     * @return List of RollingChecksum for each overlapping block.
     */
    List<RollingChecksum> getRollingChecksum(byte[] byteStream) {
        List<RollingChecksum> rollingChecksums = new ArrayList<RollingChecksum>();

        byte[] firstBlock = Arrays.copyOfRange(byteStream, 0, Constants.MIN_BLOCK_SIZE_TEST);
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(firstBlock);
        checksum.calculateValue();
        rollingChecksums.add(checksum);

        RollingChecksum previous = checksum;
        for (int i = 1; i < byteStream.length; i++) {
            int end = i + Constants.MIN_BLOCK_SIZE_TEST - 1;
            byte[] nextBlock = Arrays.copyOfRange(byteStream, i, end + 1);
                RollingChecksum rolling = new RollingChecksum(previous);
                rolling.roll(nextBlock, previous.getBlock()[0]);
                rolling.calculateValue();
                rollingChecksums.add(rolling);
                previous = rolling;
        }
        return rollingChecksums;
    }

    /**
     * Calculate the rolling checksum of non overlapping block
     * @param block Array of bytes
     * @return RollingChecksum without rolling
     */
    RollingChecksum getFirstWeakChecksum(byte[] block){
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(block);
        checksum.calculateValue();
        return checksum;
    }

    /**
     * Calculate the rolling checksum of an overlapping block
     * @param previous RollingChecksum for the previous block
     * @param newBlock Byte Array of an overlapping block
     * @return RollingChecksum with rolling
     */
    RollingChecksum getRollingChecksum1(RollingChecksum previous,byte[] newBlock){

        RollingChecksum rolling = new RollingChecksum(previous);
        rolling.roll(newBlock, previous.getBlock()[0]);
        rolling.calculateValue();
        return rolling;
    }


}
