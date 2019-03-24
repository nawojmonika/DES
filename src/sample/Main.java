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

    public static int[] IP = {58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7};

    public static int[] rightPermutation = {32, 1, 2, 3, 4, 5, 4, 5,
            6, 7, 8, 9, 8, 9, 10, 11,
            12, 13, 12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21, 20, 21,
            22, 23, 24, 25, 24, 25, 26, 27,
            28, 29, 28, 29, 30, 31, 32, 1};

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
        String permutated = this.permutation(rightSide, this.rightPermutation);
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
