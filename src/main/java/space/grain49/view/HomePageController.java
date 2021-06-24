package space.grain49.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXRadioButton;
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
import space.grain49.server.BillDbUtil;
import space.grain49.server.PaymentAccountDbUtil;
import space.grain49.server.SignInDbUtil.SignInData;
import space.grain49.server.model.Bill;
import space.grain49.server.model.PaymentAccount;

public class HomePageController {
    @FXML
    private VBox loadingTips;
    @FXML
    private JFXTreeTableColumn<BillAdapter, String> moneyColumn;
    @FXML
    private JFXTreeTableColumn<BillAdapter, String> accountPaymentColumn;
    @FXML
    private JFXTreeTableColumn<BillAdapter, String> typeColumn;
    @FXML
    private JFXTreeTableColumn<BillAdapter, String> IO;
    @FXML
    private JFXTreeTableColumn<BillAdapter, String> timeColumn;
    @FXML
    private JFXTreeTableView<BillAdapter> tableView;
    @FXML
    private Text sumText;
    @FXML
    private Text outText;
    @FXML
    private Text inText;
    @FXML
    private JFXRadioButton monthRadio;
    @FXML
    private JFXRadioButton yearRadio;

    private ObservableList<BillAdapter> billAdapters;

    public void initialize() {
        monthRadio.setSelected(true);
        loadingTips.setVisible(true);
        billAdapters = FXCollections.observableArrayList();
        getAllBillData();

        moneyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<BillAdapter, String> param) -> {
            if (moneyColumn.validateValue(param)) {
                return param.getValue().getValue().moneyProperty();
            } else {
                return moneyColumn.getComputedValue(param);
            }
        });

        accountPaymentColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<BillAdapter, String> param) -> {
            if (accountPaymentColumn.validateValue(param)) {
                return param.getValue().getValue().accountPaymentProperty();
            } else {
                return accountPaymentColumn.getComputedValue(param);
            }
        });

        typeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<BillAdapter, String> param) -> {
            if (typeColumn.validateValue(param)) {
                return param.getValue().getValue().typeProperty();
            } else {
                return typeColumn.getComputedValue(param);
            }
        });

        IO.setCellValueFactory((TreeTableColumn.CellDataFeatures<BillAdapter, String> param) -> {
            if (IO.validateValue(param)) {
                return param.getValue().getValue().IOProperty();
            } else {
                return IO.getComputedValue(param);
            }
        });

        timeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<BillAdapter, String> param) -> {
            if (timeColumn.validateValue(param)) {
                return param.getValue().getValue().timeProperty();
            } else {
                return timeColumn.getComputedValue(param);
            }
        });

        final TreeItem<BillAdapter> root = new RecursiveTreeItem<>(billAdapters, RecursiveTreeObject::getChildren);

        tableView.setRoot(root);
        tableView.setShowRoot(false);
        tableView.setEditable(false);
    }

    public void addBill() throws IOException {
        Stage windows = new Stage();
        Pane contentPage = FXMLLoader.load(getClass().getResource("./添加账单.fxml"));
        Scene scene = new Scene(contentPage);
        windows.setScene(scene);
        windows.show();
        windows.setOnHidden(e -> {
            getAllBillData();
        });
    }

    public void getAllBillData() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                billAdapters.clear();
                PaymentAccountDbUtil sPaymentAccountDbUtil = PaymentAccountDbUtil.get();
                List<PaymentAccount> paymentAccounts = sPaymentAccountDbUtil.select(SignInData.getAccount());
                Map<String, String> map = new HashMap<String, String>();
                for (PaymentAccount paymentAccount : paymentAccounts) {
                    map.put(paymentAccount.getUuid(), paymentAccount.getName());
                }
                {
                    BillDbUtil sBillDbUtil = BillDbUtil.get();
                    List<Bill> bills = sBillDbUtil.select(SignInData.getAccount());
                    for (Bill bill : bills) {
                        if (bill.getMoney() < 0)
                            billAdapters.add(new BillAdapter(bill.getType(), Double.toString(bill.getMoney()),
                                    map.get(bill.getAccountOut()), "支出", bill.timestampToString()));
                        else
                            billAdapters.add(new BillAdapter(bill.getType(), Double.toString(bill.getMoney()),
                                    map.get(bill.getAccountIn()), "收入", bill.timestampToString()));
                    }
                }
            }
        };
        thread.setName("thread1");
        thread.start();
        getBalance();
    }

    public void getBalance() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                double out = 0, in = 0;
                final double newOut, newIn;

                BillDbUtil sBillDbUtil = BillDbUtil.get();
                SimpleDateFormat sdf = new SimpleDateFormat();
                Calendar ca = Calendar.getInstance();
                if (monthRadio.isSelected())
                    ca.add(Calendar.MONTH, 1);
                else
                    ca.add(Calendar.YEAR, 1);
                sdf.applyPattern("yyyy-MM-01");
                List<Bill> bills = sBillDbUtil.selectByDate(SignInData.getAccount(), sdf.format(new Date()),
                        sdf.format(ca.getTime()));
                for (Bill bill : bills) {
                    if (bill.getMoney() < 0)
                        out += Math.abs(bill.getMoney());
                    else
                        in += bill.getMoney();
                }

                newOut = out;
                newIn = in;
                Platform.runLater(() -> {
                    sumText.setText(Double.toString(newIn - newOut));
                    outText.setText(Double.toString(newOut));
                    inText.setText(Double.toString(newIn));
                    loadingTips.setVisible(false);
                });
            }

        };
        thread.setName("threadGetBalance");
        thread.start();
    }

    public class BillAdapter extends RecursiveTreeObject<BillAdapter> {
        final StringProperty type;
        final StringProperty money;
        final StringProperty accountPayment;
        final StringProperty IO;
        final StringProperty time;

        public BillAdapter(String type, String money, String account, String IO, String time) {
            this.type = new SimpleStringProperty(type);
            this.money = new SimpleStringProperty(money);
            this.accountPayment = new SimpleStringProperty(account);
            this.IO = new SimpleStringProperty(IO);
            this.time = new SimpleStringProperty(time);
        }

        public StringProperty typeProperty() {
            return type;
        }

        public StringProperty moneyProperty() {
            return money;
        }

        public StringProperty accountPaymentProperty() {
            return accountPayment;
        }

        public StringProperty IOProperty() {
            return IO;
        }

        public StringProperty timeProperty() {
            return time;
        }
    }
}
