package rsync;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class GenerateChecksum extends Object{


    public List<ChecksumPair> generateSignatures(byte[] array,int blockOffset,int blockLength){
        List<ChecksumPair> list = new ArrayList<ChecksumPair>();
        list.add(new ChecksumPair(89, new byte[3]));
        return list;

    }


}
