package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

import static sample.EncryptionUtils.*;

public class Main extends Application {
    private String originalMessage = "IEOFIT#1";
    private String key = "IEOFIT#1";
    private String encryptedMessage = "Ç3A\n×\u0087\u0088þ";


    @Override
    public void start(Stage primaryStage) throws Exception {
        ProgramWindow pw = new ProgramWindow(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
