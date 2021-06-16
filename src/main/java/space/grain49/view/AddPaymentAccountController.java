package space.grain49.view;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import space.grain49.Main;
import space.grain49.server.DBUtil;
import space.grain49.server.PaymentAccountDbUtil;
import space.grain49.server.SignInDbUtil.SignInData;
import space.grain49.server.model.PaymentAccount;

import javafx.stage.Stage;

public class AddPaymentAccountController {
    @FXML
    JFXTextField nameField;
    @FXML
    JFXTextField moneyField;
    @FXML
    JFXTextField typeField;
    @FXML
    JFXTextField noteField;

    public void AddPaymentAccount() {
        if (SignInData.isSignIn()) {
            try {
                PaymentAccountDbUtil sPaymentAccountDbUtil = PaymentAccountDbUtil.get();
                double money = Math.abs(Double.parseDouble(moneyField.getText()));
                String type = typeField.getText();
                String name = nameField.getText();
                String note = noteField.getText();
                PaymentAccount paymentAccount = new PaymentAccount(DBUtil.getUUID32(), name, type, money, note,
                        SignInData.getAccount());
                sPaymentAccountDbUtil.insert(paymentAccount);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Main.displayWorrying();
        }
        ((Stage) nameField.getScene().getWindow()).close();
    }

    public void initialize() {

    }
}
