package rsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void main(String[] args) throws FileNotFoundException {
        String filepath = "./geneticcode.txt";
        convertToBytes(filepath);
    }

    /**
     * Split the file data into blocks of fixed size
     * @param data
     * @return List of blocks with same size except the last block.
     */
     static List<byte[]> splitIntoBlocks(byte[] data){
        List<byte[]> allBlocks = new ArrayList<byte[]>();
        int len = data.length;
        if (len <= Constants.MIN_BLOCK_SIZE){
            allBlocks.add(data);
            return allBlocks;
        }
        int j = 0;
        byte[] block = new byte[Constants.MIN_BLOCK_SIZE];
        for(int i = 0; i< data.length; i++){
            if (j < block.length){
                block[j] = data[i];
                j++;
            }
            else {
                allBlocks.add(block);
                int size = (((len - i) <= Constants.MIN_BLOCK_SIZE) ?(len-i):Constants.MIN_BLOCK_SIZE);
                block = new byte[size];
                j = 0;
                block[j] = data[i];
                j++;
            }
        }
        allBlocks.add(block);
        return allBlocks;
    }


    /**
     * Convert the file contents to a byte array
     * @throws FileNotFoundException
     */
    static byte[] convertToBytes(String filepath) throws FileNotFoundException {

        File dataToTransmit = new File(filepath);
        FileInputStream fileInputStream = new FileInputStream(dataToTransmit);
        byte[] byteStream = new byte[(int)dataToTransmit.length()];

        try{
            fileInputStream.read(byteStream);
            fileInputStream.close();
            displayByteStream(byteStream);

        }catch(IOException e){
            System.out.println("ByteStream is empty");
        }
        return byteStream;
    }

    /**
     * Display the file data into characters & bytes
     * @param byteStream
     */
    private static void displayByteStream(byte[] byteStream){
        for (byte b: byteStream){
            System.out.println(b);
        }
        for (int i = 0; i < byteStream.length; i++){
            System.out.println((char)byteStream[i]);
        }
        System.out.println("Size in bytes: "+byteStream.length);

    }

    /**
     * Decode the file data into String
     * @param byteStream
     */
    private static void displayByteToString(byte[] byteStream){
        String s = new String(byteStream);
        System.out.println("Decrypted Text = "+s);

        System.out.println("Size in bytes: "+byteStream.length);
    }



}
