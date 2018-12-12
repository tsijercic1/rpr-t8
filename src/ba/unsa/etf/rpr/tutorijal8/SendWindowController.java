package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SendWindowController implements Initializable {
    public Button sendBtn;
    public TextField postanskiBroj;
    public SendWindowController(){}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sendBtn.setDisable(true);
        postanskiBroj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oldValue && !newValue){
                    //validiraj
                    Validation validacija = new Validation(postanskiBroj.getText());
                    Thread myThread = new Thread(validacija);
                    postanskiBroj.setDisable(true);
                    myThread.start();
                }
            }
        });
    }

    public class Validation implements Runnable{
        String what;
        public Validation(String x){
            what=x;
        }

        @Override
        public void run() {
            try {
                validate(what);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void validate(String postanski) throws IOException {
            URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj="+postanski);
            InputStream tok = url.openStream();
            int size = tok.available();
            String rezultat = "";
            for(int i=0;i<size;i++){
                rezultat += (char)tok.read();
            }

                if (rezultat.equals("OK")) {
                    Platform.runLater(()->{
                        sendBtn.setDisable(false);
                        postanskiBroj.getStyleClass().removeAll("invalid");
                        postanskiBroj.getStyleClass().add("valid");
                    });
                } else {
                    Platform.runLater(()-> {
                        sendBtn.setDisable(true);
                        postanskiBroj.getStyleClass().removeAll("valid");
                        postanskiBroj.getStyleClass().add("invalid");
                    });
                }
                Platform.runLater(()->{
                    postanskiBroj.setDisable(false);
                });
        }
    }

    public void send(ActionEvent actionEvent){
        Stage prozor = (Stage) postanskiBroj.getScene().getWindow();
        prozor.close();
    }


}
