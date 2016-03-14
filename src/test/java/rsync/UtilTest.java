package rsync;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class UtilTest {

    Util util = new Util();

    @Test
    public void testSplitIntoBlocks(){
        byte[] data = new byte[]{105,32,97,109,32,104,97,112,112,121,32,104};
        List<byte[]> actualBlocks = util.splitIntoBlocks(data);

        List<byte[]> expectedBlocks = new ArrayList<byte[]>();
        expectedBlocks.add(new byte[]{105,32,97,109,32});
        expectedBlocks.add(new byte[]{104,97,112,112,121});
        expectedBlocks.add(new byte[]{32,104});

        assertTrue(Arrays.equals(expectedBlocks.get(0), actualBlocks.get(0)));
        assertTrue(Arrays.equals(expectedBlocks.get(1), actualBlocks.get(1)));
        assertTrue(Arrays.equals(expectedBlocks.get(2), actualBlocks.get(2)));
    }

    @Test
    public void testSortSignaturesFromReceiver(){
        List<byte[]> strongHash = getStrongHashes();

        List<ChecksumPair> input = new ArrayList<ChecksumPair>();
        input.add(new ChecksumPair(120,strongHash.get(0),1));
        input.add(new ChecksumPair(23,strongHash.get(1),2));

        List<ChecksumPair> expectedChecksumPairs = new ArrayList<ChecksumPair>();
        expectedChecksumPairs.add(new ChecksumPair(23,strongHash.get(1),2));
        expectedChecksumPairs.add(new ChecksumPair(120,strongHash.get(0),1));

        List<ChecksumPair> actualChecksumPairs = Util.sortSignaturesFromReceiver(input);

        assertTrue(expectedChecksumPairs.get(0).toString().equals(actualChecksumPairs.get(0).toString()));
        assertTrue(expectedChecksumPairs.get(1).toString().equals(actualChecksumPairs.get(1).toString()));
    }

    private static List<byte[]> getStrongHashes(){
        List<byte[]> blocks = new ArrayList<byte[]>();
        byte[] b1 = new byte[]{105,32,97,109,127};
        byte[] b2 = new byte[]{12,87,28};
        blocks.add(b1);
        blocks.add(b2);
        MD5 md5 = new MD5();
        return md5.getMd5Checksums(blocks);
    }


    @Test
    public void testBuildIndexTable(){
        List<ChecksumPair> checksumPairs = new ArrayList<ChecksumPair>();
        checksumPairs.add(new ChecksumPair(23, new byte[]{12,11,99,88,89}, 2));
        checksumPairs.add(new ChecksumPair(120, new byte[]{110,121,45,-30}, 1));
        checksumPairs.add(new ChecksumPair(120, new byte[]{56,45,100,99,88}, 3));
        checksumPairs.add(new ChecksumPair(600, new byte[]{69,125,100,99,88}, 4));
        Map<Integer,Integer> expectedIndexTable = new HashMap<Integer, Integer>();
        expectedIndexTable.put(23,0);
        expectedIndexTable.put(120,1);
        expectedIndexTable.put(600,3);

        Map<Integer,Integer> actualIndexTable = Util.buildIndexTable(checksumPairs);
        System.out.println("EXPECTED RESULT: "+expectedIndexTable);
        System.out.println("ACTUAL RESULT: "+actualIndexTable);

        assertTrue(expectedIndexTable.size() == actualIndexTable.size());
    }
}
