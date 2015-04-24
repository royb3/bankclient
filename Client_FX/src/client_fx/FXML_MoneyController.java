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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXML_MoneyController implements Initializable {

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private AnchorPane pane;
    @FXML
    private Label optionA;
    @FXML
    private Label optionB;
    @FXML
    private Label optionC;
    @FXML
    private Label customAmountLabel;
    private String customAmount = "";

    public void nextWindow(String document) throws IOException {
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
            String rekeningnummer = KeyPadListener.getListener().getAccountID().substring(4);
            double maximumWithdraw = ApiClient.getApiClient().getMaximumWithdraw(rekeningnummer);
            if (maximumWithdraw > 100000) {
                optionA.setText("€20,-");
                optionB.setText("€50,-");
                optionC.setText("€100,-");
            } else if (maximumWithdraw > 10000) {
                optionA.setText("€10,-");
                optionB.setText("€20,-");
                optionC.setText("€50,-");
            } else if (maximumWithdraw > 5000) {
                optionA.setText("€10,-");
                optionB.setText("€20,-");
                optionC.setText("");
            } else if (maximumWithdraw > 1000) {
                optionA.setText("€10,-");
                optionB.setText("");
                optionC.setText("");
            } else if (maximumWithdraw < 1000) {
                optionA.setText("");
                optionB.setText("");
                optionC.setText("");
            }
        } catch (Exception ex) {
            Logger.getLogger(FXML_MoneyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // TODO
            KeyPadListener.getListener().setKeyPressedListener(new ButtonPressedListener() {

                @Override
                public void buttonPressed(char character) {
                    final char key = character;
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                if (!(customAmount.equals("") && key == '0')) {
                                    if (key == '*') {
                                        customAmount = "";
                                        customAmountLabel.setText("€0,-");
                                    } else {
                                        customAmount += Integer.parseInt(new String(new char[]{key}));
                                        customAmountLabel.setText(String.format("€%s,-", customAmount));
                                    }
                                }
                            } catch (Exception e) {

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
