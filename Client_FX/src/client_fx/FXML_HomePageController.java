/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXML_HomePageController implements Initializable {

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
            
    @FXML
    private void sendPayload(MouseEvent event) throws Exception{
        KeyPadListener.getListener().setCardSwipedListener(null);
        nextWindow("FXML_CardSetup.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            KeyPadListener listener = KeyPadListener.getListener();
  
            listener.ResetCardReader();
            listener.setCardSwipedListener(new CardSwipeListener() {
                
                @Override
                public void CardSwiped(String rekeningnummer) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                nextWindow("FXML_PinPage.fxml");
                            } catch (IOException ex) {
                                Logger.getLogger(FXML_HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                    });
                }
                
            });
        } catch (Exception ex) {
            Logger.getLogger(FXML_HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
