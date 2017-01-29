package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutWindow extends BorderPane {

	public AboutWindow()
	{
		Text title = new Text();
		title.setText("About");
		title.setTranslateX(50);
		title.setFont(new Font("Arial", 30));
		Text message = new Text();
		message.setText("This game has been\ndeveloped by Amit\nTurgeman\nas part of a\ncollege project");
		message.setFont(new Font("Arial",20));
		Text rights = new Text();
		rights.setFont(new Font("Arial", 11));
		rights.setTranslateX(2);
		rights.setText("All rights reserved © 2016 Amit Turgeman");
		this.setTop(title);
		this.setCenter(message);
		this.setBottom(rights);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(this,200,200));
		stage.setOnCloseRequest(e->{
			stage.close();
		});
		try {
			stage.getIcons().add(new Image(new FileInputStream("resources/aboutIcon.png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		stage.setResizable(false);
		stage.show();
	}
}

