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
        Parent root = FXMLLoader.load(getClass().getResource("/finder.fxml"));
        primaryStage.setTitle("Pretraga datoteka");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
        File[] file = File.listRoots();
        for(int i=0;i<file.length;i++){
            File[] files = file[i].listFiles();
            for(int j=0;j<files.length;j++){
                if(files[j].getName().equals("home")){
                    System.out.print(files[j].getName() + "-- with path : ");
                    System.out.println(files[j].getAbsolutePath());
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
