package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private static int[] initialKeyPermutation = {57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4};

    private static int[] rotationTable= {1,	1, 2, 2, 2, 2, 2, 2, 1,	2, 2, 2, 2, 2, 2, 1 };

    private static int[] subkeyPermutation = {14,	17,	11,	24,	1,	5,	3,	28,
            15,	6,	21,	10,	23,	19,	12,	4,
            26,	8,	16,	7, 27,	20,	13,	2,
            41,	52,	31,	37,	47,	55,	30,	40,
            51,	45,	33,	48,	44,	49,	39,	56,
            34,	53,	46,	42,	50,	36,	29,	32};

    private static int[] IP = {58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7};

    private static int[] rightInitialPermutation = {32, 1, 2, 3, 4, 5, 4, 5,
            6, 7, 8, 9, 8, 9, 10, 11,
            12, 13, 12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21, 20, 21,
            22, 23, 24, 25, 24, 25, 26, 27,
            28, 29, 28, 29, 30, 31, 32, 1};

    private static int[][] shrinkPermutationBlock1 = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };

    private static int[][] shrinkPermutationBlock2 = {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };

    private static int[][] shrinkPermutationBlock3 = {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };

    private static int[][] shrinkPermutationBlock4 = {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };

    private static int[][] shrinkPermutationBlock5 = {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };

    private static int[][] shrinkPermutationBlock6 = {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };

    private static int[][] shrinkPermutationBlock7 = {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };

    private static int[][] shrinkPermutationBlock8 = {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    private String originalMessage = "IEOFIT#2";
    private String key = "IEOFIT#1";


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DES");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        ArrayList<String> encryptedKeys = getEncryptedKeys(this.key);
        this.encryptMessage(this.originalMessage, encryptedKeys);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ArrayList<String> getEncryptedKeys(String key) {
        String biteKey = this.getBites(key);
        String permutatedKey = this.permutation(biteKey, this.initialKeyPermutation);
        ArrayList<String> subKeys = new ArrayList<String>();
        int keyLength = permutatedKey.length();
        String leftSide = permutatedKey.substring(0, keyLength / 2);
        String rightSide = permutatedKey.substring(keyLength / 2, keyLength);

        for (int shift:rotationTable) {
            leftSide = this.leftShift(leftSide, shift);
            rightSide = this.leftShift(rightSide, shift);
            String subKey = this.permutation(leftSide + rightSide, this.subkeyPermutation);
            subKeys.add(subKey);
        }
        return subKeys;
    }

    private String getBites(String message) {
        String biteMessage = "";
        byte[] ascii = message.getBytes();
        for (byte character : ascii) {
            String binary = Integer.toString((int) character, 2);
            for (int i = 8 - binary.length(); i > 0; i--) {
                binary = "0" + binary;
            }
            biteMessage += binary;
        }
        return biteMessage;
    }

    private String permutation(String biteMessage, int[] permutationTable) {
        String permutationMessage = "";
        for (int index : permutationTable) {
            permutationMessage += biteMessage.charAt(index - 1);
        }
        return permutationMessage;
    }

    private String leftShift(String message, int shiftNum) {
        String firstChar = message.substring(0, 1);
        String shiftedMessage = message;
        for (int i = 0; i < shiftNum; i++) {
            shiftedMessage = shiftedMessage.substring(1, shiftedMessage.length()) + firstChar;
        }
        return shiftedMessage;
    }

    private void encryptMessage(String originalMessage, ArrayList<String> encryptedKeys){
        String permutatedMessage = this.permutation(this.getBites(originalMessage), this.IP);
        String leftSide = permutatedMessage.substring(0, 32);
        String rightSide = permutatedMessage.substring(32, 64);
        this.rightSideEncryption(rightSide, encryptedKeys);
    }

    private void rightSideEncryption(String rightSide, ArrayList<String> encryptedKeys){
        String permutated = this.permutation(rightSide, this.rightInitialPermutation);
        this.performXOR(permutated, encryptedKeys.get(0));
    }

    private String performXOR(String message, String key){
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
