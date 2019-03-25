package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProgramWindow {
    public static Stage mainStage = null;
    public static Group root = null;
    public static Scene scene = null;

    private static MenuBar mainMenu = null;
    private static Menu openFromFile = null;
    private static Menu cryptography = null;

    private static MenuItem encrypt = null;
    private static MenuItem decrypt = null;


    ProgramWindow(Stage mainStage) {
        this.mainStage = mainStage;
        mainStage.setTitle("DES program");
        root = new Group();
        scene = new Scene(root, 400, 250);

        setupMenu();

        mainStage.setScene(scene);
        mainStage.show();
    }

    public void setupMenu() {
        mainMenu = new MenuBar();
        openFromFile = new Menu("Open from file");
        cryptography = new Menu("Cryptography");

        encrypt = new MenuItem("Encrypt") ;
        decrypt = new MenuItem("Decrypt") ;

        cryptography.getItems().addAll(encrypt, decrypt);

        mainMenu.getMenus().addAll(openFromFile, cryptography);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(mainMenu);
        root.getChildren().add(borderPane);
    }
}
