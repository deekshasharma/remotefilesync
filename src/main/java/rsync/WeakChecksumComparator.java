package rsync;

import java.util.Comparator;

public class WeakChecksumComparator implements Comparator<ChecksumPair> {

    public int compare(ChecksumPair pair1, ChecksumPair pair2) {
        return Integer.valueOf(pair1.getWeakChecksum()).compareTo(pair2.getWeakChecksum());
    }
}
