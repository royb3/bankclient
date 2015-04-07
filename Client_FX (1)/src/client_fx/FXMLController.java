package client_fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private void handleButtonAction(ActionEvent event) throws SerialPortException, IOException {
        
        if(comboBox.getValue() != null)
        {
           KeyPadListener.getListener(comboBox.getValue());
        }
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXML_HomePage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] names = SerialPortList.getPortNames();
        
        comboBox.getItems().addAll(names);
    }    
}
