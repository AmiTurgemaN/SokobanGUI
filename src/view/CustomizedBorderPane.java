package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.data.level.Level;

public class CustomizedBorderPane extends BorderPane {

	ImageView titleImage;
	Text levelNameText;
	Text timerText;
	Text moveCounterText;
	Text errorText;
	TextInputDialog dialog;
	Button solveButton;

	private StringProperty moveCountString;
	private StringProperty levelNameString;
	private StringProperty errorString;
	private int moveCount;
	private int seconds,minutes,hours;
	private Timer t;
	private TimerTask tt;
	private boolean isTimerOn;
	private String levelName;
	private StringProperty timerString;

	public Text getErrorText() {
		return errorText;
	}

	public void setErrorText(Text errorText) {
		this.errorText = errorText;
	}

	public StringProperty getLevelNameString() {
		return levelNameString;
	}

	public void setLevelNameString(StringProperty levelNameString) {
		this.levelNameString = levelNameString;
	}

	public StringProperty getErrorString() {
		return errorString;
	}

	public void setErrorString(StringProperty errorString) {
		this.errorString = errorString;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public ImageView getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(ImageView titleImage) {
		this.titleImage = titleImage;
	}

	public CustomizedBorderPane() {
		try {
			titleImage= new ImageView(new Image(new FileInputStream("resources/titleWithIcon.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.setCenter(titleImage);
	}

	public void startLevel(Level level)
	{
		levelNameText = new Text(this.levelName);
		levelNameText.setFont(new Font("Arial", 60));
		levelNameString = new SimpleStringProperty(levelNameText.getText());
		levelNameText.textProperty().bind(levelNameString);
		GridPane grid = addGridPane(level);
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				getChildren().clear();
				setCenter(levelNameText);
				setBottom(grid);
			}
		});

		this.moveCount=0;
		this.seconds=0;
		this.minutes=0;
		this.hours=0;
		startTimer(level);
		startMoveCount();
	}

	private void startMoveCount() {
		this.moveCountString.set("Move count : " + this.moveCount);
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	protected void startTimer(Level level) {
		DecimalFormat df = new DecimalFormat("00");
		t = new Timer();
		tt = new TimerTask(){
			@Override
			public void run()
			{
				if(isTimerOn) {
					if(++seconds==60)
					{
						seconds=0;
						if(++minutes==60)
						{
							minutes=0;
							hours++;
						}
					}
					timerString.set("Timer : "+df.format(hours)+":"+df.format(minutes)+":"+df.format(seconds));
				} else {
					timerText.setFill(Color.GREEN);
					moveCounterText.setFill(Color.GREEN);
					levelNameString.set(levelName+" Completed");
					t.cancel();
					t.purge();
					tt.cancel();
				}
			};
		};
		t.schedule(tt, 1000, 1000);
	}

	public Text getTimerText() {
		return timerText;
	}

	public void setTimerText(Text timerText) {
		this.timerText = timerText;
	}

	public StringProperty getTimerString() {
		return timerString;
	}

	public void setTimerString(StringProperty timerString) {
		this.timerString = timerString;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public Timer getT() {
		return t;
	}

	public void setT(Timer t) {
		this.t = t;
	}

	public TimerTask getTt() {
		return tt;
	}

	public void setTt(TimerTask tt) {
		this.tt = tt;
	}

	public boolean isTimerOn() {
		return isTimerOn;
	}

	public void setTimerOn(boolean isTimerOn) {
		this.isTimerOn = isTimerOn;
	}

	public void showLevelDetails(Level level) {
		this.levelName=level.getLevelName();
		this.startLevel(level);
	}

	public Text getLevelNameText() {
		return levelNameText;
	}

	public void setLevelNameText(Text levelNameText) {
		this.levelNameText = levelNameText;
	}

	public Text getMoveCounterText() {
		return moveCounterText;
	}

	public void setMoveCounterText(Text moveCounterText) {
		this.moveCounterText = moveCounterText;
	}

	public StringProperty getMoveCountString() {
		return moveCountString;
	}

	public void setMoveCountString(StringProperty moveCountString) {
		this.moveCountString = moveCountString;
	}

	public void updateMoveCounter() {
		this.moveCountString.set("Move count : " + ++this.moveCount);
		this.errorString.set("");
	}

	public GridPane addGridPane(Level level)
	{
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		grid.setGridLinesVisible(false);

		timerText = new Text();
		timerText.setFont(new Font("Arial", 14));
		timerString = new SimpleStringProperty(timerText.getText());
		timerText.textProperty().bind(timerString);
		timerText.setFill(Color.BLACK);
		GridPane.setValignment(timerText, VPos.BOTTOM);
		grid.add(timerText, 0, 2); 

		errorText = new Text();
		errorText.setFont(new Font("Arial", 18));
		errorString = new SimpleStringProperty(errorText.getText());
		errorText.textProperty().bind(errorString);
		errorText.setFill(Color.RED);
		GridPane.setValignment(errorText, VPos.CENTER);
		grid.add(errorText, 11, 2); 

		moveCounterText = new Text();
		moveCounterText.setFont(new Font("Arial", 14));
		moveCountString = new SimpleStringProperty(moveCounterText.getText());
		moveCounterText.textProperty().bind(moveCountString);
		moveCounterText.setFill(Color.BLACK);
		GridPane.setValignment(moveCounterText, VPos.BOTTOM);
		grid.add(moveCounterText, 0, 3);
				
		return grid;
	}

	public void openDialog() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				DecimalFormat df = new DecimalFormat("00");
				dialog = new TextInputDialog();
				dialog.setTitle("Level completed");
				dialog.setHeaderText("Number of moves : "+moveCount+"\nTotal time : "+df.format(hours)+":"+df.format(minutes)+":"+df.format(seconds));
				dialog.setContentText("Please enter your name:");
				Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
				try {
					stage.getIcons().add(new Image(new FileInputStream("resources/addPlayer.png")));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					//send game to server
					//Game game = new Game(dialog.getResult(), levelName, moveCount, seconds+60*(minutes+60*hours));
					//GameManager gameManager = new GameManager(game);
					//gameManager.addGame();
				}
			}
		});
	}
}
