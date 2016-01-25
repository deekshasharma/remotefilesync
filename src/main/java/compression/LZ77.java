package compression;

import java.util.ArrayList;
import java.util.List;

public class LZ77 {

    String source = "aacaacabcabaaac";
//    String source = "badadadabaab";
    private static List<String> result = new ArrayList<String>();
    StringBuilder searchBuffer = new StringBuilder();

    public static void main(String[] args) {

        LZ77 lz77 = new LZ77();
        lz77.compress();
        System.out.println(result);
    }

    private void compress(){
        String currentMatch = "";
        int matchIndex = 0;
        int goBackBytes = 0;
        int pointer = 0;
        int length = 0;
        int currentIndex = 0;

        while(pointer < source.length()){
            String currentString = currentMatch+source.charAt(pointer);
            currentIndex = searchBuffer.lastIndexOf(currentString);

            if(currentIndex == - 1){
                if(currentMatch.length() == 0){
                    length = currentMatch.length();
                    String output = ""+goBackBytes+"-"+length+"-"+source.charAt(pointer);
                    result.add(output);
                    currentMatch = "";
                    pointer++;
                    addToSearchBuffer(currentString);
                }
                if(currentMatch.length() > 0){
                    String repetition = searchBuffer.substring(matchIndex)+currentMatch;
                    if((repetition.lastIndexOf(currentString) != -1)){
                        currentMatch = currentMatch+source.charAt(pointer);
                        length = currentMatch.length();
                        pointer++;
                    }else{
                        String output = ""+goBackBytes+"-"+length+"-"+source.charAt(pointer);
                        result.add(output);
                        currentMatch = "";
                        pointer++;
                        addToSearchBuffer(currentString);
                    }
                }
                }
            else{
                    matchIndex = currentIndex;
                    currentMatch = currentMatch + source.charAt(pointer);
                    goBackBytes = searchBuffer.length() - matchIndex;
                    length = currentMatch.length();
                System.out.println("CurrentMatch is: "+currentMatch);
                    if(pointer == source.length()-1){
                        String output = ""+goBackBytes+"-"+length+"-$";
                        result.add(output);
                        break;
                    }else{
                        pointer++;
                    }
            }
        }
    }

    private void addToSearchBuffer(String currentString){
        searchBuffer.append(currentString);
        resizeSearchBuffer();
    }

    private void resizeSearchBuffer(){

    }
}
