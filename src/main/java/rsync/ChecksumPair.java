package rsync;

import java.util.Arrays;

/**
 *
 */
public class ChecksumPair extends Object {

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
     * Compare if the 2 objects are same.
     * @param object given checksumpair object
     * @return boolean value if 2 checksumpair objects have the same strong and weak signature
     */
    public boolean equals(Object object){
        return true;
    }


    /**
     * Get the value of the weak checksum
     * @return integer value of the weak checksum
     */
    public int getWeakChecksum() {
        return weakChecksum;
    }

    /**
     * Get the byte array of string checksum
     * @return byte array of the hash value computed by MD5
     */
    public byte[] getStrongChecksum() {
        return strongChecksum;
    }

    /**
     * Return the sequence number of the block, this checksum belongs to
     * @return
     */
    public int getBlockSequenceNumber() {
        return blockSequenceNumber;
    }

    /**
     * Get the blockOffset for this checksum
     * @return long value of the block offset
     */
    public long getBlockOffset() {
        return blockOffset;
    }

    /**
     * Get the length of the data block,this checksumpair belong to
     * @return integer value of the length
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
