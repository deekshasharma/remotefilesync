package rsync;

public class BlockSignature {

     int weakSignature;
     byte[] strongSignature;

    public BlockSignature(int weakSignature, byte[] strongSignature){
        this.weakSignature = weakSignature;
        this.strongSignature = strongSignature;
    }
}
