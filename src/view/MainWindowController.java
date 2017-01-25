package view;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.data.level.Level;
import view.receiver.ExitReciever;

public class MainWindowController extends Observable implements View,Initializable{

	private String command;
	
	@FXML
	LevelDisplayer levelDisplayer;
	@FXML
	Label moveCountLabel;
	
	public MainWindowController(){
		this.command="";
		levelDisplayer = new LevelDisplayer();
		levelDisplayer.requestFocus();
	}
	
	public MainWindowController(Level level) {
		this.command="";
		levelDisplayer = new LevelDisplayer(level);
		displayLevel(level);
	}

	public void openFile()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Open level file");
		fc.setInitialDirectory(new File("./Level Files"));
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"Level files", "*.txt", "*.obj", "*.xml");

		fc.getExtensionFilters().add(fileExtensions);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
			this.command = "load "+chosen.getName();
			setChanged();
			notifyObservers(command);
			setChanged();
			notifyObservers("Display");
			this.moveCountLabel.setText("Move Counter : ");
		}
	}

	@Override
	public String getExitString() {
		return "exit";
	}

	@Override
	public void displayError(String msg) {
		System.out.println("Error: "+msg);
		System.out.flush();
	}

	@Override
	public void displayMessage(String msg) {
		System.out.println(msg);
		System.out.flush();
	}

	@Override
	public void start() {
		this.levelDisplayer.redraw();
	}

	@Override
	public void displayLevel(Level level) {
		levelDisplayer.setLevel(level);
		levelDisplayer.setFocusTraversable(true);
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.UP)
					command = "Move up";
				else if(event.getCode()==KeyCode.DOWN)
					command = "Move down";
				else if(event.getCode()==KeyCode.RIGHT)
					command = "Move right";
				else if(event.getCode()==KeyCode.LEFT)
					command = "Move left";
				setChanged();
				notifyObservers(command);
			}
		});
	}

	@Override
	public void exit(String exitString) {
		ExitReciever er = new ExitReciever(exitString, new PrintWriter(System.out));
		er.action();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}

