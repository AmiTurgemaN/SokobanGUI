package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.data.level.Level;
import model.data.util.Utilities;
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
	
	private Keys keys;
	private String exitString;
	
	public void setExitString(String exitString) {
		this.exitString = exitString;
	}

	public MainWindowController(){
		this.command="";
		levelDisplayer = new LevelDisplayer();
		levelDisplayer.requestFocus();
		this.borderPane=new CustomizedBorderPane();
		initKeys();
	}

	private void initKeys() {
		this.keys=null;
		try {
			keys = new Keys(new FileInputStream("Hash Maps/keysHashMap.obj"));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void saveFile()
	{
		if(this.levelDisplayer.getLevel()!=null)
		{
			FileChooser fc = new FileChooser();
			fc.setTitle("Save level file");
			fc.setInitialDirectory(new File("./Level Files"));
			FileChooser.ExtensionFilter XmlExtension = 
					new FileChooser.ExtensionFilter(
							"XML Files", "*.xml");
			FileChooser.ExtensionFilter TxtExtension = 
					new FileChooser.ExtensionFilter(
							"Text Files", "*.txt");
			FileChooser.ExtensionFilter ObjExtension = 
					new FileChooser.ExtensionFilter(
							"Objects Files", "*.obj");
			fc.getExtensionFilters().add(TxtExtension);
			fc.getExtensionFilters().add(XmlExtension);
			fc.getExtensionFilters().add(ObjExtension);
			File chosen = fc.showSaveDialog(null);
			if(chosen!=null)
			{
				this.command = "save "+chosen.getName();
				String extension = Utilities.getExtension(chosen.getAbsolutePath());
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Level Saved");
				alert.setHeaderText("Level Saved");
				alert.setContentText("Level "+this.levelDisplayer.getLevel().getLevelName()+" has been saved as "+extension+" file");
				alert.showAndWait().ifPresent(rs -> {
				});
				setChanged();
				notifyObservers(command);
				return;
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Level is not loaded");
			alert.setContentText("Please load file first");
			alert.showAndWait().ifPresent(rs -> {
			});
		}
	}

	public void exit()
	{
		setChanged();
		notifyObservers("exit");
		System.exit(0);
	}
	
	@Override
	public String getExitString() {
		return this.exitString;
	}

	@Override
	public void displayError(String msg) {
		this.borderPane.getErrorString().set("Error : "+msg);
	}

	@Override
	public void displayMessage(String msg) {
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
				if(event.getCode()==keys.getKeysMap().get("up"))
					command = "Move up";
				else if(event.getCode()==keys.getKeysMap().get("down"))
					command = "Move down";
				else if(event.getCode()==keys.getKeysMap().get("right"))
					command = "Move right";
				else if(event.getCode()==keys.getKeysMap().get("left"))
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
		this.borderPane.showLevelDetails(level);
	}

	@Override
	public void levelCompleted() {
		this.borderPane.setTimerOn(false);
	}
}

