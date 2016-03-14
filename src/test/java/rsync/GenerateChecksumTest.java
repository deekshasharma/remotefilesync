package rsync;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class GenerateChecksumTest {


    @Test
    public void testGetRollingChecksum() {
        byte[] byteStream = new byte[]{105, 32, 97, 109, 127, 12, 87, 28};
        RollingChecksum checksum1 = new RollingChecksum();
        checksum1.setBlock(new byte[]{105, 32, 97, 109, 127});
        checksum1.setFirst16Bit(470 % Constants.MOD_M);
        checksum1.setSecond16Bit(1289 % Constants.MOD_M);
        checksum1.setCheckSumValue(470 % Constants.MOD_M + (Constants.MOD_M * (1289 % Constants.MOD_M)));

        RollingChecksum checksum2 = new RollingChecksum();
        checksum2.setBlock(new byte[]{32, 97, 109, 127, 12});
        checksum2.setFirst16Bit(377 % Constants.MOD_M);
        checksum2.setSecond16Bit(1141 % Constants.MOD_M);
        checksum2.setCheckSumValue(377 % Constants.MOD_M + (Constants.MOD_M * (1141 % Constants.MOD_M)));

        RollingChecksum checksum3 = new RollingChecksum();
        checksum3.setBlock(new byte[]{97, 109, 127, 12, 87});
        checksum3.setFirst16Bit(432 % Constants.MOD_M);
        checksum3.setSecond16Bit(1413 % Constants.MOD_M);
        checksum3.setCheckSumValue(432 % Constants.MOD_M + (Constants.MOD_M * (1413 % Constants.MOD_M)));

        RollingChecksum checksum4 = new RollingChecksum();
        checksum4.setBlock(new byte[]{109, 127, 12, 87, 28});
        checksum4.setFirst16Bit(363 % Constants.MOD_M);
        checksum4.setSecond16Bit(1291 % Constants.MOD_M);
        checksum4.setCheckSumValue(363 % Constants.MOD_M + (Constants.MOD_M * (1291 % Constants.MOD_M)));

        List<RollingChecksum> expectedChecksums = new ArrayList<RollingChecksum>();
        expectedChecksums.add(checksum1);
        expectedChecksums.add(checksum2);
        expectedChecksums.add(checksum3);
        expectedChecksums.add(checksum4);
        expectedChecksums.add(new RollingChecksum());
        expectedChecksums.add(new RollingChecksum());
        expectedChecksums.add(new RollingChecksum());
        expectedChecksums.add(new RollingChecksum());

        GenerateChecksum generateChecksum = new GenerateChecksum();
        List<RollingChecksum> actualChecksums = generateChecksum.getRollingChecksum(byteStream);
        RollingChecksum c1 = actualChecksums.get(0);
        RollingChecksum c2 = actualChecksums.get(1);
        RollingChecksum c3 = actualChecksums.get(2);
        RollingChecksum c4 = actualChecksums.get(3);

        assertTrue(expectedChecksums.size() == actualChecksums.size());
        assertTrue(checksum1.toString().equals(c1.toString()));
        assertTrue(checksum2.toString().equals(c2.toString()));
        assertTrue(checksum3.toString().equals(c3.toString()));
        assertTrue(checksum4.toString().equals(c4.toString()));
    }


    @Test
    public void testGetCheckSumPairs(){
        byte[] byteStream = new byte[]{105, 32, 97, 109, 127, 12, 87, 28};
        List<byte[]> strongHash = getStrongHashes(byteStream);



    }

    private static List<byte[]> getStrongHashes(byte[] byteStream){
        List<byte[]> blocks = new ArrayList<byte[]>();
        byte[] b1 = new byte[]{105,32,97,109,127};
        byte[] b2 = new byte[]{12,87,28};
        blocks.add(b1);
        blocks.add(b2);
        MD5 md5 = new MD5();
        return md5.getMd5Checksums(blocks);
    }
}
