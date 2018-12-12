package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FinderController implements Initializable {
    private FileListModel model;
    public Button findBtn;
    public TextField inputField;
    public ListView<String> listaPuteva;
    public File root = new File("/home/tsijercic1");
    public Button stopBtn;

    public FinderController(FileListModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaPuteva.setItems(model.getPutevi());
        listaPuteva.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                Stage x =new Stage();
                x.setTitle("prozor");
                Parent root2 = new Parent() {
                    @Override
                    public void impl_updatePeer() {
                        super.impl_updatePeer();
                    }
                };
                x.setScene(new Scene( root2,300,300));
                x.initOwner(Main.m.getScene().getWindow());
                x.initModality(Modality.APPLICATION_MODAL);
                x.show();
            }
        });

    }


    public class Finder implements Runnable{

        @Override
        public void run() {
            find(inputField.getText(),root.getAbsolutePath());
        }

        public void find(String name, String parent){
            if(stopBtn.isDisabled()){
//                findBtn.setDisable(false);
                //                    Thread.currentThread().join();
                Thread.currentThread().stop();
            }
            File[] child = new File(parent).listFiles();
            if (child != null) {
                if(child.length!=0){
                    for (File aChild : child) {
                        if (aChild.getName().contains(name) && aChild.isFile()) {
                            //System.out.println(aChild.getName());
                            Platform.runLater(()-> {
                                model.addPut(aChild.getAbsolutePath());
                                //listaPuteva.getItems().setAll(model.getPutevi());
                            });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (aChild.isDirectory()) {
                            find(name, aChild.getAbsolutePath());
                        }
                    }
                }
            }
            if(parent.equals(root.getAbsolutePath())){
                findBtn.setDisable(false);
            }
        }

    }
    public void doFind(ActionEvent actionEvent) {
        model.deletePutevi();
        findBtn.setDisable(true);
        stopBtn.setDisable(false);
        Finder myFinder = new Finder();
        Thread myThread = new Thread(myFinder);
//        Platform.runLater(myThread);
        myThread.start();

    }
    public void doAbort(ActionEvent actionEvent ){
        stopBtn.setDisable(true);
        findBtn.setDisable(false);
    }

}
