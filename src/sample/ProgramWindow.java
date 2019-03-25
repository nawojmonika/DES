package sample;

import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramWindow {
    public static Stage mainStage = null;
    public static Group root = null;
    public static Scene scene = null;
    public static BorderPane borderPane = null;
    public static GridPane inputsGrid = null;

    private static MenuBar mainMenu = null;
    private static Menu openFromFile = null;
    private static MenuItem openEncryptedFile = null;
    private static MenuItem openDecryptedFile = null;

    private static Menu cryptography = null;
    private static MenuItem encrypt = null;
    private static MenuItem decrypt = null;

    private static TextField key = null;

    private static TextArea textToEncryption = null;
    private static TextArea encryptedText = null;


    ProgramWindow(Stage mainStage) {
        this.mainStage = mainStage;
        mainStage.setTitle("DES program");
        root = new Group();
        scene = new Scene(root, 1200, 250);
        borderPane = new BorderPane();

        setupMenu();
        setupInputs();

        mainStage.setScene(scene);
        mainStage.show();
    }

    public void setupInputs() {
        inputsGrid = new GridPane();

        Label labelTextToEncryption = new Label("Text to encryption");
        Label labelEncryptedText = new Label("Encrypted text");
        Label labelKeyInput = new Label("Key input");
        inputsGrid.setHalignment(labelKeyInput, HPos.CENTER);

        key = new TextField();
        textToEncryption = new TextArea();
        encryptedText = new TextArea();


        inputsGrid.add(labelKeyInput, 1, 1);
        inputsGrid.add(key, 2, 1);

        inputsGrid.add(labelTextToEncryption, 1, 2);
        inputsGrid.add(textToEncryption, 1, 3);

        inputsGrid.add(labelEncryptedText, 2, 2);
        inputsGrid.add(encryptedText, 2, 3);

        borderPane.setCenter(inputsGrid);
    }

    public void setupMenu() {
        mainMenu = new MenuBar();
        openFromFile = new Menu("Open file");
        openEncryptedFile = new MenuItem("Open encrypted");
        openDecryptedFile = new MenuItem("Open decrypted");

        openEncryptedFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                String wholeFileInText = lines.stream().map(String::toString).collect(Collectors.joining(System.lineSeparator()));
                textToEncryption.setText(wholeFileInText);
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(e.toString());
            }
        });

        openDecryptedFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                String wholeFileInText = lines.stream().collect(Collectors.joining(System.lineSeparator()));
                encryptedText.setText(wholeFileInText);
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(e.toString());
            }

        });

        cryptography = new Menu("Cryptography");
        encrypt = new MenuItem("Encrypt") ;
        decrypt = new MenuItem("Decrypt") ;

        encrypt.setOnAction(event -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Tutaj dodać obsługę enkrypcji");
            a.show();
        });

        decrypt.setOnAction(event -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Tutaj dodać obsługę dekrypcji");
            a.show();
        });


        cryptography.getItems().addAll(encrypt, decrypt);
        openFromFile.getItems().addAll(openEncryptedFile, openDecryptedFile);

        mainMenu.getMenus().addAll(openFromFile, cryptography);

        borderPane.setTop(mainMenu);
        root.getChildren().add(borderPane);
    }
}
