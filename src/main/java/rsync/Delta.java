package rsync;

public class Delta {

    int offset;
    int length;
    public Delta(int offset, int length){
        this.length = length;
        this.offset = offset;
    }

}
