package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;

import controller.Client;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.data.level.Level;
import model.data.util.Utilities;
import view.LevelDisplayer;
import view.receiver.ExitReciever;

public class MainWindowController extends Observable implements View{

	private String command;
	private String restartLevel;

	@FXML
	CustomizedBorderPane borderPane;
	@FXML
	LevelDisplayer levelDisplayer;
	@FXML
	Label moveCountLabel;
	@FXML
	Image image;
	@FXML
	MediaView mediaView;
	@FXML
	MenuItem soundMenu;
	playersBorderPane players;
	MediaPlayer mediaPlayer;
	AboutWindow aboutWindow;


	private Keys keys;
	private String exitString;
	private boolean playingSound;
	private Client client;
	private String currentLevelName;

	public void setExitString(String exitString) {
		this.exitString = exitString;
	}

	public MainWindowController(){
		this.command="";
		this.restartLevel="";
		this.playingSound=true;
		this.currentLevelName="";
		initMusic();
		levelDisplayer = new LevelDisplayer();
		levelDisplayer.requestFocus();
		this.borderPane=new CustomizedBorderPane();
		initKeys();
		players = new playersBorderPane();
		this.client = new Client("localhost",5555);
		enterName();
		this.client.connect();
		
	}


	private void initMusic() {
		String path = "resources/dotaMusic.mp3";
		Media media = new Media(new File(path).toURI().toString());
		if(mediaPlayer!=null)
			this.mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		this.mediaView = new MediaView(mediaPlayer);
	}

	public void sound()
	{
		if(this.playingSound==true)
		{
			if(mediaView.getMediaPlayer()!=null)
				mediaView.getMediaPlayer().stop();
			this.playingSound=false;
			this.soundMenu.setText("Enable Sound");
		}
		else
		{	if(mediaView.getMediaPlayer()!=null)
			mediaView.getMediaPlayer().play();
		this.playingSound=true;
		this.soundMenu.setText("Disable Sound");
		}
	}

	public void changePlayer()
	{
		players.setLevelDisplayer(levelDisplayer);
		players.setStage();
	}

	private void initKeys() {
		this.keys=null;
		try {
			keys = new Keys(new FileInputStream("Hash Maps/keysHashMap.obj"));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void about()
	{
		aboutWindow = new AboutWindow();
	}

	public void recordsCurrentLevel()
	{ 
		if(this.levelDisplayer.getLevel()!=null)
		{
			new Record(borderPane.getLevelName(),client);
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
	public void recordsWorldWide()
	{
		new Record(client);
	}

	public void restartLevel()
	{
		if(!restartLevel.equals(""))
		{
			this.borderPane.setTimerOn(false);
			if(this.borderPane.getT()!=null)
			{
				this.borderPane.getT().cancel();
				this.borderPane.getT().purge();
				this.borderPane.getTt().cancel();
			}
			initMusic();
			if(this.playingSound==true)
				mediaView.getMediaPlayer().setAutoPlay(true);
			setChanged();
			notifyObservers(restartLevel);
			setChanged();
			notifyObservers("Display");
			this.borderPane.setTimerOn(true);
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
	
	public void solveLevel()
	{
		if(currentLevelName.equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Level is not loaded");
			alert.setContentText("Please load file first");
			alert.showAndWait().ifPresent(rs -> {
			});
		}
		else
		{
			model.data.beans.Level levelToSolve = new model.data.beans.Level();
			levelToSolve.setLevelName(currentLevelName);
			levelToSolve.setLevelString(levelDisplayer.getLevel().getLevelString());
			String solution = client.getSolution(levelToSolve);
			if(solution.equals("") || solution.equals("Unsolvable"))
			{ 
				System.out.println(solution);
				displayError("Level cannot be solved");
			}
			else
			{
				String [] moveCommands = solution.split("\n");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for(String command : moveCommands)
						{
							System.out.println(command);
							setChanged();
							notifyObservers(command);
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();;
			}
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
			initMusic();
			if(this.playingSound==true)
				mediaView.getMediaPlayer().setAutoPlay(true);
			this.currentLevelName=chosen.getName();
			this.command = "load "+chosen.getName();
			this.restartLevel="load "+chosen.getName();
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
		levelDisplayer.setPlayer(this.players.getChosen());
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
		String path = "resources/completedDotaMusic.mp3";
		Media media = new Media(new File(path).toURI().toString());
		this.mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		if(playingSound)
			mediaPlayer.setAutoPlay(true);
		this.mediaView = new MediaView(mediaPlayer);
		this.borderPane.openDialog(client);
	}

	@Override
	public PrintWriter getOut() {
		return null;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void enterName()
	{
		TextInputDialog dialog;
		dialog = new TextInputDialog();
		dialog.setTitle("Welcome");
		dialog.setHeaderText("Connecting to server");
		dialog.setContentText("Please enter your name:");
		Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
		try {
			stage.getIcons().add(new Image(new FileInputStream("resources/addPlayer.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			client.setUserName(result.get());
		}
		if(client.getUserName()==null || client.getUserName().equals(""))
		{
			Random r = new Random();
			client.setUserName("Player"+r.nextInt(1000));
		}
	}

	public String getCurrentLevelName() {
		return currentLevelName;
	}

	public void setCurrentLevelName(String currentLevelName) {
		this.currentLevelName = currentLevelName;
	}
}

