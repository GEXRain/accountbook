package space.grain49.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import space.grain49.server.SignInDbUtil;
import space.grain49.server.SignInDbUtil.SignInData;

/**
 * signIn.Controller
 */
public class signInController {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton button;
    @FXML
    private JFXTextField account_number;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private VBox list;

    @FXML
    void clicked(ActionEvent event) {
        progressBar.setVisible(true);
        SignInDbUtil sInDbUtil = SignInDbUtil.get();
        if (sInDbUtil.signIn(account_number.getText(), password.getText())) {
            SignInDbUtil.SignInData.setAccount(account_number.getText());
            SignInDbUtil.SignInData.setPassword(password.getText());
            SignInDbUtil.SignInData.setisSignIn();
            progressBar.setVisible(false);
            showContent();

        } else {
            password.setStyle("-fx-prompt-text-fill:#ab3232");
            password.setPromptText("密码或账号错误");
            password.setText("");
            progressBar.setVisible(false);

        }

    }

    public void initialize() {
        progressBar.setVisible(false);
        if(SignInData.isSignIn()){
            showContent();
        }
    }

    private BorderPane showContent(){
        root.getChildren().clear();
        BorderPane content = new BorderPane();
        VBox vbox = new VBox();
        Text text1 = new Text("账号：");
        Text text2 = new Text(SignInDbUtil.SignInData.getAccount());
        vbox.getChildren().addAll(text1,text2);
        vbox.setMaxWidth(0);
        vbox.setMaxHeight(0);
        content.setCenter(vbox);
        BorderPane.setMargin(vbox, new Insets(-200,300,40,0));
        root.getChildren().add(content);
        AnchorPane.setRightAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        return content;
    }
}