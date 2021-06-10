package space.grain49.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomePageController {
    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane content;

    public void initialize() {
        
    }

    public void addBill() throws IOException{
        Stage windows = new Stage();
        Pane contentPage = FXMLLoader.load(getClass().getResource("./添加账单.fxml"));
        Scene scene = new Scene(contentPage);
        windows.setScene(scene);
        windows.show();
    }
}
