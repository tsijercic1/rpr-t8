package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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
                x.setTitle("Slanje pošte");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sendWindow.fxml"));
                loader.setController(new SendWindowController());
                Parent root2 = null;
                try {
                    root2 = loader.load();
                    if(root2==null){
                        System.out.println("fakat jes null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                x.setScene(new Scene( root2,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
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
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            File[] child = new File(parent).listFiles();
            if (child != null) {
                if(child.length!=0){
                    for (File aChild : child) {
                        if (aChild.getName().contains(name) && aChild.isFile()) {
                            Platform.runLater(()-> {
                                model.addPut(aChild.getAbsolutePath());
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
        myThread.start();
    }
    public void doAbort(ActionEvent actionEvent ){
        stopBtn.setDisable(true);
        findBtn.setDisable(false);
    }
}
