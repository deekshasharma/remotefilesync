package rsync;

import java.util.ArrayList;
import java.util.List;

public class RollingChecksum extends Object {

    private int first16Bit = 0;
    private int second16Bit = 0;
    private byte[] block;
    private int startIndex;
    private int endIndex;
    private int checkSumValue;


    /** Copy Constructor
     * @param rollingChecksum
     */
    RollingChecksum(RollingChecksum rollingChecksum){
        first16Bit = rollingChecksum.first16Bit;
        second16Bit = rollingChecksum.second16Bit;
    }

    public RollingChecksum() {
    }


    /**
     * Compute 32 bit weak checksum.
     * @param block      array of bytes in a block.
     * @param startIndex start index of the block.
     * @param endIndex   end index of the block.
     */
    public void update(byte[] block, int startIndex, int endIndex) {
        this.block = block;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        for (int i = startIndex; i <= endIndex; i++) {
            first16Bit += block[i];
        }
        for (int j = startIndex; j <= endIndex; j++) {
            second16Bit += ((endIndex - j + 1) * block[j]);
        }
    }

    /**
     * Compute 32 bit checksum given a byte[]
     * @param block
     */
    public void update(byte[] block) {
        update(block, 0, block.length - 1);
    }


//    /**
//     *
//     * @return
//     */
//    public int getValue(){
//        return ((first16Bit % Constants.MOD_M) + (Constants.MOD_M * (second16Bit % Constants.MOD_M)));
//    }

    /**
     * @return
     */
    public void getValue() {
        this.checkSumValue = (first16Bit % Constants.MOD_M) + (Constants.MOD_M * (second16Bit % Constants.MOD_M));
    }

    /**
     * Update the checksum value with the next new byte.The last byte is <em>X<sub>k+1</sub></em> and update the checksum of
     * <em>X<sub>k+1</sub></em>....<em>X<sub>l+1</sub></em>
     * @param nextByte the new last byte added to the block
     */
    public void roll(byte nextByte){
        prune();
        first16Bit += nextByte;
        first16Bit %= Constants.MOD_M;
        second16Bit += first16Bit;
        second16Bit %= Constants.MOD_M;
    }

    /**
     *
     */
    private void clear() {
        first16Bit = 0;
        second16Bit = 0;
        startIndex = 0;
        endIndex = 0;
    }

    /**
     * Update the checksum by pruning the left most byte from the block.
     */
    public void prune() {
        first16Bit -= block[startIndex];
        second16Bit -= (endIndex - startIndex + 1) * block[startIndex];
        startIndex += 1;
        endIndex += 1;
    }

    /**
     * Calculate and return the WeakChecksum of each non-overlapping block of a file.
     * This would be mostly used by the receiver.
     * @param blocks List of byte[] containing all the blocks of a file.
     * @return List of weakCheckSums for all the blocks of a file.
     */
    List<Integer> getWeakChecksums(List<byte[]> blocks) {
        List<Integer> weakChecksums = new ArrayList<Integer>();
        for (byte[] block: blocks) {
            update(block);
            getValue();
            weakChecksums.add(checkSumValue);
            clear();
        }
        return weakChecksums;
    }

    public int getFirst16Bit() {
        return first16Bit;
    }

    public int getSecond16Bit() {
        return second16Bit;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public byte[] getBlock() {
        return block;
    }

    public int getCheckSumValue() {
        return checkSumValue;
    }
}
