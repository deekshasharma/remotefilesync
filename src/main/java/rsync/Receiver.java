package rsync;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class that should be able to receive a copy of file from the Sender
 * and be able to update the file same as sender's copy.
 */
public class Receiver {


    public void update(){

    }


//    /**
//     * Generate the signatures for a file.
//     * @param filepath path to the file
//     * @return List of all signatures for each block of the file
//     * @throws FileNotFoundException
//     */
//    public List<ChecksumPair> generateSignatures(String filepath) throws FileNotFoundException {
//        // Convert the file at the given path to stream of bytes
//        byte[] byteStream = Util.convertToBytes(filepath);
//
//        // Split the byte stream into blocks of equal size
//        List<byte[]> allBlocks = Util.splitIntoBlocks(byteStream);
//
//        List<ChecksumPair> checksumPairs = new ArrayList<ChecksumPair>();
//        RollingChecksum rollingChecksum = new RollingChecksum();
//        MD5 md5 = new MD5();
//
//        //Return all blockSignatures with a weak and strong checksum for each block.
//        for (byte[] block: allBlocks){
//            rollingChecksum.update(block,0,block.length);
//            int weakSignature = rollingChecksum.calculateValue();
//            byte[] strongSignature = md5.getMd5Checksum(block);
//                    checksumPairs.add(new ChecksumPair(weakSignature,strongSignature));
//        }
//        return checksumPairs;
//    }

    public void sendSignatures(){

    }

}
