package model.data.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Wall extends GeneralGameObject {

	private static final long serialVersionUID = 1L;
	
	public Wall()
	{
		super();
	}
	
	public Wall(GeneralIntegerPoint point)
	{
		super(point);
	}

	@Override
	public objectType getType() {
		return objectType.WALL;
	}
	
	@Override
	public char getSymbol() {
		return '#';
	}

	@Override
	public Image getImage() {
		Image wallImage=null;
		try {
			wallImage = new Image(new FileInputStream("resources/wall.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return wallImage;
	}
}
