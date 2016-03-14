package rsync;
import static org.junit.Assert.*;

import com.sun.tools.javac.comp.Check;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.Checksum;

public class MatchingTest {

    @Test
    public void testCalculateDelta1(){
        byte[] byteStream = new byte[]{105, 32, 97, 109, 127, 12, 87, 28};

        GenerateChecksum generateChecksum = new GenerateChecksum();
        List<ChecksumPair> checksumPairs = generateChecksum.getCheckSumPairs(byteStream);
        List<ChecksumPair> sorted = Util.sortSignaturesFromReceiver(checksumPairs);

        Map<Integer,Integer> indexTable = Util.buildIndexTable(sorted);

        Matching matching = new Matching();
        String actualDelta = matching.calculateDelta(byteStream,sorted,indexTable);
        System.out.println(actualDelta);
    }


    @Test
    public void testCalculateDelta2(){
        String remote = "My name is Deeksha Sharma";
        byte[] remoteBytes = remote.getBytes();

        System.out.println("REMOTE BYTES");
        System.out.println(Arrays.toString(remoteBytes));

        GenerateChecksum generateChecksum = new GenerateChecksum();
        List<ChecksumPair> checksumPairs = generateChecksum.getCheckSumPairs(remoteBytes);
        List<ChecksumPair> sortedChecksums = Util.sortSignaturesFromReceiver(checksumPairs);
        Map<Integer,Integer> indexTable = Util.buildIndexTable(sortedChecksums);

        String local = "My name is Deeksha Sharma and I study in Santa clara University";

        byte[] localBytes = local.getBytes();
        System.out.println("LOCAL BYTES");
        System.out.println(Arrays.toString(localBytes));

        Matching matching = new Matching();
        String actualDelta = matching.calculateDelta(localBytes,sortedChecksums,indexTable);
        System.out.println(actualDelta);

    }
}
