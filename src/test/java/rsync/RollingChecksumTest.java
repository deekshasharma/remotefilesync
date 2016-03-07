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


        int expectedFirst16 = 343 % Constants.MOD_M;
        int actualFirst16 = checksum.getFirst16Bit();

        int expectedSecond16 = 819 % Constants.MOD_M;
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
        int first16Bit = 431 % Constants.MOD_M;
        int second16Bit = 1250 % Constants.MOD_M;
        int expectedValue = first16Bit+ (Constants.MOD_M* second16Bit);
        assertEquals(expectedValue,actualValue);
    }

    @Test
    public void testPrune(){
        byte[] block = new byte[]{105,32,97,109,88};
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(block);
        checksum.prune();

        int expectedFirst16 = (431 % Constants.MOD_M) - 105;
        int actualFirst16 = checksum.getFirst16Bit();

        int expectedSecond16 = (1250 % Constants.MOD_M) - (5*105);
        int actualSecond16 = checksum.getSecond16Bit();

        assertEquals(expectedFirst16,actualFirst16);
        assertEquals(expectedSecond16,actualSecond16);
    }

    @Test
    public void testRoll(){
        byte[] block = new byte[]{105,32,97,109,88};
        RollingChecksum checksum = new RollingChecksum();
        checksum.update(block);
        byte nextByte = 100;
        checksum.roll(nextByte);

        int expectedFirst16 = (((431 % Constants.MOD_M) - 105)+nextByte) % Constants.MOD_M;
        int actualFirst16 = checksum.getFirst16Bit();

        int expectedSecond16 = ((1250 % Constants.MOD_M) - (5*105)+expectedFirst16) % Constants.MOD_M;
        int actualSecond16 = checksum.getSecond16Bit();

        assertEquals(expectedFirst16,actualFirst16);
        assertEquals(expectedSecond16,actualSecond16);

    }
}
