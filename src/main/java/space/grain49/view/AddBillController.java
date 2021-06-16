package space.grain49.view;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import space.grain49.Main;
import space.grain49.server.BillDbUtil;
import space.grain49.server.DBUtil;
import space.grain49.server.PaymentAccountDbUtil;
import space.grain49.server.SignInDbUtil.SignInData;
import space.grain49.server.model.Bill;
import space.grain49.server.model.PaymentAccount;


public class AddBillController {
    private BillDbUtil sBillDbUtil = BillDbUtil.get();
    private PaymentAccountDbUtil sPaymentAccountDbUtil = PaymentAccountDbUtil.get();

    @FXML
    JFXComboBox<PaymentAccount> paymentAccountComboBox;

    @FXML
    JFXTimePicker timePicker;

    @FXML
    JFXDatePicker datePicker;

    @FXML
    JFXTextField typeField;

    @FXML
    JFXTextField moneyField;

    @FXML
    JFXTextField noteField;

    @FXML
    JFXRadioButton expend;

    public void addBill() {
        if (SignInData.isSignIn()) {
            PaymentAccount paymentAccount = paymentAccountComboBox.getValue();

            LocalDateTime localDateTime = datePicker.getValue().atTime(timePicker.getValue());
            java.util.Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp timestamp = new Timestamp(date.getTime());

            double money = Math.abs(Double.parseDouble(moneyField.getText()));
            String type = typeField.getText();
            String note = noteField.getText();
            String paymentAccountID = paymentAccount.getUuid();

            try {
                paymentAccount.setMoney(money + paymentAccount.getMoney());
                sPaymentAccountDbUtil.updatePaymentAccount(paymentAccount);
                if (expend.isSelected()) {
                    money = -1 * money;
                    Bill bill = new Bill(DBUtil.getUUID32(), money, type, paymentAccountID, null, note, timestamp,
                            SignInData.getAccount());
                    sBillDbUtil.insert(bill);
                } else {
                    Bill bill = new Bill(DBUtil.getUUID32(), money, type, null, paymentAccountID, note, timestamp,
                            SignInData.getAccount());
                    sBillDbUtil.insert(bill);
                }
                ((Stage) expend.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            
        } else {
            Main.displayWorrying();
        }
    }


    public void initialize() {
        initPaymentAccount();
    }

    public void initPaymentAccount() {
        PaymentAccountDbUtil sPaymentAccountDbUtil = PaymentAccountDbUtil.get();
        List<PaymentAccount> paymentAccounts = sPaymentAccountDbUtil.select(SignInData.getAccount());
        for (PaymentAccount paymentAccount : paymentAccounts) {
            paymentAccountComboBox.getItems().add(paymentAccount);
        }
        timePicker.set24HourView(true);
        timePicker.setValue(LocalTime.now());
        datePicker.setValue(LocalDate.now());
    }
}
