package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class playersBorderPane extends BorderPane {

	private String chosen;
	private Image image;
	private Image picImage;
	private Scene scene;
	private LevelDisplayer levelDisplayer;

	
	public String getChosen() {
		return chosen;
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}

	public Image getPicImage() {
		return picImage;
	}

	public void setPicImage(Image picImage) {
		this.picImage = picImage;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public playersBorderPane()
	{
		scene = new Scene(this,200,200);
	}
	
	public LevelDisplayer getLevelDisplayer() {
		return levelDisplayer;
	}

	public void setLevelDisplayer(LevelDisplayer levelDisplayer) {
		this.levelDisplayer = levelDisplayer;
	}

	public void setStage()
	{
		creatingWindow(); 
	}
	
	private void creatingWindow() {
		ChoiceBox<String> cb = new ChoiceBox<>();
		cb.getItems().addAll("Ogre Magi","Rubick","Earthshaker");
		cb.setOnAction(e->{
		           try {
					this.changePicture(cb.getValue());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});
		if(this.chosen==null)
			cb.setValue("Ogre Magi");
		else
			cb.setValue(chosen);
		cb.setTranslateX(50);
		this.setTop(cb);
		VBox vbox = new VBox();
		Button button = new Button("Confirm");
		button.setTranslateX(75);
		button.setTranslateY(-10);
		vbox.getChildren().add(button);
		this.setBottom(vbox);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.setOnCloseRequest(e->{
			stage.close();
		});
		button.setOnAction(e-> {
			this.chosen=cb.getValue();
			this.image=picImage;
			stage.close();
			this.levelDisplayer.setPlayer(chosen);
			this.levelDisplayer.redraw();
		});
		try {
			stage.getIcons().add(new Image(new FileInputStream("resources/playersIcon.png")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		stage.setResizable(false);
		stage.show();
	}
	
	private void changePicture(String value) throws FileNotFoundException {
		if(value.equals("Ogre Magi"))
			picImage = new Image(new FileInputStream("resources/Ogre Magi.png"));
		else if(value.equals("Rubick"))
			picImage = new Image(new FileInputStream("resources/Rubick.png"));
		else if(value.equals("Earthshaker"))
			picImage = new Image(new FileInputStream("resources/Earthshaker.png"));
		ImageView playerImage = new ImageView(picImage);
		playerImage.setFitHeight(130);
		playerImage.setFitWidth(150);
		playerImage.setTranslateY(-10);
		this.setCenter(playerImage);
	}
}
