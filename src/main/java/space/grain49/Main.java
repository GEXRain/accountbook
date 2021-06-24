package space.grain49;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import space.grain49.server.BillDbUtil;
import space.grain49.server.PaymentAccountDbUtil;
import space.grain49.server.SignInDbUtil.SignInData;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("test1");
		BillDbUtil.get();
		PaymentAccountDbUtil.get();
		SignInData.load();
		initRootLayout();
	}

	public void initRootLayout() {
		try {
			rootLayout = FXMLLoader.load(getClass().getResource("view/Root.fxml"));
			rootLayout.getChildren();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void displayWorrying() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("先登录一下吧");
		alert.setContentText("联网登录后才可以使用");

		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}