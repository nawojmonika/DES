package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.BitSet;

public class Main extends Application {

    private static int[] IP = {58, 50, 42, 34, 26, 18, 10, 2,
                         60, 52, 44, 36, 28, 20, 12, 4,
                         62, 54, 46, 38, 30, 22, 14, 6,
                         64, 56, 48, 40, 32, 24, 16, 8,
                         57, 49, 41, 33, 25, 17, 9, 1,
                         59, 51, 43, 35, 27, 19, 11, 3,
                         61, 53, 45, 37, 29, 21, 13, 5,
                         63, 55, 47, 39, 31, 23, 15, 7};

    private String originalMessage = "Ala ma kota";
    private String key = "IEOFIT#1";


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DES encryptor");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        getEncryptedKey(this.key);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void getEncryptedKey(String key){
       String biteKey = this.getBites(key);
       this.initialPermutaion(biteKey);
    }

    private String getBites(String message){
        String biteMessage= "";
        System.out.println(message);

        byte[] ascii = message.getBytes();
        for (byte character : ascii) {
            System.out.println((int)character);
            String binary = Integer.toString((int)character, 2);
            for (int i = 8 - binary.length(); i > 0; i--  ) {
                binary = "0" + binary;
            }
            biteMessage += ' ' + binary;
        }
        return biteMessage;
    }

    private void initialPermutaion(String biteMessage){
        String permutationMessage = "";
        for (int index:this.IP) {
    }

    }
}
