package view;

import java.io.File;
import java.io.PrintWriter;
import java.util.Observable;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.data.level.Level;
import view.LevelDisplayer;
import view.receiver.ExitReciever;

public class MainWindowController extends Observable implements View{

	private String command;
	
	@FXML
	CustomizedBorderPane borderPane;
	@FXML
	LevelDisplayer levelDisplayer;
	@FXML
	Label moveCountLabel;
	@FXML
	ImageView titleImage;
	
	public MainWindowController(){
		this.command="";
		levelDisplayer = new LevelDisplayer();
		levelDisplayer.requestFocus();
		this.borderPane=new CustomizedBorderPane();
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
			this.borderPane.setTimerOn(false);
			if(this.borderPane.getT()!=null)
			{
				this.borderPane.getT().cancel();
				this.borderPane.getT().purge();
				this.borderPane.getTt().cancel();
			}
			this.command = "load "+chosen.getName();
			setChanged();
			notifyObservers(command);
			setChanged();
			notifyObservers("Display");
			this.borderPane.setTimerOn(true);
		}
	}

	@Override
	public String getExitString() {
		return "exit";
	}

	@Override
	public void displayError(String msg) {
		this.borderPane.errorString.set("Error : "+msg);
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
	public void updateCounter() {
		this.borderPane.updateMoveCounter();
	}

	@Override
	public void showLevelDetails(Level level) {
		this.borderPane.showLevelDetails(level.getLevelName());
	}

	@Override
	public void levelCompleted() {
		this.borderPane.setTimerOn(false);
		displayMessage("Level completed!");
	}
}

