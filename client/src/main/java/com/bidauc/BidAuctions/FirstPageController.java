package com.bidauc.BidAuctions;

import com.mysql.cj.xdevapi.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class FirstPageController {

    @FXML
    private TextField userNameTxt;

    @FXML
    private TextField passWordTxt;

    @FXML
    private TextField updatepriceTXT;

    @FXML
    private Label currentpriceLBL;

    private double currentPrice=0.0;

    @FXML
    private Label err1Lbl;

    @FXML
    private TextField IDText;

    @FXML
    void logninOP(ActionEvent event) throws IOException {
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#getclient%%" + userNameTxt.getText() + "%%"
                + passWordTxt.getText());
    }

    @FXML
    void updatepriceOP(ActionEvent event) throws IOException {
        /*double updatePrice=Double.valueOf(updatepriceTXT.getText());
        if(updatePrice <= currentPrice) {
            err1Lbl.setVisible(true);
            return;
        }
        err1Lbl.setVisible(false);
        currentPrice=updatePrice;
        SimpleClient.getClient().sendToServer("#updatePrice%%" + updatepriceTXT.getText());*/
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#getclient" + updatepriceTXT.getText());
    }

    @FXML
    void CheckFunc(ActionEvent event) throws IOException {
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#getclient" + IDText.getText());
    }

    public void setData(String str) {
        currentpriceLBL.setText(str);
    }
}