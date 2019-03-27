package sample;

import java.util.ArrayList;
import java.util.Collections;

import static sample.EncryptionUtils.*;
import static sample.EncryptionUtils.permutation;

public class Algorithm {
    public static ArrayList<String> getEncryptedKeys(String key) {
        String biteKey = getBinary(key);
        String permutatedKey = permutation(biteKey, PermutationTables.initialKeyPermutation);

        ArrayList<String> subKeys = new ArrayList<String>();
        int keyLength = permutatedKey.length();
        String leftSide = permutatedKey.substring(0, keyLength / 2);
        String rightSide = permutatedKey.substring(keyLength / 2, keyLength);
        for (int shift:PermutationTables.rotationTable) {
            leftSide = leftShift(leftSide, shift);
            rightSide = leftShift(rightSide, shift);
            String subKey = permutation(leftSide + rightSide, PermutationTables.subkeyPermutation);
            subKeys.add(subKey);
        }
        return subKeys;
    }


    public static String encryptMessage(String originalMessage, String key){
        ArrayList<String> encryptedKeys = getEncryptedKeys(key);


        String binaryMessage = getBinary(originalMessage);
        String encryptedMessage = "";
        int wantedLenght = 64;
        int messageLength = binaryMessage.length();
        if(messageLength % wantedLenght != 0){
            int numberOfBytesToFill = messageLength < wantedLenght ? wantedLenght - messageLength : (wantedLenght * 2) - messageLength;
        binaryMessage = fillZerosEnd(binaryMessage, numberOfBytesToFill);
        }
        for (int i = 0; i < binaryMessage.length() ; i+=64) {
            String block = binaryMessage.substring(0, i +64);
            encryptedMessage += DES(block, encryptedKeys);
        }
        return encryptedMessage;
    }
    public static String decryptMessage(String encryptedMessage, String key){
        ArrayList<String> encryptedKeys = getEncryptedKeys(key);
        String binaryMessage = getBinary(encryptedMessage);
        Collections.reverse(encryptedKeys);
        return DES(binaryMessage, encryptedKeys);
    }

    private static String DES(String binaryMessage,  ArrayList<String> encryptedKeys){
        String permutatedMessage = permutation(binaryMessage, PermutationTables.IP);
        String leftSide = permutatedMessage.substring(0, 32);
        String rightSide = permutatedMessage.substring(32, 64);
        for (String subkey:encryptedKeys) {
            String prevLeft = leftSide;
            String prevRight = rightSide;
            String encrytedRight = rightSideEncryption(rightSide, subkey);
            rightSide = performXOR(encrytedRight, prevLeft);
            leftSide = prevRight;
        }
        String reversed = rightSide + leftSide;
        String encryptedBlock = permutation(reversed, PermutationTables.finalPermutation);
        return binaryToString(encryptedBlock);

    }

    public static String fillZerosEnd(String bits, int numberOfBytesToFill){
        for (int i = 0 ; i < numberOfBytesToFill; i++) {
            bits += "0";
        }
        return bits;
    }

    private static String binaryToString(String binaryMessage){
        String message = "";
        for (int i = 0; i < binaryMessage.length(); i+=8) {
            String binary = binaryMessage.substring(i, i+8);
            int charCode = Integer.parseInt(binary, 2);
            message += String.valueOf((char)charCode);
        }

        return message;
    }

    private static String binaryToHex(String binaryMessage){
        String message = "";
        for (int i = 0; i < binaryMessage.length(); i+=8) {
            String binary = binaryMessage.substring(i, i+8);
            int decimal = Integer.parseInt(binary, 2);
            String hex = Integer.toHexString(decimal);
            if(hex.length() < 2){
                hex = "0" + hex;
            }
            message += hex + " ";
        }
        return message;
    }

    private static String rightSideEncryption(String rightSide, String key){
        String expanded = permutation(rightSide, PermutationTables.expansionBox);
        String XORoutput  = performXOR(expanded, key);
        String sblockValues = getSBlockValues(XORoutput);
        return  permutation(sblockValues, PermutationTables.rightFinalPermutation);
    }

    private static String getSBlockValues(String message){
        String combinedSBlockValues = "";
        for (int i = 0; i < 8; i++) {
            int from = i * 6;
            int to = from + 6;
            String sBlock = message.substring(from, to);
            int row = getSRow(sBlock);
            int column = getSColumn(sBlock);
            int shrinkValue;
            try {
                shrinkValue = PermutationTables.getshrinkPermutationBlock(i)[row][column];
                String binaryVal = Integer.toBinaryString(shrinkValue);
                for (int j = 4 - binaryVal.length(); j > 0; j--) {
                    binaryVal = "0" + binaryVal;
                }
                combinedSBlockValues += binaryVal;
            }
            catch (Exception e){}
        }
        return combinedSBlockValues;
    }

    private static int getSRow(String block){
        String binaryVal = block.substring(0,1) + block.substring(5, 6);
        return Integer.parseInt(binaryVal, 2);
    }

    private static int getSColumn(String block){
        String binaryVal = block.substring(1, 5);
        return Integer.parseInt(binaryVal, 2);
    }

}
