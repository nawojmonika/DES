package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static int[] IP = {57, 49,	41,	33,	25,	17,	9,	1,
            58,	50,	42,	34,	26,	18,	10,	2,
            59,	51,	43,	35,	27,	19,	11,	3,
            60,	52,	44,	36,	63,	55,	47,	39,
            31,	23,	15,	7,	62,	54,	46,	38,
            30,	22,	14,	6,	61,	53,	45,	37,
            29,	21,	13,	5,	28,	20,	12,	4};

    private String originalMessage = "Ala ma kota";
    private String key = "IEOFIT#1";


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DES");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        getEncryptedKeys(this.key);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void getEncryptedKeys(String key){
       String biteKey = this.getBites(key);
       String[] halfKeys = this.initialPermutaion(biteKey);
       String leftKey = this.leftShift(halfKeys[0]);
       String rightKey = this.leftShift(halfKeys[1]);
    }

    private String getBites(String message){
        String biteMessage= "";
        byte[] ascii = message.getBytes();
        for (byte character : ascii) {
            String binary = Integer.toString((int)character, 2);
            for (int i = 8 - binary.length(); i > 0; i--  ) {
                binary = "0" + binary;
            }
            biteMessage += binary;
        }
        return biteMessage;
    }

    private String[] initialPermutaion(String biteMessage){
        String permutationMessage = "";
        for (int index:this.IP) {
            permutationMessage += biteMessage.charAt(index - 1);
            }
        int IPLength = this.IP.length;
        String leftSide = permutationMessage.substring(0,IPLength/2);
        String rightSide = permutationMessage.substring(IPLength/2, IPLength);
        return new String[] {leftSide, rightSide};
    }

    private String leftShift(String message){
        String firstChar = message.substring(0, 1);
        return message.substring(1, message.length()) + firstChar;
    }
}
