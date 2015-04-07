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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXML_PinController implements Initializable {

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private Label firstDigit;
    @FXML
    private Label secondDigit;
    @FXML
    private Label thirdDigit;
    @FXML
    private Label fourthDigit;

    private int digitState;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        nextWindow();
    
    }

    private void nextWindow() throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXML_OptionPage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) firstDigit.getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            KeyPadListener.getListener().setKeyPressedListener(new ButtonPressedListener() {

                @Override
                public void buttonPressed(char character) {

                    String key = "" + character;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (character == '#') {
                                try {
                                    nextWindow();
                                } catch (IOException ex) {
                                    Logger.getLogger(FXML_PinController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            switch (digitState) {
                                case 0:
                                    firstDigit.setText(key);
                                    break;
                                case 1:
                                    secondDigit.setText(key);
                                    break;
                                case 2:
                                    thirdDigit.setText(key);
                                    break;
                                case 3:
                                    fourthDigit.setText(key);
                                    break;
                            }
                            digitState++;

                        }
                    });

                }
            });
        } catch (Exception e) {

        }
    }

}
