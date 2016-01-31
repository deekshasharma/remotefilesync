package rsync;

import java.io.*;

public class FileStreamToBytes {


    public static void main(String[] args) throws FileNotFoundException {
        FileStreamToBytes stringToBytes = new FileStreamToBytes();
        String filepath = "./geneticcode.txt";
        stringToBytes.convertToBytes(filepath);
    }

    /**
     * Convert the file contents to a byte array
     * @throws FileNotFoundException
     */
    public byte[] convertToBytes(String filepath) throws FileNotFoundException {

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
