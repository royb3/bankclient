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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Roy
 */
public class FXML_CardSetupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private boolean written = false;
    @FXML
    private ComboBox<String> banks;
    @FXML
    private TextField number;           
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField cardNumber;
    @FXML
    private void OkButtonPressed(ActionEvent event) throws Exception{
        byte[] data = new byte[16];
        String bankCode = banks.getValue(); 
        int i = 0;
        while(i < bankCode.length())
        {
            data[i] = (byte) bankCode.charAt(i++);
        }
        int j = 14 - i - number.getText().length();
        while(j-- > 0){
            data[i++] = '0';
        }
        j = 0;
        while(i < 14)
        {
            data[i++] = (byte)number.getText().charAt(j++);
        }
        data[14] = (byte)cardNumber.getText().charAt(0);
        data[15] = (byte)cardNumber.getText().charAt(1);
        KeyPadListener.getListener().WriteDataToCard(data);
        written = true;
    }
    
    public void nextWindow(String document) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(document));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) pane.getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        banks.getItems().add("PROH");
        banks.getItems().add("SKER");
        banks.getItems().add("COPO");
        banks.getItems().add("ILMG");
        banks.getItems().add("ATMB");
        banks.getItems().add("MLBI");
        try {
            KeyPadListener.getListener().setCardSwipedListener(new CardSwipeListener() {
                
                @Override
                public void CardSwiped(String rekeningnummer) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(written){
                                    nextWindow("FXML_HomePage.fxml");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(FXML_CardSetupController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(FXML_CardSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
