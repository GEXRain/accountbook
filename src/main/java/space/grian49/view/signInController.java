package space.grian49.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import space.grian49.server.SignIn;

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
    void clicked(ActionEvent event) {
        if(SignIn.signIn(account_number.getText(), password.getText())){
            SignIn.SignInData.setAccount(account_number.getText());
            SignIn.SignInData.setPassword(password.getText());
            SignIn.SignInData.setisSignIn();
        }
        else{
            password.setStyle("-fx-prompt-text-fill:#ab3232");
            password.setPromptText("密码或账号错误");
            password.setText("");
        }
        
    }

    public void initialize() {

    }
}