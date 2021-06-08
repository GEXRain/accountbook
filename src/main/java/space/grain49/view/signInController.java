package space.grain49.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import space.grain49.server.SignIn;

/**
 * signIn.Controller
 */
public class signInController {
    @FXML
    private BorderPane root;
    @FXML
    private JFXButton button;
    @FXML
    private JFXTextField account_number;
    @FXML
    private JFXPasswordField password;
    @FXML
    private  JFXProgressBar progressBar;
    @FXML
    private VBox list;

    @FXML
    void clicked(ActionEvent event) {
        progressBar.setVisible(false);
        if(SignIn.signIn(account_number.getText(), password.getText())){
            SignIn.SignInData.setAccount(account_number.getText());
            SignIn.SignInData.setPassword(password.getText());
            SignIn.SignInData.setisSignIn();
            progressBar.setVisible(false);
        }
        else{
            password.setStyle("-fx-prompt-text-fill:#ab3232");
            password.setPromptText("密码或账号错误");
            password.setText("");
            progressBar.setVisible(false);
        }
        
    }

    public void initialize() {
    }
}