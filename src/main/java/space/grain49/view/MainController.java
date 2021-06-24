package space.grain49.view;

import java.io.IOException;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController {

	@FXML
	private Label title;

	@FXML
	private AnchorPane content; // content为顶栏下的空白面板

	@FXML
	private AnchorPane body;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private AnchorPane mainPage;

	@FXML
	private Pane vBox;

	private JFXDrawersStack drawersStack;

	private JFXDrawer leftDrawer;

	private HamburgerBackArrowBasicTransition ht;

	@FXML
	public void initialize() {
		refreshPage("主页");
		drawersStack = new JFXDrawersStack();
		drawersStack.setLayoutY(58); // drawer的起始位置x定点，默认0，y同。
		body.getChildren().add(drawersStack);

		for (Node node : vBox.getChildren()) {
			node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				refreshPage(node.getAccessibleText());
				toggerDrawer();
			});
		}

		leftDrawer = new JFXDrawer();
		leftDrawer.setSidePane(vBox);// 注入
		// leftDrawer.setDirection(JFXDrawer.DrawerDirection.LEFT); 默认LEFT
		leftDrawer.setDefaultDrawerSize(160);// 与vBox宽对应
		leftDrawer.setResizeContent(false);
		leftDrawer.setOverLayVisible(false);
		leftDrawer.setResizableOnDrag(true);

		ht = new HamburgerBackArrowBasicTransition(hamburger);
		ht.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			toggerDrawer();
		});

		// 抽屉打开状态下，点击content抽屉以关闭
		content.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (ht.getRate() != -1) {
				toggerDrawer();
			}
		});
	}

	private void toggerDrawer() {
		ht.setRate(ht.getRate() * -1);
		ht.play();
		leftDrawer.setPrefHeight(content.getHeight());
		drawersStack.toggle(leftDrawer);
	}

	private void refreshPage(String page) {
		try {
			content.getChildren().clear();
			System.out.println(getClass().getResource("./" + page + ".fxml"));
			Pane contentPage = FXMLLoader.load(getClass().getResource("./" + page + ".fxml"));
			content.getChildren().add(contentPage);
			AnchorPane.setRightAnchor(contentPage, 0.0);
			AnchorPane.setLeftAnchor(contentPage, 0.0);
			AnchorPane.setTopAnchor(contentPage, 0.0);
			AnchorPane.setBottomAnchor(contentPage, 0.0);
			title.setText(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
