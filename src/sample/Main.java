package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

import static sample.EncryptionUtils.*;

public class Main extends Application {
    private String originalMessage = "IEOFIT#1";
    private String key = "IEOFIT#1";


    @Override
    public void start(Stage primaryStage) throws Exception {
        ProgramWindow pw = new ProgramWindow(primaryStage);
        primaryStage.show();

        this.encryptMessage(this.originalMessage, this.key);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ArrayList<String> getEncryptedKeys(String key) {
        String biteKey = getBites(key);
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


    private void encryptMessage(String originalMessage, String key){
        ArrayList<String> encryptedKeys = getEncryptedKeys(key);
        String permutatedMessage = permutation(getBites(originalMessage), PermutationTables.IP);
        String leftSide = permutatedMessage.substring(0, 32);
        String rightSide = permutatedMessage.substring(32, 64);
        for (String subkey:encryptedKeys) {
            String prevLeft = leftSide;
            String prevRight = rightSide;
            String encrytedRight = this.rightSideEncryption(rightSide, subkey);
            rightSide = performXOR(encrytedRight, prevLeft);
            leftSide = prevRight;
        }
        String reversed = rightSide + leftSide;
        String encryptedBlock = permutation(reversed, PermutationTables.finalPermutation);
        System.out.println(this.binaryToHex(encryptedBlock));
    }

    private String binaryToString(String binaryMessage){
        String message = "";
        for (int i = 0; i < binaryMessage.length(); i+=8) {
            String binary = binaryMessage.substring(i, i+8);
            int charCode = Integer.parseInt(binary, 2);
            message += new Character((char)charCode).toString();
        }
        return message;
    }

    private String binaryToHex(String binaryMessage){
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

    private String rightSideEncryption(String rightSide, String key){
        String permutated = permutation(rightSide, PermutationTables.rightInitialPermutation);
        String XORoutput  = performXOR(permutated, key);
        String sblockValues = this.getSBlockValues(XORoutput);
        return  permutation(sblockValues, PermutationTables.rightFinalPermutation);
    }

    private String getSBlockValues(String message){
        String combinedSBlockValues = "";
        for (int i = 0; i < 8; i++) {
            int from = i * 6;
            int to = from + 6;
            String sBlock = message.substring(from, to);
            int row = this.getSRow(sBlock);
            int column = this.getSColumn(sBlock);
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

    private int getSRow(String block){
        String binaryVal = block.substring(0,1) + block.substring(5, 6);
        return Integer.parseInt(binaryVal, 2);
    }

    private int getSColumn(String block){
        String binaryVal = block.substring(1, 5);
        return Integer.parseInt(binaryVal, 2);
    }

}
