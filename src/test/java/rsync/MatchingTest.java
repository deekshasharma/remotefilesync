package rsync;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

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
}
