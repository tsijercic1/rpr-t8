package ba.unsa.etf.rpr.tutorijal8;

import com.sun.security.ntlm.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.InputStream;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
                System.exit(0);
            }
        });
//
//        URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=71301");
//        InputStream tok = url.openStream();
//        int size = tok.available();
//        String rezultat = "";
//        for(int i=0;i<size;i++){
//            rezultat += (char)tok.read();
//        }
//        System.out.println(rezultat);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
