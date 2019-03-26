package sample;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncryptionUtils {
    public static String getBinary(String message) {
        String biteMessage = "";
        for (int i = 0; i < message.length() ; i++) {
            String binary = Integer.toString(message.charAt(i), 2);
            for (int numberOfBitsToFill = 8 - binary.length(); numberOfBitsToFill > 0; numberOfBitsToFill--) {
                binary = "0" + binary;
            }
            biteMessage += binary;

        }
        return biteMessage;
    }

    public static String permutation(String biteMessage, int[] permutationTable) {
        String permutationMessage = "";
        for (int index : permutationTable) {
            permutationMessage += biteMessage.charAt(index - 1);
        }
        return permutationMessage;
    }

    public static String leftShift(String message, int shiftNum) {
        String firstCharacters = message.substring(0, shiftNum);
        String shiftedMessage = message;
        shiftedMessage = message + firstCharacters;
        return shiftedMessage.substring(shiftNum);
    }
    public static String performXOR(String message, String key){
        String output = "";
        for (int i = 0; i < message.length() ; i++) {
            char messChar = message.charAt(i);
            char keyChar = key.charAt(i);
            if(messChar != keyChar){
                output += "1";
            }
            else {
                output += "0";
            }
        }
        return output;
    }
}
