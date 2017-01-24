package model.data.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Box extends GeneralGameObject {

	private static final long serialVersionUID = 1L;

	public Box()
	{
		super();
	}

	public Box(GeneralIntegerPoint point)
	{

	}

	@Override
	public objectType getType() {
		return objectType.BOX;
	}

	public char getSymbol() {
		if(this.onArea==true)
			return '$';
		return '@';
	}

	@Override
	public Image getImage() {
		Image boxImage=null;
		if(this.onArea)
		{
			try {
				boxImage = new Image(new FileInputStream("resources/BoxOnArea.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				boxImage = new Image(new FileInputStream("resources/box.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return boxImage;
	}
}
