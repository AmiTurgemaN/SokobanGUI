package view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.SokobanController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.SokobanModel;
import sokobanSolver.SokobanSolver;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import controller.server.Server;

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
		if(args.length==0)
			launch(args);
		else if(args.length==1 && args[0].toLowerCase().equals("cli"))
		{
			CLI view = new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out),"exit");
			SokobanModel model = new SokobanModel();
			SokobanController controller = new SokobanController(model, view);

			model.addObserver(controller);
			view.addObserver(controller);
			view.start();
		}
		else if(args.length==2 && args[0].equals("-server"))
		{
			Server server = new Server(Integer.parseInt(args[1]));
			server.start();
		}
		else if(args.length==2)
		{
			SokobanSolver sokobanSolver = new SokobanSolver(args[0],args[1]);
			sokobanSolver.solve();
		}
	}
}
