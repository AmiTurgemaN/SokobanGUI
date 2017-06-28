package view;

import java.io.FileInputStream;

import controller.SokobanController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.SokobanModel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MainWindowController view = loader.getController();
			view.setExitString("exit");
			SokobanModel model = new SokobanModel();
			SokobanController controller = new SokobanController(model, view);
			model.addObserver(controller);
			view.addObserver(controller);
			Scene scene = new Scene(root, 700, 750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(new FileInputStream("resources/sokobanIcon.png")));
			primaryStage.setTitle("Sokoban");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			view.start();
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				view.exit();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
			launch(args);
	}
}
