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
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.nio.file.Files;
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
    private static MenuItem readFile = null;
    private static MenuItem saveToFile = null;
    private static MenuItem saveDecryptedFile = null;

    private static Menu cryptography = null;
    private static MenuItem encrypt = null;
    private static MenuItem decrypt = null;

    private static TextField keyInput = null;

    private static TextArea originalText = null;
    private static TextArea outputText = null;
    private static Button actionButton = null;

    private static CheckBox shouldPutTextToTextArea = new CheckBox("Should put data as files");

    private static String sadTextToEncrypt = "";
    private static String sadTextToSaveDecrypt = "";

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
        inputsGrid.add(shouldPutTextToTextArea, 4,1);
        actionButton.setOnAction(event -> {
            if(this.validKey()){
                this.encryptMessage();
            }
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
        if (shouldPutTextToTextArea.isSelected()) {
            String key = keyInput.getText();
            sadTextToSaveDecrypt = Algorithm.encryptMessage(sadTextToEncrypt, key);
            saveToFile.fire();
        } else {
            String message = originalText.getText();
            String key = keyInput.getText();
            String output = Algorithm.encryptMessage(message, key);

            outputText.setText(StringEscapeUtils.escapeJava(output));
        }
    }

    private void decryptMessage(){
        if (shouldPutTextToTextArea.isSelected()) {
            openFromFile.fire();
            String key = keyInput.getText();
            sadTextToSaveDecrypt = Algorithm.decryptMessage(sadTextToEncrypt, key);
            saveToFile.fire();
        } else {
            String message = originalText.getText();

            String key = keyInput.getText();
            String output = Algorithm.decryptMessage(StringEscapeUtils.unescapeJava(message), key);
            outputText.setText(output);
        }
    }

    private void setEncryption(){
        String originalText = "Text to encrypt";
        String output = "Encrypted text";
        String button = "Encrypt";
        outputText.setText("");
        this.setLabels(originalText, output, button);
        actionButton.setOnAction(event -> {
            if(this.validKey()){
                this.encryptMessage();
            }
        });
    }

    private void setDecryption(){
        String originalText = "Text to decrypt";
        String output = "Decrypted text";
        String button = "Decrypt";
        outputText.setText("");
        this.setLabels(originalText, output, button);
        actionButton.setOnAction(event -> {
            if(this.validKey()){
                this.decryptMessage();
            }
        });
    }

    private boolean validKey(){
        String key = keyInput.getText();
        if(key.length() != 8){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Key must have 8 chars!");
            a.show();
            return false;
        }
        return true;
    }

    public void setupMenu() {
        mainMenu = new MenuBar();
        openFromFile = new Menu("File");
        readFile = new MenuItem("Read from file");
        saveToFile = new MenuItem("Save output to file");

        readFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            try {
                if(shouldPutTextToTextArea.isSelected()) {
                    FileInputStream fis = new FileInputStream(selectedFile);
                    DataInputStream dis = new DataInputStream(fis);
                    int bytes[] = new int[dis.available()];
                    int i = 0;
                    sadTextToEncrypt = "";
                    while(dis.available() > 0) {
                        sadTextToEncrypt += (char)dis.readUnsignedByte();
                        i++;
                    }

                } else {
                    List<String> lines = Files.readAllLines(selectedFile.toPath());
                    String wholeFileInText = lines.stream().collect(Collectors.joining(System.lineSeparator()));
                    originalText.setText(wholeFileInText);
                }
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(e.toString());
            }

        });

        saveToFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(mainStage);

            if (shouldPutTextToTextArea.isSelected()) {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    DataOutputStream dos = new DataOutputStream(fos);

                    dos.writeBytes(sadTextToSaveDecrypt);
                    dos.close();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if(file != null){
                    SaveFile(outputText.getText(), file);
                }
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
        openFromFile.getItems().addAll(readFile, saveToFile);

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
