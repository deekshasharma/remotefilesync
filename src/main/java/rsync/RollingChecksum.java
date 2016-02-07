package rsync;

public class RollingChecksum extends Object{

    int value;
    int first16Bytes;
    int next16Bytes;
    byte[] block;
    int blockOffset;
    int blockLength;


    /**
     *
     * @param block
     * @param blockOffset
     * @param blockLength
     */
    public void update(byte[] block,int blockOffset, int blockLength){
    }

    /**
     *
     * @return
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Update the checksum value with the next new byte.
     */
    public void rolling(byte nextByte){

    }

    /**
     * Update the checksum by pruning the left most byte from the block.
     */
    public void prune(){

    }


    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
