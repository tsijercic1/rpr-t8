package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        FileListModel model = new FileListModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/finder.fxml"));
        loader.setController(new FinderController(model));
        Parent root = loader.load();
        primaryStage.setTitle("Pretraga datoteka");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
