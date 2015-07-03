/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import client_fx.api.ApiClient;
import client_fx.api.DispenzorApiClient;
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
                    long amount = Transaction.getCurrentTransaction().getAmmount();
                    if(!KeyPadListener.getListener().getAccountID().substring(0, 4).equals("PROH")) {
                        amount *= 100;
                    }
                    long brief = amount / 5000;
                    amount -= brief * 5000;
                    for (int i = 0; i < brief; i++) {
                        DispenzorApiClient.shootMoney("D");
                        Thread.sleep(2000);
                    }
                    brief = amount / 2000;
                    amount -= brief * 2000;
                    for (int i = 0; i < brief; i++) {
                        DispenzorApiClient.shootMoney("C");
                        Thread.sleep(2000);
                    }
                    brief = amount / 1000;
                    amount -= brief * 1000;
                    for (int i = 0; i < brief; i++) {
                        DispenzorApiClient.shootMoney("B");
                        Thread.sleep(2000);
                    }
                    brief = amount / 500;
                    amount -= brief * 500;
                    for (int i = 0; i < brief; i++) {
                        DispenzorApiClient.shootMoney("A");
                        Thread.sleep(2000);
                    }
                    if (Transaction.getCurrentTransaction().isPending()) {
                        try{
                            ApiClient.getApiClient().logout();
                        } catch (Exception ex){
                            ex.printStackTrace(System.out);
                        }
                        Thread.sleep(2500);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXML_EndController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FXML_EndController.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally{
                    Transaction.clearTransaction();

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
