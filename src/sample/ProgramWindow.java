package sample;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Observable;
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
    private static MenuItem saveEncryptedFile = null;
    private static MenuItem saveDecryptedFile = null;

    private static Menu cryptography = null;
    private static MenuItem encrypt = null;
    private static MenuItem decrypt = null;

    private static TextField keyInput = null;

    private static TextArea originalText = null;
    private static TextArea outputText = null;
    private static Button actionButton = null;

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

        Label originalTextLabel = new Label("Text to encrypt");
        Label outputLabel = new Label("Encrypted text");
        Label labelKeyInput = new Label("Key input");
        actionButton = new Button("Encrypt");
        inputsGrid.setHalignment(labelKeyInput, HPos.CENTER);

        keyInput = new TextField();
        originalText = new TextArea();
        outputText = new TextArea();


        inputsGrid.add(labelKeyInput, 1, 1);
        inputsGrid.add(keyInput, 2, 1);

        inputsGrid.add(originalTextLabel, 1, 2);
        inputsGrid.add(originalText, 1, 3);

        inputsGrid.add(outputLabel, 2, 2);
        inputsGrid.add(outputText, 2, 3);

        inputsGrid.add(actionButton, 3,1);
        actionButton.setOnAction(event -> {
            this.encryptMessage();
        });
        borderPane.setCenter(inputsGrid);
    }

    private void setLabels(String originalText, String output, String button){
        ObservableList<Node> children = inputsGrid.getChildren();
        Label originalTextLabel = (Label) children.get(2);
        originalTextLabel.setText(originalText);

        Label outputLabel = (Label) children.get(4);
        outputLabel.setText(output);

        actionButton.setText(button);
    }

    private void encryptMessage(){
        String message = originalText.getText();
        String key = keyInput.getText();
        String output = Algorithm.encryptMessage(message, key);
        outputText.setText(output);
    }

    private void decryptMessage(){
        String message = originalText.getText();
        String key = keyInput.getText();
        String output = Algorithm.decryptMessage(message, key);
        outputText.setText(output);
    }

    private void setEncryption(){
        String originalText = "Text to encrypt";
        String output = "Encrypted text";
        String button = "Encrypt";
        outputText.setText("");
        this.setLabels(originalText, output, button);
        actionButton.setOnAction(event -> {
            this.encryptMessage();
        });
    }

    private void setDecryption(){
        String originalText = "Text to decrypt";
        String output = "Decrypted text";
        String button = "Decrypt";
        outputText.setText("");        this.setLabels(originalText, output, button);
        actionButton.setOnAction(event -> {
            this.decryptMessage();
        });
    }

    public void setupMenu() {
        mainMenu = new MenuBar();
        openFromFile = new Menu("File");
        openEncryptedFile = new MenuItem("Open encrypted");
        openDecryptedFile = new MenuItem("Open decrypted");

        saveEncryptedFile = new MenuItem("Save encrypted");
        saveDecryptedFile = new MenuItem("Save decrypted");

        openEncryptedFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                String wholeFileInText = lines.stream().map(String::toString).collect(Collectors.joining(System.lineSeparator()));
                outputText.setText(wholeFileInText);
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
                originalText.setText(wholeFileInText);
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(e.toString());
            }

        });

        saveEncryptedFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(mainStage);

            if(file != null){
                SaveFile(outputText.getText(), file);
            }
        });

        saveDecryptedFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(mainStage);

            if(file != null){
                SaveFile(originalText.getText(), file);
            }
        });

        cryptography = new Menu("Cryptography");
        encrypt = new MenuItem("Encrypt") ;
        decrypt = new MenuItem("Decrypt") ;

        encrypt.setOnAction(event -> {
            this.setEncryption();
        });

        decrypt.setOnAction(event -> {
            this.setDecryption();
        });


        cryptography.getItems().addAll(encrypt, decrypt);
        openFromFile.getItems().addAll(openEncryptedFile, openDecryptedFile, saveEncryptedFile, saveDecryptedFile);

        mainMenu.getMenus().addAll(openFromFile, cryptography);

        borderPane.setTop(mainMenu);
        root.getChildren().add(borderPane);
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
        }
    }
}
