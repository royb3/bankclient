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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXML_EndController implements Initializable {

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private AnchorPane pane;
     
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXML_EndController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXML_HomePage.fxml"));
                            Scene home_page_scene = new Scene(home_page_parent);
                            Stage app_stage = (Stage) pane.getScene().getWindow();
                            app_stage.hide();
                            app_stage.setScene(home_page_scene);
                            app_stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(FXML_EndController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }).start();
    }    
    
}
