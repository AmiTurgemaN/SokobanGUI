package view;

import java.io.File;
import java.util.Observable;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.data.level.Level;

public class MainWindowController extends Observable implements View{

	@FXML
	LevelDisplayer levelDisplayer;
	
	private String command;
	
	public MainWindowController(Level level) {
		levelDisplayer = new LevelDisplayer(level);

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
		}
	}

	@Override
	public String getExitString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayError(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMessage(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		String command = "Display";
		this.setChanged();
		this.notifyObservers(command);
	}

	@Override
	public void displayLevel(Level level) {
		levelDisplayer.setLevel(level);

	}

	@Override
	public void exit(String exitString) {
		// TODO Auto-generated method stub

	}

}

