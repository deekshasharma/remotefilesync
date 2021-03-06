package rsync;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Reconstruction {


    /**
     * Reconstruct the file using the byte literals and matching tokens received from sender
     * @param delta String containing comma separated values of matched tokens and unmatched byte literals
     * @param receiverBlocks List of byte arrays containing blocks of fixed size
     */
    public void reconstructFile(String delta,List<byte[]> receiverBlocks){
        try {
            File file = new File(Constants.RECEIVER_FILEPATH);
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String[] literalsArray = delta.split(",");
            for (String literal: literalsArray){
                if (StringUtils.isNumeric(literal)){
                    byte byteLiteral = Byte.valueOf(literal);
                    char charLiteral = (char) byteLiteral;
                    bufferedWriter.write(Character.toString(charLiteral));
                }else{
                    int blockSequenceNumber = Integer.parseInt(literal.substring(1));
                    bufferedWriter.write(new String(receiverBlocks.get(blockSequenceNumber-1)));
                }
            }bufferedWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }



    }
}
