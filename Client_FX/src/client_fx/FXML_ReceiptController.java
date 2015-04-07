/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXML_ReceiptController implements Initializable {

    /**
     *
     * @param event
     * @throws IOException
     */
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXML_EndPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
