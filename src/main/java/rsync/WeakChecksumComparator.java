package rsync;

import java.util.Comparator;

public class WeakChecksumComparator implements Comparator<ChecksumPair> {

    public int compare(ChecksumPair pair1, ChecksumPair pair2) {
        int weak1 = pair1.getWeakChecksum();
        int weak2 = pair2.getWeakChecksum();

        return(weak1 <= weak2 ?weak1:weak2);
    }
}
