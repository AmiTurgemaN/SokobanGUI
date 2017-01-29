package model.data.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Space extends GeneralGameObject {

	private static final long serialVersionUID = 1L;

	public Space()
	{
		super();
	}

	public Space(GeneralIntegerPoint point)
	{
		super(point);
	}

	@Override
	public objectType getType() {
		return objectType.SPACE;
	}

	@Override
	public char getSymbol() {
		return ' ';
	}

	@Override
	public Image getImage() {
		Image wallImage=null;
		if(!((this.point.getX()%2==0 && this.point.getY()%2==0) || (this.point.getX()%2==1 && this.point.getY()%2==1)))
		{
			try {
				wallImage = new Image(new FileInputStream("resources/black.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return wallImage;
	}

}
