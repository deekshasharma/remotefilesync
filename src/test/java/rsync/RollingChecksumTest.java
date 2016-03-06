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

    @Test
    public void testGetValue(){
        byte[] block = new byte[]{105,32,97,109,88};
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(block);
        checksum.getValue();

        int actualValue = checksum.getCheckSumValue();
        int expectedValue = (431 % Constants.MOD_M)+ Constants.MOD_M*(1250 % Constants.MOD_M);
        assertEquals(expectedValue,actualValue);
    }

    @Test
    public void testPrune(){
        byte[] block = new byte[]{105,32,97,109,88};
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(block);
        checksum.prune();

        int expectedFirst16 = 431 - 105;
        int actualFirst16 = checksum.getFirst16Bit();

        int expectedSecond16 = 1250 - (5*105);
        int actualSecond16 = checksum.getSecond16Bit();

        assertEquals(expectedFirst16,actualFirst16);
        assertEquals(expectedSecond16,actualSecond16);

    }
}
