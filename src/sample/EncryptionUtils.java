package sample;

public class EncryptionUtils {
    public static String getBinary(String message) {
        String biteMessage = "";
        for (int i = 0; i < message.length() ; i++) {
            String binary = Integer.toString(message.charAt(i), 2);
            biteMessage += fillWithZeros(binary, 8);
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

    public static String fillWithZeros(String binaryMessage, int numberToFill){
        for (int i = numberToFill - binaryMessage.length(); i > 0; i--) {
            binaryMessage = "0" + binaryMessage;
        }
        return binaryMessage;
    }
}
