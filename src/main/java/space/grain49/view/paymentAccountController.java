package space.grain49.view;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import space.grain49.server.PaymentAccountDbUtil;
import space.grain49.server.SignInDbUtil.SignInData;
import space.grain49.server.model.PaymentAccount;

public class paymentAccountController {

    @FXML
    private Text money;

    @FXML
    private JFXTreeTableView<PaymentAccountAdapter> tableView;

    @FXML
    private JFXTreeTableColumn<PaymentAccountAdapter, String> nameColumn;

    @FXML
    private JFXTreeTableColumn<PaymentAccountAdapter, String> moneyColumn;

    @FXML
    private VBox loadingTips;

    public void initialize() {
        loadingTips.setVisible(true);
        ObservableList<PaymentAccountAdapter> paymentAccountAdapters = FXCollections.observableArrayList();
        getData(paymentAccountAdapters);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PaymentAccountAdapter, String> param) -> {
            if (nameColumn.validateValue(param)) {
                return param.getValue().getValue().nameProperty();
            } else {
                return nameColumn.getComputedValue(param);
            }
        });

        moneyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PaymentAccountAdapter, String> param) -> {
            if (moneyColumn.validateValue(param)) {
                return param.getValue().getValue().moneyProperty();
            } else {
                return moneyColumn.getComputedValue(param);
            }
        });

        final TreeItem<PaymentAccountAdapter> root = new RecursiveTreeItem<>(paymentAccountAdapters,
                RecursiveTreeObject::getChildren);

        tableView.setRoot(root);
        tableView.setShowRoot(false);
        tableView.setEditable(false);

    }

    private double calculateSum(List<PaymentAccount> paymentAccounts) {
        int sum = 0;
        for (PaymentAccount paymentAccount : paymentAccounts) {
            sum += paymentAccount.getMoney();
        }
        return sum;
    }

    private static final class PaymentAccountAdapter extends RecursiveTreeObject<PaymentAccountAdapter> {
        final StringProperty name;
        final StringProperty money;

        public PaymentAccountAdapter(String name, String money) {
            this.name = new SimpleStringProperty(name);
            this.money = new SimpleStringProperty(money);
        }

        public StringProperty nameProperty() {
            return name;
        }

        public StringProperty moneyProperty() {
            return money;
        }
    }

    public void addPaymentAccount() throws IOException {
        Stage windows = new Stage();
        Pane contentPage = FXMLLoader.load(getClass().getResource("./??????????????????.fxml"));
        Scene scene = new Scene(contentPage);
        windows.setScene(scene);
        windows.show();
    }

    private void getData(ObservableList<PaymentAccountAdapter> paymentAccountAdapters) {
        Thread thread = new Thread() {
            @Override
            public void run() {

                PaymentAccountDbUtil sPaymentAccountDbUtil = PaymentAccountDbUtil.get();
                List<PaymentAccount> paymentAccounts = sPaymentAccountDbUtil.select(SignInData.getAccount());
                for (PaymentAccount paymentAccount : paymentAccounts) {
                    paymentAccountAdapters.add(new PaymentAccountAdapter(paymentAccount.getName(),
                            Double.toString(paymentAccount.getMoney())));
                }
                Platform.runLater(() -> {
                    money.setText(Double.toString(calculateSum(paymentAccounts)));
                    loadingTips.setVisible(false);
                });
            }

        };
        thread.setName("thread1");
        thread.start();
    }
}