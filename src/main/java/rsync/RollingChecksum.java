package rsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RollingChecksum extends Object {

    private int first16Bit = 0;
    private int second16Bit = 0;
    private byte[] block;
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
        for (int i = startIndex; i <= endIndex; i++) {
            first16Bit += block[i];
        }
        first16Bit %= Constants.MOD_M;
        for (int j = startIndex; j <= endIndex; j++) {
            second16Bit += ((endIndex - j + 1) * block[j]);
        }
        second16Bit %= Constants.MOD_M;
    }

    /**
     * Compute 32 bit checksum given a byte[]
     * @param block
     */
    public void update(byte[] block) {
        update(block, 0, block.length - 1);
    }


    public void calculateValue() {
        this.checkSumValue = first16Bit + (Constants.MOD_M * second16Bit);
    }

    public void roll(byte[] newBlock,byte byteToPrune){
        block = newBlock;
        prune(byteToPrune);
        first16Bit = (first16Bit+block[block.length-1]) % Constants.MOD_M;
        second16Bit = (second16Bit+first16Bit) % Constants.MOD_M;
    }
    /**
     *
     */
    private void clear() {
        first16Bit = 0;
        second16Bit = 0;
//        startIndex = 0;
//        endIndex = 0;
        checkSumValue = 0;
    }

    public void prune(byte byteToPrune) {
        first16Bit -= byteToPrune;
        second16Bit -= Constants.MIN_BLOCK_SIZE_TEST * byteToPrune;
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
            calculateValue();
            weakChecksums.add(checkSumValue);
            clear();
        }
        return weakChecksums;
    }

    public int getFirst16Bit() {return first16Bit;}

    public int getSecond16Bit() {return second16Bit;}

    public byte[] getBlock() {return block;}

    public int getCheckSumValue() {
        return checkSumValue;
    }


    public void setFirst16Bit(int first16Bit) {
        this.first16Bit = first16Bit;
    }

    public void setSecond16Bit(int second16Bit) {
        this.second16Bit = second16Bit;
    }

    public void setBlock(byte[] block) {
        this.block = block;
    }

    public void setCheckSumValue(int checkSumValue) {
        this.checkSumValue = checkSumValue;
    }

    @Override
    public String toString() {
        return "RollingChecksum{" +
                "first16Bit=" + first16Bit +
                ", second16Bit=" + second16Bit +
                ", block=" + Arrays.toString(block) +
                ", checkSumValue=" + checkSumValue +
                '}';
    }
}
