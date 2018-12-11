package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FinderController implements Initializable {
    private FileListModel model;
    public Button findBtn;
    public TextField inputField;
    public ListView<String> listaPuteva;
    public File root = new File("/home");

    public FinderController(FileListModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // Bindings.bindContentBidirectional(model.getPutevi(),listaPuteva.getItems());
    }


    public class Finder implements Runnable{

        @Override
        public void run() {
            find(inputField.getText(),root.getAbsolutePath());
        }

        public void find(String name, String parent){
            File[] child = new File(parent).listFiles();
            if (child != null) {
                if(child.length!=0){
                    for (File aChild : child) {
                        if (aChild.getName().contains(name) && aChild.isFile()) {
                            //System.out.println(aChild.getName());
                            model.addPut(aChild.getAbsolutePath());
                            listaPuteva.getItems().setAll(model.getPutevi());
                        }
                        if (aChild.isDirectory()) {
                            find(name, aChild.getAbsolutePath());
                        }
                    }
                }
            }
        }

    }
    public void doFind(ActionEvent actionEvent) {
        model.deletePutevi();
        Finder myFinder = new Finder();
        Thread myThread = new Thread(myFinder);
        Platform.runLater(myThread);
    }

}
