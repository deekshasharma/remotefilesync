package rsync;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


public class RollingChecksumTest {

    @Test
    public void testUpdate(){
        byte[] block = new byte[]{105,32,97,109};
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(block);

        byte[] expectedBlock = block;
        byte[] actualBlock = checksum.getBlock();

        int expectedStartIndex = 0;
        int actualStartIndex = checksum.getStartIndex();

        int expectedEndIndex = block.length-1;
        int actualEndIndex = checksum.getEndIndex();


        int expectedFirst16 = 343;
        int actualFirst16 = checksum.getFirst16Bit();

        int expectedSecond16 = 819;
        int actualSecond16 = checksum.getSecond16Bit();

        assertTrue(Arrays.equals(expectedBlock,actualBlock));
        assertEquals(expectedStartIndex,actualStartIndex);
        assertEquals(expectedEndIndex,actualEndIndex);
        assertEquals(expectedFirst16,actualFirst16);
        assertEquals(expectedSecond16,actualSecond16);

    }
}
