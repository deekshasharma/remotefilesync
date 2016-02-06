package rsync;

import java.util.Arrays;

/**
 *
 */
public class ChecksumPair extends Object{

    private int weakChecksum;
    private byte[] strongChecksum;
    private int blockSequenceNumber;
    private long blockOffset;
    private int lengthOfBlock;

    public ChecksumPair(int weakChecksum, byte[] strongChecksum) {
        this.weakChecksum = weakChecksum;
        this.strongChecksum = strongChecksum;
    }

    public ChecksumPair(int weakChecksum, byte[] strongChecksum, int blockSequenceNumber, long blockOffset, int lengthOfBlock) {
        this.weakChecksum = weakChecksum;
        this.strongChecksum = strongChecksum;
        this.blockSequenceNumber = blockSequenceNumber;
        this.blockOffset = blockOffset;
        this.lengthOfBlock = lengthOfBlock;
    }


    /**
     *
     * @param object
     * @return
     */
    public boolean equals(Object object){
        return true;
    }


    /**
     *
     * @return
     */
    public int getWeakChecksum() {
        return weakChecksum;
    }

    /**
     *
     * @return
     */
    public byte[] getStrongChecksum() {
        return strongChecksum;
    }

    /**
     *
     * @return
     */
    public int getBlockSequenceNumber() {
        return blockSequenceNumber;
    }

    /**
     *
     * @return
     */
    public long getBlockOffset() {
        return blockOffset;
    }

    /**
     *
     * @return
     */
    public int getLengthOfBlock() {
        return lengthOfBlock;
    }

    @Override
    public String toString() {
        return "ChecksumPair{" +
                "weakChecksum=" + weakChecksum +
                ", strongChecksum=" + Arrays.toString(strongChecksum) +
                ", blockSequenceNumber=" + blockSequenceNumber +
                ", blockOffset=" + blockOffset +
                ", lengthOfBlock=" + lengthOfBlock +
                '}';
    }
}
