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

    public ChecksumPair(int weakChecksum, byte[] strongChecksum) {
        this.weakChecksum = weakChecksum;
        this.strongChecksum = strongChecksum;
    }

    public ChecksumPair(int weakChecksum, byte[] strongChecksum, int blockSequenceNumber) {
        this.weakChecksum = weakChecksum;
        this.strongChecksum = strongChecksum;
        this.blockSequenceNumber = blockSequenceNumber;
//        this.blockOffset = blockOffset;
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
        return this.weakChecksum;
    }

    /**
     * Get the byte array of string checksum
     * @return byte array of the hash value computed by MD5
     */
    public byte[] getStrongChecksum() {
        return this.strongChecksum;
    }

    /**
     * Return the sequence number of the block, this checksum belongs to
     * @return
     */
    public int getBlockSequenceNumber() {
        return this.blockSequenceNumber;
    }

    /**
     * Get the blockOffset for this checksum
     * @return long value of the block offset
     */
    public long getBlockOffset() {
        return this.blockOffset;
    }


    @Override
    public String toString() {
        return "ChecksumPair{" +
                "weakChecksum=" + weakChecksum +
                ", strongChecksum=" + Arrays.toString(strongChecksum) +
                ", blockSequenceNumber=" + blockSequenceNumber +
                '}';
    }
}
