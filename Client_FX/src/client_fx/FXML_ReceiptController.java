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

public class FXML_ReceiptController implements Initializable {

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private AnchorPane pane;
    
    
    public void nextWindow(String document) throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource(document));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) pane.getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            KeyPadListener.getListener().setKeyPressedListener(new ButtonPressedListener() {
                
                @Override
                public void buttonPressed(char character) {
                    final char key = character;
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            try{
                                switch(key)
                                {
                                    case 'A':
                                        PrinterTest p = new PrinterTest();
                                        nextWindow("FXML_EndPage.fxml");
                                        break;
                                    case 'B':
                                        nextWindow("FXML_EndPage.fxml");
                                        //onthoudKeuzeVoorReceipt();
                                        break;
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(FXML_OptionController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(FXML_OptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }      
    
}
