package rsync;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Receiver class that should be able to receive a copy of file from the Sender a
 * nd be able to update the file same as sender's copy.
 */
public class Receiver {
    private static final int MIN_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 1000;


    public void update(){

    }

    /**
     * Split the file data into blocks of fixed size
     * @param data
     * @return List of blocks with same size except the last block.
     */
    private static List<byte[]> splitBytes(byte[] data){
        List<byte[]> allBlocks = new ArrayList<byte[]>();
        int len = data.length;
        if (len <= MIN_BLOCK_SIZE){
            allBlocks.add(data);
            return allBlocks;
        }
        int j = 0;
        byte[] block = new byte[MIN_BLOCK_SIZE];
        for(int i = 0; i< data.length; i++){
            if (j < block.length){
                block[j] = data[i];
                j++;
            }
            else {
                allBlocks.add(block);
                int size = (((len - i) <= MIN_BLOCK_SIZE) ?(len-i):MIN_BLOCK_SIZE);
                block = new byte[size];
                j = 0;
                block[j] = data[i];
                j++;
            }
        }
        allBlocks.add(block);
        return allBlocks;
    }

    /**
     *
     * @param filepath
     * @return
     * @throws FileNotFoundException
     */
    public List<BlockSignature> generateSignatures(String filepath) throws FileNotFoundException {
        // Convert the file at the given path to stream of bytes
        FileStreamToBytes fileStreamToBytes = new FileStreamToBytes();
        byte[] byteStream = fileStreamToBytes.convertToBytes(filepath);

        // Split the byte stream into blocks of equal size
        List<byte[]> allBlocks = splitBytes(byteStream);

        List<BlockSignature> blockSignatures = new ArrayList<BlockSignature>();
        Signatures signatures = new Signatures();

        //Return all blockSignatures with a weak and strong checksum for each block.
        for (byte[] block: allBlocks){
            int weakSignature = signatures.getRollingChecksum(0, block.length, block);
            byte[] strongSignature = signatures.getMd5Checksum(block);
            blockSignatures.add(new BlockSignature(weakSignature,strongSignature));
        }
        return blockSignatures;
    }

    public void sendSignatures(){

    }

}
