package rsync;

public class RollingChecksum extends Object{

    private int first16Bit = 0;
    private int second16Bit = 0;
    private byte[] block;
    private int startIndex;
    private int endIndex;


    /**
     * Compute 32 bit weak checksum.
     * @param block array of bytes in a block
     * @param startIndex start index of the block
     * @param endIndex end index of the block
     */
    public void update(byte[] block,int startIndex, int endIndex){
        clear();
        this.block = block;
        for (int i = startIndex; i <= endIndex; i++) {
            first16Bit += block[i];
        }
        for (int j = startIndex; j <= endIndex; j++) {
            second16Bit += ((endIndex - j + 1) * block[j]);
        }
    }


    /**
     *
     * @return
     */
    public int getValue(){
        return ((first16Bit % Constants.MOD_M) + (Constants.MOD_M * (second16Bit % Constants.MOD_M)));
    }

    /**
     * Update the checksum value with the next new byte.The last byte is <em>X<sub>k+1</sub></em> and update the checksum of
     * <em>X<sub>k+1</sub></em>....<em>X<sub>l+1</sub></em>
     * @param nextByte the new last byte added to the block
     */
    public void rolling(byte nextByte){


    }

    private void clear(){
        first16Bit = 0;
        second16Bit= 0;
        startIndex = 0;
        endIndex = 0;
    }

    /**
     * Update the checksum by pruning the left most byte from the block.
     */
    public void prune(){
        first16Bit -= block[startIndex];
        second16Bit -= (endIndex -startIndex+1)*block[startIndex];
        startIndex += 1;
        endIndex += 1;
    }
}
