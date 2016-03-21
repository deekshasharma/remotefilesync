package rsync;

import java.io.FileNotFoundException;
import java.util.List;

public class Receiver {


    /**
     * Receive the sync signal from the other side to calculate ChecksumPair for each non-overlapping block.
     * @return List<ChecksumPair> containing waek and strong checksum of each block
     * @throws FileNotFoundException
     */
    public List<ChecksumPair> calculateHash() throws FileNotFoundException {
        GenerateChecksum generateChecksum = new GenerateChecksum();
        return generateChecksum.getCheckSumPairs(Util.convertToBytes(Constants.RECEIVER_FILEPATH));
    }

    /**
     * Receive the Delta/Diff from one side and reconstruct the file
     * @param delta
     * @throws FileNotFoundException
     */
    public void receiveDifference(String delta) throws FileNotFoundException {
        Reconstruction fileReconstruct = new Reconstruction();
        fileReconstruct.reconstructFile(delta,Util.splitIntoBlocks(Util.convertToBytes(Constants.RECEIVER_FILEPATH)));
    }
}
