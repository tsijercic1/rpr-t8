package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {
    public static Stage m;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FileListModel model = new FileListModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/finder.fxml"));
        loader.setController(new FinderController(model));
        Parent root = loader.load();
        primaryStage.setTitle("Pretraga datoteka");
        m=primaryStage;
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
    }
















    public static void main(String[] args) {
        launch(args);
    }
}
