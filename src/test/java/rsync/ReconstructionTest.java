package rsync;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class ReconstructionTest {

    @Test
    public void testReconstructFile(){

        String remote = "My name is Deeksha Sharma";
        byte[] remoteBytes = remote.getBytes();

        GenerateChecksum generateChecksum = new GenerateChecksum();
        List<ChecksumPair> checksumPairs = generateChecksum.getCheckSumPairs(remoteBytes);
        List<ChecksumPair> sortedChecksums = Util.sortSignaturesFromReceiver(checksumPairs);
        Map<Integer,Integer> indexTable = Util.buildIndexTable(sortedChecksums);

        String local = "My name is Deeksha Sharma and I love programming";
        byte[] localBytes = local.getBytes();

        Matching matching = new Matching();
        String actualDelta = matching.calculateDelta(localBytes,sortedChecksums,indexTable);
        System.out.println(actualDelta);

        Reconstruction reconstruction = new Reconstruction();
        reconstruction.reconstructFile(actualDelta,Util.splitIntoBlocks(remoteBytes));

    }

}
