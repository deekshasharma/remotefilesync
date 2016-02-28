package rsync;

import java.util.Arrays;

public class Matching {

    //Input: Byte stream from the sender's side.
    //Output:
    // 0 1 2  3 4 5 6
    // i = 0;
    //
    public void match(byte[] byteStreamSender){
        for(int i = 0; i < byteStreamSender.length; i++){
            int end = i+Constants.MIN_BLOCK_SIZE-1;
            byte[] block = Arrays.copyOfRange(byteStreamSender,i,end);
            
        }
    }

    public byte getByteLiteral(byte x){
        return x;
    }

    public String getMatchingToken(){
        return "";
    }
}

