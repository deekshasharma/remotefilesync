package rsync;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
