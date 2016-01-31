package rsync;

public class BlockSignature {

    private int weakSignature;
    private byte[] strongSignature;

    public BlockSignature(int weakSignature, byte[] strongSignature){
        this.weakSignature = weakSignature;
        this.strongSignature = strongSignature;
    }
}
