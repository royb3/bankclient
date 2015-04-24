/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private char[] pincode;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        nextWindow();
    }

    private void nextWindow() throws IOException {
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
        pincode = new char[]{'-', '-', '-', '-'};

        try {
            KeyPadListener.getListener().setKeyPressedListener(new ButtonPressedListener() {

                @Override
                public void buttonPressed(char character) {

                    char key = character;
                    Platform.runLater(new Runnable() {
                        public boolean hasItem(char[] characters, char c) {
                            for (int i = 0; i < characters.length; i++) {
                                if (characters[i] == '-') {
                                    return true;
                                }
                            }
                            return false;
                        }

                        @Override
                        public void run() {
                            if (character == '#') {
                                if (!hasItem(pincode, '-')) {
                                    try {
                                        System.out.println(String.format("Digits: %s", new String(pincode)));
                                        String rekeningnummer = KeyPadListener.getListener().getAccountID().substring(4);
                                        if(ApiClient.getApiClient().authorize(rekeningnummer, KeyPadListener.getListener().getCardNumber(), new String(pincode))){
                                            nextWindow();
                                        }else{
                                            pincode = new char[]{'-','-', '-', '-'};
                                            firstDigit.setText("O");
                                            secondDigit.setText("O");
                                            thirdDigit.setText("O");
                                            fourthDigit.setText("O");
                                            digitState = 0;
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(FXML_PinController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (Exception ex) {
                                        Logger.getLogger(FXML_PinController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }else{
                                switch (digitState) {
                                    case 0:
                                        firstDigit.setText("X");
                                        pincode[0] = key;
                                        break;
                                    case 1:
                                        secondDigit.setText("X");
                                        pincode[1] = key;
                                        break;
                                    case 2:
                                        thirdDigit.setText("X");
                                        pincode[2] = key;
                                        break;
                                    case 3:
                                        fourthDigit.setText("X");
                                        pincode[3] = key;
                                        break;
                                }
                                digitState++;
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {

        }
    }

}
