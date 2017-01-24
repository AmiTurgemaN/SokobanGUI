package view;
	
import controller.SokobanController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.SokobanModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			BorderPane root = (BorderPane)loader.load();	
			MainWindowController view = loader.getController();
											
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			init(view);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init(MainWindowController view) {
		SokobanModel model = new SokobanModel();
		SokobanController controller = new SokobanController(model, view);
		
		model.addObserver(controller);
		view.addObserver(controller);
		view.start();	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
