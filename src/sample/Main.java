package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static int[] IP = {57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4};

    private static int[] rotationTable= {1,	1,	2,	2,	2,	2,	2,	2,	1,	2,	2,	2,	2,	2,	2, 1 };

    private static int[] keyPermutation = {14,	17,	11,	24,	1,	5,	3,	28,
            15,	6,	21,	10,	23,	19,	12,	4,
            26,	8,	16,	7, 27,	20,	13,	2,
            41,	52,	31,	37,	47,	55,	30,	40,
            51,	45,	33,	48,	44,	49,	39,	56,
            34,	53,	46,	42,	50,	36,	29,	32};

    private String originalMessage = "Ala ma kota";
    private String key = "IEOFIT#1";


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DES");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        getEncryptedKeys(this.key);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String[] getEncryptedKeys(String key) {
        String biteKey = this.getBites(key);
        String permutatedKey = this.initialPermutaion(biteKey, this.IP);
        String[] subKeys = {};

        int keyLength = permutatedKey.length();
        String leftSide = permutatedKey.substring(0, keyLength / 2);
        String rightSide = permutatedKey.substring(keyLength / 2, keyLength);
        String leftKey = this.leftShift(leftSide);
        String rightKey = this.leftShift(rightSide);

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

    private String initialPermutaion(String biteMessage, int[] permutationTable) {
        String permutationMessage = "";
        for (int index : permutationTable) {
            permutationMessage += biteMessage.charAt(index - 1);
        }
        return permutationMessage;
    }

    private String leftShift(String message) {
        String firstChar = message.substring(0, 1);
        return message.substring(1, message.length()) + firstChar;
    }
}
