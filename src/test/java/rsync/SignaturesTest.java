package rsync;

import org.junit.Test;

import java.security.MessageDigest;

import static org.junit.Assert.*;

public class SignaturesTest {

//    Signatures checksumSignature = new Signatures();
    Util util = new Util();
    byte[] block = new byte[]{105,32,97,109,32,104,97,112,112,121,32,104};

//    @Test
//    public void testGetRollingChecksum() throws Exception {
//        int k = 0;
//        int l = 3;
//        assertEquals(93,checksumSignature.getRollingChecksum(k,l,block));
//    }
//
//    @Test
//    public void testGetMd5Checksum() throws Exception {
//        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//        byte[] expected = messageDigest.digest(block);
//        byte[] actual = checksumSignature.getMd5Checksum(block);
//        assertArrayEquals(expected,actual);
//    }

}