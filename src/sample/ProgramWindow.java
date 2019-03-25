package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProgramWindow {
    public static Stage mainStage = null;
    public static Group root = null;
    public static Scene scene = null;
    public static BorderPane borderPane = null;
    public static GridPane inputsGrid = null;

    private static MenuBar mainMenu = null;
    private static Menu openFromFile = null;
    private static Menu cryptography = null;

    private static MenuItem encrypt = null;
    private static MenuItem decrypt = null;
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
        textToEncryption = new TextArea();
        encryptedText = new TextArea();

        inputsGrid.add(labelTextToEncryption, 1, 1);
        inputsGrid.add(textToEncryption, 1, 2);

        inputsGrid.add(labelEncryptedText, 2, 1);
        inputsGrid.add(encryptedText, 2, 2);

        borderPane.setCenter(inputsGrid);
    }

    public void setupMenu() {
        mainMenu = new MenuBar();
        openFromFile = new Menu("Open from file");
        cryptography = new Menu("Cryptography");

        encrypt = new MenuItem("Encrypt") ;
        decrypt = new MenuItem("Decrypt") ;

        cryptography.getItems().addAll(encrypt, decrypt);

        mainMenu.getMenus().addAll(openFromFile, cryptography);

        borderPane.setTop(mainMenu);
        root.getChildren().add(borderPane);
    }
}
