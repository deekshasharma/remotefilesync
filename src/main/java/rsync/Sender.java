package rsync;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Sender {


    public String sendDifference(List<ChecksumPair> checksumPairs) throws FileNotFoundException {
        Map<Integer,Integer> indexTable = Util.buildIndexTable(Util.sortSignaturesFromReceiver(checksumPairs));
        byte[] senderByteStream = Util.convertToBytes(Constants.SENDER_FILEPATH);
        Matching matching = new Matching();
        return matching.calculateDelta(senderByteStream,Util.sortSignaturesFromReceiver(checksumPairs),indexTable);
    }
}
