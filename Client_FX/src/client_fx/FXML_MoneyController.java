/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import client_fx.api.WithdrawResponse;
import client_fx.api.ApiClient;
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
    private Label foutmelding;
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
        foutmelding.setVisible(false);
        try {
            String rekeningnummer = KeyPadListener.getListener().getAccountID().substring(4);
            double maximumWithdraw = ApiClient.getApiClient().getMaximumWithdraw(rekeningnummer);
            if (maximumWithdraw >= 100000) {
                optionA.setText("€20,-");
                optionB.setText("€50,-");
                optionC.setText("€100,-");
            } else if (maximumWithdraw >= 10000) {
                optionA.setText("€10,-");
                optionB.setText("€20,-");
                optionC.setText("€50,-");
            } else if (maximumWithdraw >= 5000) {
                optionA.setText("€5,-");
                optionB.setText("€10,-");
                optionC.setText("€20,-");
            } else if (maximumWithdraw >= 1000) {
                optionA.setText("€5,-");
                optionB.setText("€10,-");
                optionC.setText("");
            } else if (maximumWithdraw < 1000) {
                optionA.setText("€5,-");
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
                                if (!(customAmount.equals("") && (key == '0' || key == '*' || key == '#'))) {
                                    if (key == '*') {
                                        customAmount = "";
                                        customAmountLabel.setText("€0,-");
                                    } else if (key == 'A' && optionA.getText() != "") {
                                        if (!Transaction.transactionPending()) {
                                            Transaction.init();
                                        }
                                        Transaction.getCurrentTransaction().setAccountID(KeyPadListener.getListener().getAccountID());
                                        String optionAText = optionA.getText();
                                        Transaction.getCurrentTransaction().setAmmount(Long.parseLong(optionAText.substring(1, optionAText.length() - 2)) * 100);
                                    } else if (key == 'B' && optionB.getText() != "") {
                                        if (!Transaction.transactionPending()) {
                                            Transaction.init();
                                        }
                                        Transaction.getCurrentTransaction().setAccountID(KeyPadListener.getListener().getAccountID());
                                        String optionBText = optionB.getText();
                                        Transaction.getCurrentTransaction().setAmmount(Long.parseLong(optionBText.substring(1, optionBText.length() - 2)) * 100);
                                    } else if (key == 'C' && optionC.getText() != "") {
                                        if (!Transaction.transactionPending()) {
                                            Transaction.init();
                                        }
                                        Transaction.getCurrentTransaction().setAccountID(KeyPadListener.getListener().getAccountID());
                                        String optionCText = optionC.getText();
                                        Transaction.getCurrentTransaction().setAmmount(Long.parseLong(optionCText.substring(1, optionCText.length() - 2)) * 100);
                                    } else if (key == 'D'){
                                        if (customAmount != "")  {
                                            if (Integer.parseInt(customAmount) % 5 == 0) {
                                                if (!Transaction.transactionPending()) {
                                                    Transaction.init();
                                                }
                                                Transaction.getCurrentTransaction().setAccountID(KeyPadListener.getListener().getAccountID());
                                                Transaction.getCurrentTransaction().setAmmount(Long.parseLong(customAmount) * 100);
                                            } else {
                                                foutmelding.setText("Het bedrag is geen veelvoud van 5!");
                                                foutmelding.setVisible(true);
                                            }
                                        } else {
                                            foutmelding.setText("U moet een bedrag invullen!");
                                            foutmelding.setVisible(true);
                                        }
                                    } else {
                                        customAmount += Integer.parseInt(new String(new char[]{key}));
                                        customAmountLabel.setText(String.format("€%s,-", customAmount));
                                    }
                                }
                                if (Transaction.transactionPending()) {
                                    WithdrawResponse response = ApiClient.getApiClient().withdraw(Transaction.getCurrentTransaction().getAccountID(), Transaction.getCurrentTransaction().getAmmount());
                                    if (response != null) {
                                        Transaction.getCurrentTransaction().setID(response.getTransactionNumber());
                                        nextWindow("FXML_ReceiptPage.fxml");
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
