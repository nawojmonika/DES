package sample;

public class EncryptionUtils {
    public static String getBites(String message) {
        String biteMessage = "";
        byte[] ascii = message.getBytes();
        for (byte character : ascii) {
            String binary = Integer.toString((int) character, 2);
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
